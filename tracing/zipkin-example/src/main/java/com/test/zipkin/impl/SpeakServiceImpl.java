package com.test.zipkin.impl;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.zipkin.SpeakService;

public class SpeakServiceImpl implements SpeakService {

    private static Random random = new Random();

    private static Logger logger = LoggerFactory.getLogger(SpeakServiceImpl.class);

    @Override
    public String say(String msg) {
        long latency = random.nextInt(10);
        try {
            Thread.sleep(latency);
        } catch (InterruptedException e) {
            // do nothing
        }
        logger.info("I stuttered for {} ms.", latency);
        return msg;
    }

}
