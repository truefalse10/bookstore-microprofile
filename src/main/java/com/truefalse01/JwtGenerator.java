package com.truefalse01;

import static com.nimbusds.jose.JOSEObjectType.JWT;
import static com.nimbusds.jose.JWSAlgorithm.RS256;
import static com.nimbusds.jwt.JWTClaimsSet.parse;
import static java.lang.Thread.currentThread;

import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.SignedJWT;

import java.io.StringWriter;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonWriter;

import org.eclipse.microprofile.jwt.Claims;

public class JwtGenerator {
  /**
   * creates token with predefined claims.
   *
   * @return generated json token
   */
  public static String generateJwtString(User user, String issuer) throws Exception {
    long currentTimeInSecs = (System.currentTimeMillis() / 1000);
    long expirationTime = currentTimeInSecs + 30 * 60; // expires in 30 min

    // TODO: put groups in database and get from user
    JsonArray groups = Json.createArrayBuilder()
        .add("chief")
        .add("hacker")
        .build();

    JsonObject jwtJson = Json.createObjectBuilder()
        // user specific claims
        .add(Claims.iss.name(), issuer)
        .add(Claims.jti.name(), "42") // unique identifier for token TODO: make unique
        .add(Claims.sub.name(), user.name)
        .add(Claims.upn.name(), "duke") // user principal name in java.security.Principal
        .add(Claims.groups.name(), groups)
        // general claims
        .add(Claims.iat.name(), Json.createValue(currentTimeInSecs)) // issue time
        .add(Claims.exp.name(), Json.createValue(expirationTime)) // expiration time
        .build();

    StringWriter stringWriter = new StringWriter();
    JsonWriter writer = Json.createWriter(stringWriter);
    writer.writeObject(jwtJson);
    String jsonString = stringWriter.toString();
    SignedJWT signedJwt = new SignedJWT(
        new JWSHeader.Builder(RS256).keyID("/privateKey.pem").type(JWT).build(),
        parse(jsonString));

    signedJwt.sign(new RSASSASigner(readPrivateKey("privateKey.pem")));

    return signedJwt.serialize();
  }

  /**
   * reads private key from resources folder and returns as PrivateKey.
   *
   * @param resourceName filename
   * @return
   */
  public static PrivateKey readPrivateKey(String resourceName) throws Exception {
    byte[] byteBuffer = new byte[16384];
    int length = currentThread()
        .getContextClassLoader()
        .getResource(resourceName)
        .openStream()
        .read(byteBuffer);

    String key = new String(byteBuffer, 0, length).replaceAll("-----BEGIN (.*)-----", "")
        .replaceAll("-----END (.*)----", "")
        .replaceAll("\r\n", "")
        .replaceAll("\n", "")
        .trim();

    return KeyFactory.getInstance("RSA")
        .generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(key)));
  }
}
