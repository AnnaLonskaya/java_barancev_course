package ua.annalonskaya.mantis.appmanager;


import org.openqa.selenium.WebDriver;

public class RegistrationHelper {

  private final ApplicationManager app;
  private WebDriver wd;

  public RegistrationHelper(ApplicationManager app) {
    this.app = app;
    wd = app.getDriver();  // просим ссылку на драйвер у ApplicationManager. Чтобы инициализация драйвера не происходила слишком рано делаем "ленивую
  }                      // инициализацию". Создаем метод getDriver, к-ый будет иниц-ть драйвер в момент перовго обращения


  public void start(String username, String email) {
    wd.get(app.getProperty("web.baseUrl") + "/signup_page.php");
  }
}
