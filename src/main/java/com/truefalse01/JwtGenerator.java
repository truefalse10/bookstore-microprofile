package com.truefalse01;

import static com.nimbusds.jose.JOSEObjectType.JWT;
import static com.nimbusds.jose.JWSAlgorithm.RS256;
import static com.nimbusds.jwt.JWTClaimsSet.parse;
import static java.lang.Thread.currentThread;

import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.SignedJWT;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonPatch;
import javax.json.JsonWriter;
import javax.json.stream.JsonParser;

import org.eclipse.microprofile.jwt.Claims;

public class JwtGenerator {
  /**
   * creates token with predefined claims.
   *
   * @param jsonResource location of default json payload
   * @return generated json token
   */
  public static String generateJwtString(String jsonResource) throws Exception {
    byte[] byteBuffer = new byte[16384];
    currentThread()
        .getContextClassLoader()
        .getResource(jsonResource)
        .openStream()
        .read(byteBuffer);

    JsonParser parser = Json.createParser(new ByteArrayInputStream(byteBuffer));
    parser.next();
    JsonObject jwtJson = parser.getObject();

    long currentTimeInSecs = (System.currentTimeMillis() / 1000);
    long expirationTime = currentTimeInSecs + 1000;

    JsonPatch patch = Json.createPatchBuilder()
        .add("/" + Claims.iat.name(), Json.createValue(currentTimeInSecs))
        .add("/" + Claims.auth_time.name(), Json.createValue(currentTimeInSecs))
        .add("/" + Claims.exp.name(), Json.createValue(expirationTime))
        .build();

    jwtJson = patch.apply(jwtJson);

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
