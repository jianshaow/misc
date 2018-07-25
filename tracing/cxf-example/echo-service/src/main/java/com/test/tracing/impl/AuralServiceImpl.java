package com.test.tracing.impl;

import javax.ws.rs.core.Context;

import org.apache.cxf.tracing.TracerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.tracing.AuralService;
import com.test.tracing.MindService;

public class AuralServiceImpl implements AuralService {

    private static Logger logger = LoggerFactory.getLogger(AuralServiceImpl.class);

    private MindService mindService;

    @Context
    private TracerContext tracerContext;

    @Override
    public String hear(String msg) {
        final int latency = SleepUtil.sleepRandomly(100);
        logger.info("I got the msg finally in {} ms.", latency);
        tracerContext.timeline("recall in mind...");
        final String result = mindService.recall(msg);
        return result;
    }

    public void setMindService(MindService mindService) {
        this.mindService = mindService;
    }
}
