package ua.annalonskaya.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ua.annalonskaya.addressbook.model.ContactData;
import ua.annalonskaya.addressbook.model.GroupData;

import java.util.List;

/**
 * Created by Admin on 18.02.2017.
 */
public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().createContact(new ContactData("Anna", "Qwerty", "Incom", "Street",
            "1@mail.ru", "123456789", 6, 10, "2000", "annn"), true);
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size() + 1);
  }

}
