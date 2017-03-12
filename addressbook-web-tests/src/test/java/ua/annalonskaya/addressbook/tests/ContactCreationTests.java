package ua.annalonskaya.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ua.annalonskaya.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

/**
 * Created by Admin on 18.02.2017.
 */
public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {
    List<ContactData> before = app.contact().list();
    ContactData contact = new ContactData("Sunny", "Irina", "Incom", "Street",
            "1@mail.ru", "123456789", 6, 10, "2000", "test1");
    app.contact().create(contact, true);
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), before.size() + 1);

    contact.setId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
    before.add(contact);
    Comparator<? super ContactData> byId = (g1, g2)-> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);
  }

}
