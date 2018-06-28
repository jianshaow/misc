package com.test.zipkin;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/mind")
public interface MindService {

    @POST
    @Path("/respond")
    String respond(String msg);
}
