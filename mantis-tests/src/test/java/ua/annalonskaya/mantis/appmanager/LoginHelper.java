package ua.annalonskaya.mantis.appmanager;

import org.openqa.selenium.By;

public class LoginHelper extends HelperBase {

  public LoginHelper (ApplicationManager app) {
    super(app);
  }

  public void loginAsAdmin() {
    wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
    type(By.name("username"), "administrator");
    type(By.name("password"), "root");
    click(By.cssSelector("input[value='Login']"));
  }

  public void loginAsUser(String username, String password) {
    wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
    type(By.name("username"), username);
    type(By.name("password"), password);
    click(By.cssSelector("input[value='Login']"));
  }

}


