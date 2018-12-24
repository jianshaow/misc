package com.test.benchmark.akka;

import com.test.benchmark.BenchmarkTask;
import com.test.benchmark.ConcurrentBenchmark;

import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

public class AkkaRemoteLookupBenchMark extends ConcurrentBenchmark {

    private ActorSystem system;

    public AkkaRemoteLookupBenchMark(int defaultThreadCount, long defaultLoopCount) {
        super(defaultThreadCount, defaultLoopCount);
    }

    @Override
    protected void setUp() {
        this.system = ActorSystem.create("testLookupClient");
    }

    @Override
    protected BenchmarkTask createTask() {
        return new AkkaCreationTask();
    }

    public class AkkaCreationTask extends BenchmarkTask {

        private ActorSelection actorSelection;

        public AkkaCreationTask() {
            final String path = "akka.tcp://testServer@127.0.0.1:2552/user/testActor";
            this.actorSelection = system.actorSelection(path);
        }

        @Override
        protected void execute(@SuppressWarnings("unused") int requestSequence) {
            try {
                final Timeout timeout = new Timeout(Duration.create(5, "seconds"));
                final Future<Object> future = Patterns.ask(this.actorSelection, "Hello", timeout);
                final String result = (String) Await.result(future, timeout.duration());
//                System.out.println("reply: " + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        final AkkaRemoteLookupBenchMark benchMark = new AkkaRemoteLookupBenchMark(10, 100);
        benchMark.execute();
    }

    @Override
    protected void tearDown() throws Exception {
        this.system.terminate();
    }
}
