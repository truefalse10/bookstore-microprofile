package com.truefalse01;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("ping")
public class PingResource {

    @GET
    public String ping() {
        return "Enjoy Java EE 8 with MicroProfile";
    }

}
