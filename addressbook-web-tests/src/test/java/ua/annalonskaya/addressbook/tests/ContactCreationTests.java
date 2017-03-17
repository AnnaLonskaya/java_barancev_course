package ua.annalonskaya.addressbook.tests;

import org.testng.annotations.Test;
import ua.annalonskaya.addressbook.model.ContactData;
import ua.annalonskaya.addressbook.model.Contacts;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {
    Contacts before = app.contact().all();
    File photo = new File("src/test/resources/icon.jpg");  // создаем объект типа File, относительный путь
    ContactData contact = new ContactData()
            .withLname("Sunny").withFname("Irina").withCompany("Incom").withAddress("Street").withEmail("1@mail.ru").withHomePhone("123456789")
            .withDay(6).withMonth(10).withYear("2000").withGroup("[none]").withPhoto(photo);
    app.contact().create(contact, true);
    assertThat(app.contact().count(),equalTo(before.size() + 1));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
  }

  @Test
  public void testBadContactCreation() {
    Contacts before = app.contact().all();
    ContactData contact = new ContactData()
            .withLname("Sunny").withFname("Irina'").withCompany("Incom").withAddress("Street").withEmail("1@mail.ru").withHomePhone("123456789")
            .withDay(6).withMonth(10).withYear("2000").withGroup( "test2");
    app.contact().create(contact, true);
    assertThat(app.contact().count(),equalTo(before.size()));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(before));
  }

  @Test (enabled = false)  // не запускать
  public void testCurrentDir() {
    File currentDir = new File(".");
    System.out.println(currentDir.getAbsolutePath());  // D:\Automation\Barancev\java_barancev_course\addressbook-web-tests\. (абсолютный путь к директории)
    File photo = new File("src/test/resources/icon.jpg");
    System.out.println(photo.getAbsolutePath()); //  D:\Automation\Barancev\java_barancev_course\addressbook-web-tests\src\test\resources\icon.jpg
    System.out.println(photo.exists());  // true
  }
// когда мы хотим указать относительный путь к какому-то файлу, нужно указывать путь относительно вот этой директории: D:\Automation\Barancev\java_barancev_course\addressbook-web-tests\
}
