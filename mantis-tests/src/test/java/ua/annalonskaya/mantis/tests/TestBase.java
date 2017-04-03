package ua.annalonskaya.mantis.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ua.annalonskaya.mantis.appmanager.ApplicationManager;

public class TestBase {

// Чтобы использовать один и  тот же экземпляр браузера для всех тестов:
// в TestNG Suite всегда одни единственный, он соответствует одному запуску и он может состоять из нескольких тестов (тогда используем конфигурационный файл).
// Suite -> Test -> Class -> Method

  // Делаем ссылку на ApplicationManager общей для всех тестов. Для этого её нужно объявить static. Переменная static становится независимой,
// она не является частью какого-то объекта. Это самостоятельная глобальная переменная.
  protected static final ApplicationManager app = new ApplicationManager(System.getProperty("browser", BrowserType.FIREFOX));


  @BeforeSuite
  public void setUp() throws Exception {
    app.init();
  }

  @AfterSuite(alwaysRun = true)
  public void tearDown() {
    app.stop();
  }

}


