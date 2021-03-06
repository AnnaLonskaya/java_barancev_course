package ua.annalonskaya.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ua.annalonskaya.addressbook.model.ContactData;
import ua.annalonskaya.addressbook.model.Contacts;
import ua.annalonskaya.addressbook.model.GroupData;
import ua.annalonskaya.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactRemoveFromGroupTests extends TestBase {

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
  public void testContactRemoveFromGroup() {
    app.goTo().gotoHomePage();
    Groups groups = app.db().groups();
    Contacts before = app.db().contacts();
    ContactData removedFromGroupContact = before.iterator().next();
    if (removedFromGroupContact.getGroups().size()==0) {
      removedFromGroupContact = app.contact().addContactToGroup(removedFromGroupContact, groups.iterator().next());
      app.goTo().gotoHomePage();
    }
    Groups contactInGroupsBeforeDeleting = app.db().contactInGroup();
    GroupData deletedGroup = contactInGroupsBeforeDeleting.iterator().next();
    app.contact().removeContactFromGroup(removedFromGroupContact, deletedGroup);
    app.goTo().gotoHomePage();

    assertThat(app.contact().count(),equalTo(before.size()));
    Groups contactInGroupsAfterDeleting = app.db().contactInGroup();
    assertThat(contactInGroupsAfterDeleting, equalTo(contactInGroupsBeforeDeleting.without(deletedGroup)));
  }

}
