package com.test.benchmark;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class ConcurrentBenchmark {

    public static final String THREAD_COUNT_NAME = "benchmark.thread.count";
    public static final String LOOP_COUNT_NAME = "benchmark.loop.count";

    private int threadCount;
    private long loopCount;

    private CountDownLatch startLock;
    private CountDownLatch finishLock;

    private long startTime;
    private int interval = 10 * 1000;

    public ConcurrentBenchmark(int defaultThreadCount, long defaultLoopCount) {
        // merge default setting and system properties
        this.threadCount = Integer.parseInt(System.getProperty(THREAD_COUNT_NAME, String.valueOf(defaultThreadCount)));
        this.loopCount = Long.parseLong(System.getProperty(LOOP_COUNT_NAME, String.valueOf(defaultLoopCount)));

        startLock = new CountDownLatch(threadCount);
        finishLock = new CountDownLatch(threadCount);
    }

    public CountDownLatch getStartLock() {
        return startLock;
    }

    public CountDownLatch getFinishLock() {
        return finishLock;
    }

    public int getInterval() {
        return interval;
    }

    public long getStartTime() {
        return startTime;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public long getLoopCount() {
        return loopCount;
    }

    public void execute() throws Exception {
        // override for connection & data setup
        setUp();

        // start threads
        ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);
        try {
            for (int i = 0; i < threadCount; i++) {
                BenchmarkTask task = createTask();
                task.taskSequence = i;
                task.parent = this;
                threadPool.execute(task);
            }

            // wait for all threads ready
            startLock.await();

            // print start message
            startTime = System.currentTimeMillis();
            printStartMessage();

            // wait for all threads finish
            finishLock.await();

            // print finish summary message
            printFinishMessage();
        } finally {
            threadPool.shutdownNow();
            // override for connection & data cleanup
            tearDown();
        }
    }

    protected void printStartMessage() {
        String className = this.getClass().getSimpleName();
        long invokeTimes = threadCount * loopCount;

        System.out.printf("%s started at %s.%n%d threads with %,d loops, totally %,d requests will be invoked.%n",
                className, new Date(startTime).toString(), threadCount, loopCount, invokeTimes);
    }

    protected void printFinishMessage() {
        long endTime = System.currentTimeMillis();
        String className = this.getClass().getSimpleName();
        long invokeTimes = threadCount * loopCount;
        long totalTimeMillis = endTime - startTime;
        long tps = (invokeTimes * 1000) / totalTimeMillis;

        System.out.printf("%s finished at %s.%n%d threads processed %,d requests after %,d ms, tps is %,d.%n",
                className, new Date(endTime).toString(), threadCount, invokeTimes, totalTimeMillis, tps);
    }

    protected void setIntervalSeconds(int intervalSeconds) {
        this.interval = intervalSeconds * 1000;
    }

    /**
     * Override for connection & data setup.
     */
    protected void setUp() throws Exception {
        //
    }

    /**
     * Override to connection & data cleanup.
     */
    protected void tearDown() throws Exception {
        //
    }

    /**
     * create a new benchmark task.
     */
    protected abstract BenchmarkTask createTask();
}
