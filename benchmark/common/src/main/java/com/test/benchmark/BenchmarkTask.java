package com.test.benchmark;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

public abstract class BenchmarkTask implements Runnable {

    public static final String VERBOSE_NAME = "benchmark.verbose";

    private boolean verbose = false;

    protected int taskSequence;
    protected ConcurrentBenchmark parent;

    protected long previousRequests = 0L;
    protected long nextPrintTime;

    public BenchmarkTask() {
        this.verbose = Boolean.valueOf(System.getProperty(VERBOSE_NAME, "false"));
    }

    @Override
    public void run() {
        try {
            setUp();
            onThreadStart();
            for (int i = 1; i <= parent.getLoopCount(); i++) {
                execute(i);
                if (verbose) {
                    printProgressMessage(i);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                tearDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
            onThreadFinish();
        }
    }

    abstract protected void execute(final int requestSequence);

    /**
     * Must be invoked when each thread after the setup().
     */
    protected void onThreadStart() {
        parent.getStartLock().countDown();
        // wait for all other threads ready
        try {
            parent.getStartLock().await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        nextPrintTime = System.currentTimeMillis() + parent.getInterval();

    }

    /**
     * Must be invoked when each thread finish loop, before the
     * tearDown().
     */
    protected void onThreadFinish() {
        // notify test finish
        parent.getFinishLock().countDown();

        // print finish summary message
        if (verbose) {
            printThreadFinishMessage();
        }
    }

    /**
     * print progress.
     */
    protected void printProgressMessage(final int currentRequests) {
        long currentTime = System.currentTimeMillis();

        if (currentTime > nextPrintTime) {
            long lastIntervalMillis = parent.getInterval() + (currentTime - nextPrintTime);
            nextPrintTime = currentTime + parent.getInterval();

            long lastRequests = currentRequests - previousRequests;

            long totalTimeMillis = currentTime - parent.getStartTime();
            long totalTimeSeconds = TimeUnit.MILLISECONDS.toSeconds(totalTimeMillis);

            long totalTps = (currentRequests * 1000) / totalTimeMillis;
            long lastTps = (lastRequests * 1000) / lastIntervalMillis;

            BigDecimal lastLatency = new BigDecimal(lastIntervalMillis).divide(new BigDecimal(lastRequests), 2,
                    BigDecimal.ROUND_HALF_UP);
            BigDecimal totalLatency = new BigDecimal(totalTimeMillis).divide(new BigDecimal(currentRequests), 2,
                    BigDecimal.ROUND_HALF_UP);

            if (verbose) {
                System.out.printf(
                        "Thread %02d process %,d requests after %s seconds. Last tps/latency is %,d/%sms. Total tps/latency is %,d/%sms.%n",
                        taskSequence, currentRequests, totalTimeSeconds, lastTps, lastLatency.toString(), totalTps,
                        totalLatency.toString());
            }

            previousRequests = currentRequests;
        }
    }

    /**
     * print result.
     */
    protected void printThreadFinishMessage() {
        long totalTimeMillis = System.currentTimeMillis() - parent.getStartTime();
        long totalRequest = parent.getLoopCount();
        long totalTps = (totalRequest * 1000) / totalTimeMillis;
        BigDecimal totalLatency = new BigDecimal(totalTimeMillis).divide(new BigDecimal(totalRequest), 2,
                BigDecimal.ROUND_HALF_UP);

        System.out.printf("Thread %02d finish.Total tps/latency is %,d/%sms.%n", taskSequence, totalTps,
                totalLatency.toString());
    }

    /**
     * Override for thread local connection and data setup.
     */
    protected void setUp() throws Exception {
        //
    }

    /**
     * Override for thread local connection and data cleanup.
     */
    protected void tearDown() throws Exception {
        //
    }
}
