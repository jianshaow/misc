package com.test.zipkin.impl;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.zipkin.AuralService;

public class AuralServiceImpl implements AuralService {

    private static Random random = new Random();

    private static Logger logger = LoggerFactory.getLogger(AuralServiceImpl.class);

    @Override
    public String hear(String msg) {
        long latency = random.nextInt(10);
        try {
            Thread.sleep(latency);
        } catch (InterruptedException e) {
            // do nothing
        }
        logger.info("I got the point in {} ms.", latency);
        return msg;
    }
}
