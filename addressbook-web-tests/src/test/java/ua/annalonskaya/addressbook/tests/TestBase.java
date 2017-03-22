package ua.annalonskaya.addressbook.tests;

import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import ua.annalonskaya.addressbook.appmanager.ApplicationManager;

import java.lang.reflect.Method;
import java.util.Arrays;

public class TestBase {

// Чтобы использовать один и  тот же экземпляр браузера для всех тестов:
// в TestNG Suite всегда одни единственный, он соответствует одному запуску и он может состоять из нескольких тестов (тогда используем конфигурационный файл).
// Suite -> Test -> Class -> Method

// Делаем ссылку на ApplicationManager общей для всех тестов. Для этого её нужно объявить static. Переменная static становится независимой,
// она не является частью какого-то объекта. Это самостоятельная глобальная переменная.
  protected static final ApplicationManager app = new ApplicationManager(System.getProperty("browser", BrowserType.FIREFOX));

  Logger logger = LoggerFactory.getLogger(TestBase.class); // создаем переменную logger. Создаем объект с помощью LoggerFactory

  @BeforeSuite
  public void setUp() throws Exception {
    app.init();
  }

  @AfterSuite(alwaysRun = true)
  public void tearDown() {
    app.stop();
  }


  @BeforeMethod
  // для логирования нужно узнать метод, к-ый запускается, для этого в метод, к-ый помечен аннотацией @BeforeMethod передаем параметр Method m (он содержит
  // информацию о тестовом методы, к-ый сейчас запускается)
  public void logTestStart(Method m, Object[] p) {  // чтобы выводить инф-цию о параметрах тестовых методов объявляем параметр типа Object[],
    logger.info("Start test " + m.getName() + " with parameters " + Arrays.asList(p));  // к-ый будет содержать параметры тестового метода
  }                                                                                      // массив объектов Object[] преобразовываем в список и выводим


  @AfterMethod(alwaysRun = true)
  // @AfterMethod может не всегда выполниться, если тест завершился не успешно (он будет пропущен)
  public void logTestStop(Method m) {
    logger.info("Stop test " + m.getName());
  }

}


