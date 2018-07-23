package com.test.zipkin;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/echo")
public interface EchoService {

    @POST
    String echo(String msg);
}
