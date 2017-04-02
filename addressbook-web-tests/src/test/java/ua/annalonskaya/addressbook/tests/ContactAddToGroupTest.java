package ua.annalonskaya.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ua.annalonskaya.addressbook.model.ContactData;
import ua.annalonskaya.addressbook.model.Contacts;
import ua.annalonskaya.addressbook.model.GroupData;
import ua.annalonskaya.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddToGroupTest extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test1"));
    }
    if (app.db().contacts().size() == 0) {
      Groups groups = app.db().groups();
      app.goTo().gotoHomePage();
      app.contact().create(new ContactData().withLname("Sunny").withFname("Irina").withCompany("Incom")
              .withAddress("Street").withEmail("1@mail.ru").withHomePhone("123456789")
              .withDay(6).withMonth("May").withYear("2000").inGroup(groups.iterator().next()));
    }
  }

  @Test
  public void testContactAddToGroup() {
    app.goTo().gotoHomePage();
    Groups groups = app.db().groups();
    Contacts before = app.db().contacts();
    ContactData addedToGroupContact = before.iterator().next();
    if (addedToGroupContact.getGroups().size() == app.db().groups().size()) {
      app.goTo().groupPage();
      GroupData group = new GroupData().withName("test3");
      app.group().create(group);
      app.goTo().gotoHomePage();
      app.contact().addToGroupByName(addedToGroupContact, group);
    } else {
      app.contact().addToGroup(addedToGroupContact, groups.iterator().next());
    }
    app.goTo().gotoHomePage();

    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(before));
    Contacts dr = app.db().contactInGroup(addedToGroupContact, groups.iterator().next());
    assertThat(app.db().contactInGroup(addedToGroupContact, groups.iterator().next()), equalTo(after));  // вот здесь я не знаю, как правильно сравнить
  }

}
