package com.test.tracing.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.tracing.AuralService;
import com.test.tracing.EchoService;
import com.test.tracing.SpeakService;

public class EchoServiceImpl implements EchoService {

    private static Logger logger = LoggerFactory.getLogger(AuralServiceImpl.class);

    private AuralService auralService;

    private SpeakService speakService;

    @Override
    public String echo(String msg) {
        logger.info("be requested to echo a message: {}", msg);
        String result = msg;
        result = auralService.hear(result);
        result = speakService.say(result);
        logger.info("echo back the result: {}", result);
        return result;
    }

    public void setAuralService(AuralService auralService) {
        this.auralService = auralService;
    }

    public void setSpeakService(SpeakService speakService) {
        this.speakService = speakService;
    }
}
