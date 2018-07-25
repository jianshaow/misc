package com.test.tracing;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/vision")
public interface VisionService {

    @POST
    @Path("/see")
    void see();
}
