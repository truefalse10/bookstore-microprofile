package com.truefalse01;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("ping")
@RequestScoped
public class PingResource {

  @Inject
  @ConfigProperty(name = "VERSION")
  String version;

  @GET
  public String ping() {
    return "Hello from Java EE 8 with Microprofile. Curren Version: " + version;
  }

}
