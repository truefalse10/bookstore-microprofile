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
  String version;

  @Inject
  JsonWebToken token;

  /**
   * simple ping route to test jwt.
   * @return ping answer
   */
  @GET
  @RolesAllowed("hacker")
  public String ping() {
    System.out.println("****raw-token****:" + this.token.getRawToken());
    return "groups: " + token.getGroups() + " token:" + token.getRawToken();
  }

}
