package ua.annalonskaya.addressbook.tests;

import org.testng.annotations.Test;
import ua.annalonskaya.addressbook.model.ContactData;

/**
 * Created by Admin on 21.02.2017.
 */
public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification() {
    app.getContactHelper().initContactAction();
    app.getContactHelper().initContactModification();
    app.getContactHelper().fillContactForm(new ContactData("Svetlana", "Qwerty", "Incom", "Street",
            "1@mail.ru", "123456789", 6, 10, "2000", null), false);
    app.getContactHelper().submitContactModification();
  }

}
