package com.test.tracing.impl;

import javax.ws.rs.core.Context;

import org.apache.cxf.tracing.TracerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.tracing.MindService;
import com.test.tracing.SpeakService;

public class SpeakServiceImpl implements SpeakService {

    private static Logger logger = LoggerFactory.getLogger(SpeakServiceImpl.class);

    private MindService mindService;

    @Context
    private TracerContext tracerContext;

    @Override
    public String say(String msg) {
        tracerContext.timeline("respond in mind...");
        final String result = mindService.respond(msg);
        final int latency = SleepUtil.sleepRandomly(100);
        logger.info("I stuttered for {} ms.", latency);
        return result;
    }

    public void setMindService(MindService mindService) {
        this.mindService = mindService;
    }
}
