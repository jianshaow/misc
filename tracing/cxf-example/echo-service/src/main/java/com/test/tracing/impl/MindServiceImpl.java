package com.test.tracing.impl;

import javax.ws.rs.core.Context;

import org.apache.cxf.tracing.TracerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.test.tracing.MindService;

public class MindServiceImpl implements MindService {

    private static Logger logger = LoggerFactory.getLogger(MindServiceImpl.class);

    @Context
    private TracerContext tracerContext;

    private JdbcTemplate template;

    @Override
    public String respond(String msg) {
        final int latency = SleepUtil.sleepRandomly(100);
        logger.info("I lost my mind for {} ms.", latency);
        return "echo back: " + msg;
    }

    @Override
    public String recall(String something) {
        final int latency = SleepUtil.sleepRandomly(100);
        logger.info("I lost my mind for {} ms.", latency);
        tracerContext.timeline("asking memory...");
        template.execute("select * from application");
        return something;
    }

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }
}
