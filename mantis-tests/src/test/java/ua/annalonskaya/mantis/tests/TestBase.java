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

  public Boolean isIssueOpen(int issueId) throws IOException, ServiceException {
    Issue issue = app.soap().getIssue(issueId);
    if (issue.getStatus().equals("resolved") || (issue.getStatus().equals("closed"))) {
      return false;
    }
    return true;
  }

  public void skipIfNotFixed(int issueId) throws IOException, ServiceException {
    if (isIssueOpen(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }

  @AfterSuite(alwaysRun = true)
  public void tearDown() throws IOException {
    app.stop();
    app.ftp().restore("config_inc.php.bak", "config_inc.php");
  }

}

