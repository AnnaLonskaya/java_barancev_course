package ua.annalonskaya.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.thoughtworks.xstream.XStream;
import ua.annalonskaya.addressbook.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {

  @Parameter(names = "-c", description = "Contact count")
  public int count;

  @Parameter(names = "-f", description = "Target file")
  public String file;

  @Parameter(names = "-d", description = "Data format")
  public String format;

  public static void main(String[] args) throws IOException {
    ContactDataGenerator generator = new ContactDataGenerator();
    JCommander jCommander = new JCommander(generator);
    try {
      jCommander.parse(args);
    } catch (ParameterException ex) {
      jCommander.usage();
      return;
    }
    generator.run();
  }

  private void run() throws IOException {
    List<ContactData> contacts = generateContacts(count);
    if (format.equals("csv")) {
      saveAsCsv(contacts, new File(file));
    } else if (format.equals("xml")) {
      saveAsXml(contacts, new File(file));
    } else {
      System.out.println("Unrecognized format " + format);
    }
  }

  private void saveAsXml(List<ContactData> contacts, File file) throws IOException {
    XStream xstream = new XStream();
    xstream.processAnnotations(ContactData.class);
    String xml = xstream.toXML(contacts);
    Writer writer = new FileWriter(file);
    writer.write(xml);
    writer.close();
  }

  private void saveAsCsv(List<ContactData> contacts, File file) throws IOException {
    Writer writer = new FileWriter(file);
    for (ContactData contact : contacts) {
      writer.write(String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s\n", contact.getLname(), contact.getFname(),
              contact.getCompany(), contact.getAddress(), contact.getEmail(), contact.getEmail2(), contact.getEmail3(),
              contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone(), contact.getGroup(), contact.getPhoto()));
    }
    writer.close();
  }

  private List<ContactData> generateContacts(int count) {
    List<ContactData> contacts = new ArrayList<ContactData>();
    for (int i = 0; i < count; i++) {
      contacts.add(new ContactData().withLname(String.format("Lname %s", i)).withFname(String.format("Fname %s", i))
              .withCompany(String.format("Company %s", i)).withAddress(String.format("Address %s", i))
              .withEmail(String.format("Email %s", i)).withEmail2(String.format("Email2 %s", i))
              .withEmail3(String.format("Email3 %s", i)).withHomePhone(String.format("HomePhone %s", i))
              .withMobilePhone(String.format("MobilePhone %s", i)).withWorkPhone(String.format("WorkPhone %s", i))
              .withGroup("[none]").withPhoto(new File("src/test/resources/icon.jpg")));

    }
    return contacts;
  }

}
