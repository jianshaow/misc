/*------------------------------------------------------------------------------
 * COPYRIGHT Ericsson 2018
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *----------------------------------------------------------------------------*/
package com.test.zipkin.impl;

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
