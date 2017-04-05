package ua.annalonskaya.mantis.appmanager;

import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;
import ua.annalonskaya.mantis.model.MailMessage;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

// после регистрации на указанный почтовый адрес отправляется письмо. Делаем собственный почтовый сервер (запускаем почтовый сервер, к-ый встроен в тесты).
// Для этого исп-ем помощник для работы с почтой MailHelper. Нужно подключить библиотеку subethamail.
public class MailHelper {

  private ApplicationManager app;
  private final Wiser wiser;

  public MailHelper(ApplicationManager app) { // при инициализации создается объект типа Wiser(это почтовый сервер)
    this.app = app;
    wiser = new Wiser();
  }

  public List<MailMessage> waitForMail(int count, long timeout) throws MessagingException, IOException {  // т.к. почта приходит не мгновенно, содаем метод
    long start = System.currentTimeMillis();   // для ожидания, к-ый имеет два параметра: count-кол-во писем ,к-ые должны прийти и timeout - время ожидания
    while (System.currentTimeMillis() < start + timeout) {
      if(wiser.getMessages().size() >= count) {
        return wiser.getMessages().stream().map((m) -> toModelMail(m)).collect(Collectors.toList());
      }
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    throw new Error("No mail :(");
  }

  public static MailMessage toModelMail(WiserMessage m) {
    try {
      MimeMessage mm = m.getMimeMessage();
      return new MailMessage(mm.getAllRecipients()[0].toString(), (String) mm.getContent());
    } catch (MessagingException e) {
      e.printStackTrace();
      return null;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public void start() {
    wiser.start();
  }

  public void stop() {
    wiser.stop();
  }
}
