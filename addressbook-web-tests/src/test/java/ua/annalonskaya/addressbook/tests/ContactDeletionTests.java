package ua.annalonskaya.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ua.annalonskaya.addressbook.model.ContactData;

import java.util.List;

/**
 * Created by Admin on 21.02.2017.
 */
public class ContactDeletionTests extends TestBase {

  @Test
  public void testContactDeletion() {
    if (! app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("Svetlana", "Qwerty", "Incom", "Street",
              "1@mail.ru", "123456789", 6, 10, "2000", null), false);
    }
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().selectContact(0);
    app.getContactHelper().submitContactDeletion();
    app.getContactHelper().acceptAlert();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size() - 1);

    before.remove(0);
    Assert.assertEquals(before, after);
  }

}
