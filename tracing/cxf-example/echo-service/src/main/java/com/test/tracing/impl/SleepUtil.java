package com.test.tracing.impl;

import java.util.Random;

public class SleepUtil {

    private static Random random = new Random();

    public static int sleepRandomly(int maxMs) {
        int latency = random.nextInt(maxMs);
        try {
            Thread.sleep(latency);
        } catch (InterruptedException e) {
            // do nothing
        }
        return latency;
    }
}
