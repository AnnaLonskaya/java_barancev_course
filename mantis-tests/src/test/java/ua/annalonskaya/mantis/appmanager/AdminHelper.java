package ua.annalonskaya.mantis.appmanager;

import org.openqa.selenium.By;
import ua.annalonskaya.mantis.model.UserData;

public class AdminHelper extends HelperBase {

  public AdminHelper(ApplicationManager app) {
    super(app);
  }

  public void initManageUsers() {
    click(By.xpath("//a[contains(@href,'manage_user_page')]"));
  }

  public void initUserModificationById(int id) {
    wd.findElement(By.xpath(String.format("//tr[contains(@class,'row-')]//a[contains(@href,'user_id=%s')]", id))).click();
  }

  public void resetPassword() {
    click(By.cssSelector("input[value='Reset Password']"));
  }

  public void finished(String confirmationLink, String password) {
    wd.get(confirmationLink);
    type(By.name("password"), password);
    type(By.name("password_confirm"), password);
    click(By.cssSelector("input[value='Update User']"));
  }


}
