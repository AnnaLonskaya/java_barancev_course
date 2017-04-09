package ua.annalonskaya.mantis.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ua.annalonskaya.mantis.appmanager.ApplicationManager;
import ua.annalonskaya.mantis.model.Issue;

import javax.xml.rpc.ServiceException;
import java.io.File;
import java.io.IOException;

public class TestBase {

  protected static final ApplicationManager app = new ApplicationManager(System.getProperty("browser", BrowserType.FIREFOX));

  @BeforeSuite
  public void setUp() throws Exception {
    app.init();
    app.ftp().upload(new File("src/test/resources/config_inc.php"), "config_inc.php", "config_inc.php.bak");
  }

  public void skipIfNotFixed(int id) throws IOException, ServiceException {
    if (isIssueOpen()) {
      throw new SkipException("Ignored because of issue " + id);
    }
  }

  public Boolean isIssueOpen() throws IOException, ServiceException {
    Issue next = app.soap().getIssue();
    if (!next.getStatus().equals("resolved")) {
      return true;
    } return false;
   }

  @AfterSuite(alwaysRun = true)
  public void tearDown() throws IOException {
    app.stop();
    app.ftp().restore("config_inc.php.bak", "config_inc.php");
  }

}


