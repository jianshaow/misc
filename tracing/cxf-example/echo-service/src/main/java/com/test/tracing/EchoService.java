package com.test.tracing;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/echo")
public interface EchoService {

    @POST
    String echo(String msg);
}
