package com.test.benchmark.akka;

import java.util.concurrent.atomic.AtomicInteger;

import com.test.benchmark.BenchmarkTask;
import com.test.benchmark.ConcurrentBenchmark;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

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
        final AkkaRemoteCreationBenchMark benchMark = new AkkaRemoteCreationBenchMark(10, 100);
        benchMark.execute();
    }

    @Override
    protected void tearDown() throws Exception {
        this.system.shutdown();
    }
}
