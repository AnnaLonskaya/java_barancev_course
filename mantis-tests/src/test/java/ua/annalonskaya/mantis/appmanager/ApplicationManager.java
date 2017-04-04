package ua.annalonskaya.mantis.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ApplicationManager {

  private final Properties properties;
  private WebDriver wd;

  private String browser;
  private RegistrationHelper registrationHelper;

  public ApplicationManager(String browser) {
    this.browser = browser;
    properties = new Properties();  // создаем объект типа Properties
  }

  public void init() throws IOException {
    String target = System.getProperty("target", "local");  // это часть имени конфигурац-го файла, исп-ем local в качестве дефолтного значения
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));  // Загружаем свойства
  }

  public void stop() {
    if (wd != null) {
      wd.quit();
    }
  }

  public HttpSession newSession() {  // этот метод будет инициализировать помощника при каждом обращении(т.к. он инициализируется мгновенно мы можем
    return new HttpSession(this);   // открывать таких сессий сколько угодно) и можно открывать сразу несколько сессий от имени тестировщика, от имени администратора и т.д.
  }

  public String getProperty (String key) { // в качестве параметра метод принимает имя свойства ,к-ое надо извлечь
    return properties.getProperty(key);
  }

  public RegistrationHelper registration() {  // метод возвращает объект типа RegistrationHelper
    if (registrationHelper == null) {  // иниц-ем его при первом обращении к методу registration()
      registrationHelper = new RegistrationHelper(this); // в качестве параметра передаем this, т.е. ссылку на ApplicationManager (менеджер нанимает помощника и передает )
    }                                                         //  ему ссылку на самого себя
    return registrationHelper;
  }

  public WebDriver getDriver() {  // чтобы инициализация была ленивой, переносим её в этот метод
    if (wd == null) {  // если драйвер не проинициализирован, то иниц-ем его, а потом возвращаем, а если проиниц-н, то делать ничего не надо
      if (browser.equals (BrowserType.FIREFOX)) {
        wd = new FirefoxDriver();
      } else if (browser.equals (BrowserType.CHROME)) {
        wd = new ChromeDriver();
      } else if (browser.equals (BrowserType.IE)) {
        wd = new InternetExplorerDriver();
      }

      wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
      wd.get(properties.getProperty("web.baseUrl"));
    }
    return wd;
  }

}
