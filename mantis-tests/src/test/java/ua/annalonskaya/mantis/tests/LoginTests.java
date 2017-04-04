package ua.annalonskaya.mantis.tests;

import org.testng.annotations.Test;
import ua.annalonskaya.mantis.appmanager.HttpSession;

import java.io.IOException;

import static org.testng.Assert.assertTrue;


public class LoginTests extends TestBase {

  @Test
  public void testLogin() throws IOException {
    HttpSession session = app.newSession();
    assertTrue(session.login("administrator", "root"));
    assertTrue(session.isLoggedInAs("administrator"));
  }

}
