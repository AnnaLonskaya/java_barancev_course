package ua.annalonskaya.addressbook.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import ua.annalonskaya.addressbook.appmanager.ApplicationManager;

/**
 * Created by Admin on 18.02.2017.
 */
public class TestBase {

  protected final ApplicationManager app = new ApplicationManager(BrowserType.FIREFOX);

  @BeforeMethod
  public void setUp() throws Exception {
    app.init();

  }

  @AfterMethod
  public void tearDown() {
    app.stop();
  }

}
