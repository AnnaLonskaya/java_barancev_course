package ua.annalonskaya.addressbook.tests;

import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.*;
import ua.annalonskaya.addressbook.appmanager.ApplicationManager;
import ua.annalonskaya.addressbook.model.ContactData;
import ua.annalonskaya.addressbook.model.Contacts;
import ua.annalonskaya.addressbook.model.GroupData;
import ua.annalonskaya.addressbook.model.Groups;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@Listeners(MyTestListener.class)
public class TestBase {

// Чтобы использовать один и  тот же экземпляр браузера для всех тестов:
// в TestNG Suite всегда одни единственный, он соответствует одному запуску и он может состоять из нескольких тестов (тогда используем конфигурационный файл).
// Suite -> Test -> Class -> Method

  // Делаем ссылку на ApplicationManager общей для всех тестов. Для этого её нужно объявить static. Переменная static становится независимой,
// она не является частью какого-то объекта. Это самостоятельная глобальная переменная.
  protected static final ApplicationManager app = new ApplicationManager(System.getProperty("browser", BrowserType.FIREFOX));

  Logger logger = LoggerFactory.getLogger(TestBase.class); // создаем переменную logger. Создаем объект с помощью LoggerFactory

  @BeforeSuite
  public void setUp(ITestContext context) throws Exception {
    app.init();
    context.setAttribute("app", app);
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

  // чтобы включить эту проверку в настройках конфигурации запуска(Edit Configuration) в VM options добавляем -ea -DverifyUI=true
  public void verifyGroupListInUI() {  // метод для сравнения множества групп. Загружаем два списка групп:
    if (Boolean.getBoolean("verifyUI")) { // проверка условия (если установлено спец-ое системное свойство и оно имеет значение True, то выполняем)
      // Boolean.getBoolean() - эта ф-ция получает системное свойство с заданным именем и автоматически преобраз-ет его в булевскую величину
      Groups dbGroups = app.db().groups();  // один - из БД
      Groups uiGroups = app.group().all();  // второй - из пользоват-го интерфейса
      //т.к. из польз-го интерфейса загруж-ся инф-ция только об именах групп и идентификатор, а из БД-полная инф-ция(хедер, футер) нужно объекты, к-ые загруж-ся
      // из БД упростить(убрать инф-цию о хедерах и футерах). Для этого используем функцион-ое програм-ие - ко всем эл-ам коллекции к-ые загружены из БД применяем
      // ф-цию, к-ая их упрощает. stream()-превращаем список в поток, map() - применить ко все эл-ам потока какую-то ф-цию. (g) - анониманя ф-ция, к-ая
      // принимает на вход группу, а на выходе будет новый объект типа GroupData(new GroupData()) с идентификатором и именем таким же, как у преобразуемого
      // объекта, а хедер и футер будут просто не указаны.Потом собираем при помощи коллектора
      assertThat(uiGroups, equalTo(dbGroups.stream()
              .map((g) -> new GroupData().withId(g.getId()).withName(g.getName()))
              .collect(Collectors.toSet())));
    }
  }

  public void verifyContactListInUI() {
    if (Boolean.getBoolean("verifyUI")) {
      Contacts dbContacts = app.db().contacts();
      Contacts uiContacts = app.contact().all();
      assertThat(uiContacts, equalTo(dbContacts));
    }
  }

}


