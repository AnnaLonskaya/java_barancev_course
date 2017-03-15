package ua.annalonskaya.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ua.annalonskaya.addressbook.model.ContactData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactPhoneTests extends TestBase{

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.contact().all().size() == 0) {
      app.contact().create(new ContactData().withLname("Sunny").withFname("Irina").withCompany("Incom").withAddress("Street").withEmail("1@mail.ru").withHomePhone("123456789")
              .withDay(6).withMonth(10).withYear("2000").withGroup("[none]"), true);
    }
  }

  @Test
  public void testContactPhones() {
    app.goTo().gotoHomePage();
    ContactData contact = app.contact().all().iterator().next();  // загружаем множество контактов и выбираем контакт случайным образом
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
    assertThat(contact.getHomePhone(), equalTo(cleaned(contactInfoFromEditForm.getHomePhone())));
    assertThat(contact.getMobilePhone(), equalTo(cleaned(contactInfoFromEditForm.getMobilePhone())));
    assertThat(contact.getWorkPhone(), equalTo(cleaned(contactInfoFromEditForm.getWorkPhone())));
  }

  public static String cleaned (String phone) {
    return phone.replaceAll("\\s", "").replaceAll("[-()]", "");  // replaceAll() - заменить все вхождения чего-то на что-то
  }

}

// "\\s" - пробельный символ (пробел, табуляцияб перевод строки).  "[a-z]" - заменить любую букву от a до z
// "[-az]" - заменить "-", букву а и букву z,    ("[-()]" - заменить "-", "(" и ")"