import static org.junit.Assert.assertTrue;

import com.truefalse01.PingResource;

import org.junit.Test;

public class PingResourceTest {

  @Test
  public void testPing() {
    String responseRegex = "^Hello from Java EE(.+)";
    PingResource pingRequest = new PingResource();
    String response = pingRequest.ping();
    assertTrue(response.matches(responseRegex));
  }
}
