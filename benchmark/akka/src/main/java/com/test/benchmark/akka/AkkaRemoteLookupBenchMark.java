package com.test.benchmark.akka;

import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import akka.util.Timeout;

import com.test.benchmark.BenchmarkTask;
import com.test.benchmark.ConcurrentBenchmark;

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
        int threadCount = Integer.valueOf(args[0]);
        int loopCount = Integer.valueOf(args[1]);
        final AkkaRemoteLookupBenchMark benchMark = new AkkaRemoteLookupBenchMark(threadCount, loopCount);
        benchMark.execute();
    }

    @Override
    protected void tearDown() throws Exception {
        this.system.shutdown();
    }
}
