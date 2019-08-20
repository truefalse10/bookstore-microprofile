package com.truefalse01;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("ping")
public class PingResource {

    @Inject
    @ConfigProperty(name = "VERSION")
    String VERSION;

    @GET
    public String ping() {
        return "Enjoy Java EE 8 with MicroProfile v." + VERSION + "!";
    }

}
