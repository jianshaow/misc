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
