package ua.annalonskaya.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ua.annalonskaya.addressbook.model.ContactData;

import java.util.List;

/**
 * Created by Admin on 21.02.2017.
 */
public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification() {
    if (! app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("Svetlana", "Qwerty", "Incom", "Street",
              "1@mail.ru", "123456789", 6, 10, "2000", "test1"), true);
    }
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().selectContact(1);
    app.getContactHelper().initContactModification();
    app.getContactHelper().fillContactForm(new ContactData("Anna", "Qwerty", "Incom", "Street",
            "1@mail.ru", "123456789", 6, 10, "2000", null), false);
    app.getContactHelper().submitContactModification();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size());
  }

}
