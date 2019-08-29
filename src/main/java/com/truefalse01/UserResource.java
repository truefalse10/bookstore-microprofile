package com.truefalse01;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("/user")
public class UserResource {
  @Inject
  @ConfigProperty(name = "mp.jwt.verify.issuer")
  String issuer;

  /**
   * takes user and returns token if valid credentials.
   * 
   * @param user User
   * @return
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public String login(User user) throws Exception {
    if ("Dieter".equalsIgnoreCase(user.name) && "123Geheim".equals(user.password)) {
      String token = JwtGenerator.generateJwtString(user, issuer);
      return token;
    } else {
      throw new NotAuthorizedException("User not authorized!");
    }
  }

}
