import static org.junit.Assert.assertTrue;

import com.truefalse01.PingResource;

import org.junit.Test;

public class PingResourceTest {

  @Test
  public void testPing() {
    // TODO: currently the config property VERSION is null in our test
    // somehow the microprofile-config.properties is not evaluated.
    // Even if we override it with System.setProperty("VERSION", "0.0.1")
    // it returns null. Maybe its because we are not starting a embedded
    // payara server? Do we need a jee testing framework?
    String responseRegex = "^Hello from Java EE(.+)";
    PingResource pingRequest = new PingResource();
    String response = pingRequest.ping();
    assertTrue(response.matches(responseRegex));
  }
}
