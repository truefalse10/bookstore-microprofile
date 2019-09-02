import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.truefalse01.JwtGenerator;
import com.truefalse01.User;

import java.security.PrivateKey;
import org.junit.Test;

public class JwtGeneratorTest {

  @Test
  public void testJwtGenerate() throws Exception {
    String jwtStructureRegex = "(.+\\..+\\..+)";
    String issuer = "truefalse01";
    User testUser = new User("Dieter", "123Geheim");
    String jwt = JwtGenerator.generateJwtString(testUser, issuer);
    // TODO: currently we are only checking for a token that has the
    // structure of a valid jwt. It would be better to actually
    // decrypt the token and check for valid claims
    assertTrue(jwt.matches(jwtStructureRegex));
  }

  @Test
  public void testReadPrivateKey() throws Exception {
    PrivateKey pk = JwtGenerator.readPrivateKey("privateKey.pem");
    assertNotNull(pk);
  }

}