package ua.annalonskaya.mantis.model;

// т.к. разные почтовые сервера могут исп-ть разный формат представления почты, поэтому создаем свой собственный модельный объект типа MailMessage, к-ый содержит два поля:
public class MailMessage {

  public String to;      // кому пришло письмо
  public String text;    // текст письма

  public MailMessage(String to, String text) {
    this.to = to;
    this.text = text;
  }

}
