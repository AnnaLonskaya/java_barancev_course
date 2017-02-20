package ua.annalonskaya.addressbook.tests;

import org.testng.annotations.Test;
import ua.annalonskaya.addressbook.model.ContactData;

/**
 * Created by Admin on 18.02.2017.
 */
public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {
    app.getContactHelper().initContactCreation();
    app.getContactHelper().fillContactForm(new ContactData("Anna", "Qwerty", "Incom", "Street",
            "1@mail.ru", "123456789", 6, 10, "2000"));
    app.getContactHelper().submitContactCreation();
  }

}
