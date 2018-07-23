package com.test.zipkin;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/mind")
public interface MindService {

    @POST
    @Path("/recall")
    String recall(String something);

    @POST
    @Path("/respond")
    String respond(String msg);
}
