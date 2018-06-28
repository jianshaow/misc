package com.test.zipkin.impl;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.test.zipkin.MindService;

public class MindServiceImpl implements MindService {

    private static Random random = new Random();

    private static Logger logger = LoggerFactory.getLogger(MindServiceImpl.class);

    private JdbcTemplate template;

    @Override
    public String respond(String msg) {
        long latency = random.nextInt(10);
        try {
            Thread.sleep(latency);
        } catch (InterruptedException e) {
            // do nothing
        }
        template.execute("select * from application");
        logger.info("I losed my mind for {} ms.", latency);
        return "echo back: " + msg;
    }

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }
}
