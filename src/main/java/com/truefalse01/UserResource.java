package com.truefalse01;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Path("/user")
public class UserResource {
  @Inject
  @ConfigProperty(name = "mp.jwt.verify.publickey")
  String publicKeyString;

  @Inject
  @ConfigProperty(name = "mp.jwt.verify.privatekey")
  String privateKeyString;

  @Inject
  @ConfigProperty(name = "mp.jwt.verify.issuer")
  String issuer;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public String login(User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
    if ("Dieter".equalsIgnoreCase(user.name) && "123Geheim".equals(user.password)) {
      try {
        byte[] decodedPrivateKey = Base64.getDecoder().decode(privateKeyString);
        PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decodedPrivateKey));

        byte[] decodedPublicKey = Base64.getDecoder().decode(publicKeyString);
        PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decodedPublicKey));


        Algorithm algorithmRS = Algorithm.RSA256((RSAPublicKey) publicKey, (RSAPrivateKey) privateKey);
        String token = JWT.create()
          .withIssuer(issuer)
          .withSubject(user.name)
          .sign(algorithmRS);
        return token;
      } catch (JWTCreationException e) {
        throw new NotAuthorizedException("creation of token failed", e);
      }
    } else throw new NotAuthorizedException("User not authorized!");
  }

}
