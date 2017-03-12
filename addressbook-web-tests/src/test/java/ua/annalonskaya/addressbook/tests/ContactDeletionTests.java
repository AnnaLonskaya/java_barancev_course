package ua.annalonskaya.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ua.annalonskaya.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.contact().list().size() == 0) {
      app.contact().create(new ContactData("Svetlana", "Qwerty", "Incom", "Street",
              "1@mail.ru", "123456789", 6, 10, "2000", "test1"), true);
    }
  }

  @Test
  public void testContactDeletion() {
    List<ContactData> before = app.contact().list();
    int index = 0;
    app.contact().delete(index);
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), before.size() - 1);

    before.remove(0);
    Assert.assertEquals(before, after);
  }

}
