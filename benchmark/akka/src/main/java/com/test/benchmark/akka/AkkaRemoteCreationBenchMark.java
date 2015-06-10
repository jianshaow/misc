/*------------------------------------------------------------------------------
 * COPYRIGHT Ericsson 2015
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *----------------------------------------------------------------------------*/
package com.test.benchmark.akka;

import java.util.concurrent.atomic.AtomicInteger;

import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import akka.util.Timeout;

import com.test.benchmark.BenchmarkTask;
import com.test.benchmark.ConcurrentBenchmark;

public class AkkaRemoteCreationBenchMark extends ConcurrentBenchmark {

    private ActorSystem system;

    private AtomicInteger counter = new AtomicInteger(0);

    public AkkaRemoteCreationBenchMark(int defaultThreadCount, long defaultLoopCount) {
        super(defaultThreadCount, defaultLoopCount);
    }

    @Override
    protected void setUp() {
        this.system = ActorSystem.create("testCreationClient");
    }

    @Override
    protected BenchmarkTask createTask() {
        return new AkkaCreationTask();
    }

    public class AkkaCreationTask extends BenchmarkTask {

        private ActorRef actorRef;

        public AkkaCreationTask() {
            this.actorRef = system.actorOf(TestActor.create(), "testActor-" + counter.incrementAndGet());
        }

        @Override
        protected void execute(@SuppressWarnings("unused") int requestSequence) {
            try {
                final Timeout timeout = new Timeout(Duration.create(5, "seconds"));
                final Future<Object> future = Patterns.ask(this.actorRef, "Hello", timeout);
                final String result = (String) Await.result(future, timeout.duration());
//                System.out.println("reply: " + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        int threadCount = Integer.valueOf(args[0]);
        int loopCount = Integer.valueOf(args[1]);
        final AkkaRemoteCreationBenchMark benchMark = new AkkaRemoteCreationBenchMark(threadCount, loopCount);
        benchMark.execute();
    }

    @Override
    protected void tearDown() throws Exception {
        this.system.shutdown();
    }
}
