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

import akka.actor.ActorSystem;
import akka.kernel.Bootable;

public class TestCreationKernel implements Bootable {

    private ActorSystem system;

    @Override
    public void shutdown() {
        system.shutdown();
    }

    @Override
    public void startup() {
        this.system = ActorSystem.create("testServer");
    }
}
