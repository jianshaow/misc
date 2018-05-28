package com.test.btrace;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.util.Random;

public class BTraceExample {

    private static final String PROMPT = ">> ";

    private static Random random = new Random();

    private static boolean running = true;

    public static void main(String[] args) throws Exception {
        final String processName = ManagementFactory.getRuntimeMXBean().getName();
        System.out.println("demo process " + processName + " is running");
        new Thread(() -> BTraceExample.run()).start();
        System.out.print(PROMPT);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (!"quit".equals(reader.readLine())) {
            System.out.print(PROMPT);
        }
        running = false;
    }

    public static void run() {
        final BTraceExample bTraceExample = new BTraceExample();
        while (running) {
            try {
                bTraceExample.add(random.nextInt(10), random.nextInt(10));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int add(int a, int b) throws Exception {
        Thread.sleep(random.nextInt(10) * 100);
        return a + b;
    }
}
