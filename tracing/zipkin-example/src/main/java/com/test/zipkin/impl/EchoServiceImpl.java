package com.test.zipkin.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.zipkin.AuralService;
import com.test.zipkin.EchoService;
import com.test.zipkin.MindService;
import com.test.zipkin.SpeakService;

public class EchoServiceImpl implements EchoService {

    private static Logger logger = LoggerFactory.getLogger(AuralServiceImpl.class);

    private AuralService auralService;

    private MindService mindService;

    private SpeakService speakService;

    @Override
    public String echo(String msg) {
        logger.info("be requested to echo a message: {}", msg);
        String result = msg;
        result = auralService.hear(result);
        result = mindService.respond(result);
        result = speakService.say(result);
        logger.info("echo back the result: {}", result);
        return result;
    }

    public void setAuralService(AuralService auralService) {
        this.auralService = auralService;
    }

    public void setMindService(MindService mindService) {
        this.mindService = mindService;
    }

    public void setSpeakService(SpeakService speakService) {
        this.speakService = speakService;
    }
}
