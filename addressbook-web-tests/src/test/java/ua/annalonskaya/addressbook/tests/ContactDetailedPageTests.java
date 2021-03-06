package ua.annalonskaya.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ua.annalonskaya.addressbook.model.ContactData;
import ua.annalonskaya.addressbook.model.GroupData;
import ua.annalonskaya.addressbook.model.Groups;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDetailedPageTests extends TestBase {

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
  public void testContactDetailedPage() {
    app.goTo().gotoHomePage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
    ContactData contactInfoFromDetailedPAge = app.contact().infoFromDetailedPage(contact);
    assertThat(contactInfoFromDetailedPAge.getAllContactDetails(), equalTo(mergeContactDetails(contactInfoFromEditForm)));
  }

  private String mergeContactDetails(ContactData contact) {
    return Arrays.asList(contact.getFname() + " ", contact.getLname()+"\n\n", contact.getCompany()+"\n",
            contact.getAddress()+"\n\n", "H: " + contact.getHomePhone(),"\nM: " + contact.getMobilePhone(),
            "\nW: " + contact.getWorkPhone() + "\n\n", contact.getEmail()+"\n", contact.getEmail2() + "\n",
            contact.getEmail3() + "\n\n", "Birthday " + contact.getDay(), ". " + contact.getMonth()+  " ",
            contact.getYear() + " (", + contact.getAge() + ")")
            .stream().filter((s) -> !s.equals("")).collect(Collectors.joining());
  }

}

