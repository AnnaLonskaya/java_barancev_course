package ua.annalonskaya.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ua.annalonskaya.addressbook.model.ContactData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddressTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().contacts().size() == 0) {
      app.contact().create(new ContactData().withLname("Sunny").withFname("Irina").withCompany("Incom").withAddress("Street").withEmail("1@mail.ru").withHomePhone("123456789")
              .withDay(6).withMonth(10).withYear("2000").withGroup("[none]"), true);
    }
  }

  @Test
  public void testContactAddress() {
    app.goTo().gotoHomePage();
    ContactData contact = app.contact().all().iterator().next();  // загружаем множество контактов и выбираем контакт случайным образом
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
    assertThat(contact.getAddress(), equalTo(contactInfoFromEditForm.getAddress()));
  }

}
