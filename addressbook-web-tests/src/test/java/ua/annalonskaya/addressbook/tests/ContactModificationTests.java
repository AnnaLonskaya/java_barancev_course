package ua.annalonskaya.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ua.annalonskaya.addressbook.model.ContactData;

import java.util.Set;

public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.contact().all().size() == 0) {
      app.contact().create(new ContactData().withLname("Sunny").withFname("Irina").withCompany("Incom").withAddress("Street").withEmail("1@mail.ru").withPhone("123456789")
              .withDay(6).withMonth(10).withYear("2000").withGroup( "test1"), true);
    }
  }

  @Test
  public void testContactModification() {
    Set<ContactData> before = app.contact().all();
    ContactData deletedContact = before.iterator().next();
    ContactData contact = new ContactData()
            .withId(deletedContact.getId()).withLname("Sunny").withFname("Irina").withCompany("Incom").withAddress("Street").withEmail("1@mail.ru").withPhone("123456789")
            .withDay(6).withMonth(10).withYear("2000").withGroup( "test1");
    app.contact().modify(contact);
    Set<ContactData> after = app.contact().all();
    Assert.assertEquals(after.size(), before.size());

    before.remove(deletedContact);
    before.add(contact);
    Assert.assertEquals(before, after);
  }

}
