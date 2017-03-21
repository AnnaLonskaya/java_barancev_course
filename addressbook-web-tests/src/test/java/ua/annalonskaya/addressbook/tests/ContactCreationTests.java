package ua.annalonskaya.addressbook.tests;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ua.annalonskaya.addressbook.model.ContactData;
import ua.annalonskaya.addressbook.model.Contacts;
import ua.annalonskaya.addressbook.model.GroupData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validContactsFromXml() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.xml")))) {
      String xml = "";
      String line = reader.readLine();
      while (line != null) {
        xml += line;
        line = reader.readLine();
      }
      XStream xStream = new XStream();
      xStream.processAnnotations(ContactData.class);
      List<ContactData> contacts = (List<ContactData>) xStream.fromXML(xml);
      return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }
  }

  @DataProvider
  public Iterator<Object[]> validContactsFromJson() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")))) {
      String json = "";
      String line = reader.readLine();
      while (line != null) {
        json += line;
        line = reader.readLine();
      }
      Gson gson = new Gson();
      List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>() {
      }.getType());
      return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }
  }

  @Test (dataProvider = "validContactsFromJson")
  public void testContactCreation(ContactData contact) {
    Contacts before = app.contact().all();
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
    File currentDir = new File(".");  // узнаем какая текущая директория
    System.out.println(currentDir.getAbsolutePath());  // D:\Automation\Barancev\java_barancev_course\addressbook-web-tests\. (абсолютный путь к директории)
    File photo = new File("src/test/resources/icon.jpg");
    System.out.println(photo.getAbsolutePath()); //  D:\Automation\Barancev\java_barancev_course\addressbook-web-tests\src\test\resources\icon.jpg
    System.out.println(photo.exists());  // true
  }
// когда мы хотим указать относительный путь к какому-то файлу, нужно указывать путь относительно вот этой директории: D:\Automation\Barancev\java_barancev_course\addressbook-web-tests\
}
