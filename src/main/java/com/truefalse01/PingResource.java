package com.truefalse01;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("ping")
@RequestScoped
public class PingResource {

    @Inject
    @ConfigProperty(name = "VERSION")
    String VERSION;

    @Inject
    JsonWebToken token;

    @GET
    @RolesAllowed("hacker")
    public String ping() {
        // TODO: somehow getRawToken always returns null?!
        System.out.println("****raw-token****:" + this.token.getRawToken());
        return "groups: " + token.getGroups() + " token:" + token.getRawToken();
    }

}
