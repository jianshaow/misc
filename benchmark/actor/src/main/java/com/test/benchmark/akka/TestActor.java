/*------------------------------------------------------------------------------
 * COPYRIGHT Ericsson 2015
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *----------------------------------------------------------------------------*/
package com.test.benchmark.akka;

import akka.actor.Props;
import akka.actor.UntypedActor;

public class TestActor extends UntypedActor {

    public static Props create() {
        return Props.create(TestActor.class);
    }

    @Override
    public void onReceive(final Object msg) throws Exception {
        if ("start".equals(msg)) {
            System.out.println("TestActor started...............");
        } else if ("stop".equals(msg)) {
            System.out.println("TestActor stopped...............");
        } else {
//            System.out.println("recieve: " + msg);
            this.sender().tell(msg, this.self());
        }
    }
}
