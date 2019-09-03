package com.truefalse01;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.metrics.MetricRegistry;

@Path("/user")
public class UserResource {
  private static final Logger log = Logger.getLogger(UserResource.class.getName());

  @Inject
  @ConfigProperty(name = "mp.jwt.verify.issuer")
  String issuer;

  @Inject
  MetricRegistry metricRegistry;

  /**
   * takes user and returns token if valid credentials.
   * 
   * @param user User
   * @return
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public String login(User user) throws Exception {
    String metricId = "failed_logins_" + user.name;
    if ("Dieter".equalsIgnoreCase(user.name) && "123Geheim".equals(user.password)) {
      String token = JwtGenerator.generateJwtString(user, issuer);
      metricRegistry.remove(metricId);
      return token;
    } else {
      log.info("Failed login attempt by user: " + user.name);
      metricRegistry.counter(metricId).inc();
      throw new NotAuthorizedException("User not authorized!");
    }
  }

}
