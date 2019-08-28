package com.truefalse01;

import static com.nimbusds.jose.JOSEObjectType.JWT;
import static com.nimbusds.jose.JWSAlgorithm.RS256;
import static com.nimbusds.jwt.JWTClaimsSet.parse;
import static java.lang.Thread.currentThread;
import static net.minidev.json.parser.JSONParser.DEFAULT_PERMISSIVE_MODE;

import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.SignedJWT;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

import org.eclipse.microprofile.jwt.Claims;

public class JwtGenerator {
  /**
   * creates token with predefined claims.
   * @param jsonResource location of default json payload
   * @return generated json token
   */
  public static String generateJwtString(String jsonResource) throws Exception {
    byte[] byteBuffer = new byte[16384];
    currentThread().getContextClassLoader()
        .getResource(jsonResource)
        .openStream()
        .read(byteBuffer);

    JSONParser parser = new JSONParser(DEFAULT_PERMISSIVE_MODE);
    JSONObject jwtJson = (JSONObject) parser.parse(byteBuffer);

    long currentTimeInSecs = (System.currentTimeMillis() / 1000);
    long expirationTime = currentTimeInSecs + 1000;

    jwtJson.put(Claims.iat.name(), currentTimeInSecs);
    jwtJson.put(Claims.auth_time.name(), currentTimeInSecs);
    jwtJson.put(Claims.exp.name(), expirationTime);

    SignedJWT signedJwt = new SignedJWT(new JWSHeader.Builder(RS256)
        .keyID("/privateKey.pem")
        .type(JWT)
        .build(), parse(jwtJson));

    signedJwt.sign(new RSASSASigner(readPrivateKey("privateKey.pem")));

    return signedJwt.serialize();
  }

  /**
   * reads private key from resources folder and returns as PrivateKey.
   * @param resourceName filename
   * @return
   */
  public static PrivateKey readPrivateKey(String resourceName) throws Exception {
    byte[] byteBuffer = new byte[16384];
    int length = currentThread().getContextClassLoader()
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
