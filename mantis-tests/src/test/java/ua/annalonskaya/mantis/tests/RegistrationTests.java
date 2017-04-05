package ua.annalonskaya.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ua.annalonskaya.mantis.model.MailMessage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class RegistrationTests extends TestBase {

  @BeforeMethod
  public void startMailServer() {
    app.mail().start();
  }

  @Test
  public void testRegistration() throws IOException, MessagingException {
    long now = System.currentTimeMillis(); // ф-ция возвращает текущее время в милисекундах
    String user = String.format("user%s", now);
    String password = "password";
    String email = String.format("user%s@localhost.localdomain", now);
    app.registration().start(user, email);
    List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);  // ожидаем 2 письма 10 секунд (админу и польз-лю)
    String confirmationLink = findConfirmationLink(mailMessages, email); // среди всех писем находим то, к-ое отправлено на адрес email и извлекаем из него ссылку
    app.registration().finish(confirmationLink, password);
    assertTrue(app.newSession().login(user, password));
  }



  // находим среди всех писем то, к-ое отправлено на нужный адрес: исп-ем ф-цию filter(), в к-ую в качестве параметра передается предикат, т.е. ф-ция,
  // возвращающая булевское значение true или false. На вход она принимает объект типа mailMessages и выполнять проверку m.to.equals(email). В рез-те
  // фильтрации в потоке останутся только те сообщения, к-ые отправлены по нужному адресу. Дальше среди них берм первое findFirst(). И теперь из теста
  // этого сообщения нужно извлечь ссылку. Для этого исп-ем регулярные выражения. Подключаем к проекту библиотеку verbalregex и с помощью её строим
  // рег.выражение. Т.е. сначала ищем текст "http://", потом после него должно идти какое-то кол-во непробельных символов(один или больше). И рез-ом
  // явл-ся объект типа VerbalExpression, к-ый внутри содержит построенное рег.выраж.
  private String findConfirmationLink(List<MailMessage> mailMessages, String email) {                           // метод для извлечения ссылки из письма
    MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);                                       // возвращает тот кусок текста, к-ый соотв-ет построенному рег.выражению.
  }

  @AfterMethod (alwaysRun = true) // почтовый сервер будет останавливаться даже в том случае, если тест завершился не успешно
  public void stopMailServer() {
    app.mail().stop();
  }

}
// чтобы mantis узнал о том, что почту необходимо доставлять именно сюда в этот почтовый сервер, в конфигурационный файл багтреккера config_inc.php добавляем
// две опции  $g_phpMailer_method = PHPMAILER_METHOD_SMTP; (способ доставки почты по протоколу SMTP) и $g_smtp_host = 'localhost' (дрес доставки почты);