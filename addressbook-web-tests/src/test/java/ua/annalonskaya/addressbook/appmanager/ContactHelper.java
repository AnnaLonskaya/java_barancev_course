package ua.annalonskaya.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ua.annalonskaya.addressbook.model.ContactData;
import ua.annalonskaya.addressbook.model.Contacts;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void submitContactCreation() {
    click(By.xpath("(//input[@name='submit'])[2]"));
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFname());
    type(By.name("lastname"), contactData.getLname());
    type(By.name("company"), contactData.getCompany());
    type(By.name("address"), contactData.getAddress());
    type(By.name("email"), contactData.getEmail());
    type(By.name("home"), contactData.getPhone());
    select(By.name("bday"), contactData.getDay());
    select(By.name("bmonth"), contactData.getMonth());
    type(By.name("byear"), contactData.getYear());

    if (creation) {
      select(By.name("new_group"), contactData.getGroup());
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }

  private void select(By locator, int index) {
    (new Select(wd.findElement(locator))).selectByIndex(index);
  }

  private void select(By locator, String text) {
    (new Select(wd.findElement(locator))).selectByVisibleText(text);
  }

  public void initContactCreation() {
    click(By.linkText("add new"));
  }

  public void selectContact(int index) {
    wd.findElements(By.xpath("//input[@name='selected[]']")).get(index).click();
  }

  public void submitContactDeletion() {
    click(By.xpath("(//input[@type='button'])[2]"));
  }

  public void initContactModification(int index) {
   wd.findElements(By.xpath("//img[@alt='Edit']")).get(index).click();
  }

  public void initContactModificationById(int id) {
    WebElement checkbox = wd.findElement(By.cssSelector("input[value='" + id + "']"));
    WebElement row = checkbox.findElement(By.xpath("./../.."));
    List<WebElement> cells = row.findElements(By.tagName("td"));
    cells.get(7).findElement(By.tagName("a")).click();
  }

  public void initContactDeletionById(int id) {
    WebElement checkbox = wd.findElement(By.cssSelector("input[value='" + id + "']"));
    checkbox.click();
  }

  public void submitContactModification() {
    click(By.name("update"));
  }

  public void create (ContactData contact, boolean chooseGroup) {
    initContactCreation();
    fillContactForm(contact, chooseGroup);
    submitContactCreation();
    contactsCache = null;
  }

  public void modify(ContactData contact) {
    initContactModificationById(contact.getId());
    fillContactForm(contact, false);
    submitContactModification();
    contactsCache = null;
  }

  public void delete(int index) {
    selectContact(index);
    submitContactDeletion();
    acceptAlert();
  }

  public void delete(ContactData contact) {
    initContactDeletionById(contact.getId());
    submitContactDeletion();
    acceptAlert();
    contactsCache = null;
  }

  public boolean isThereAContact() {
    return (isElementPresent(By.name("entry")));
  }

  private Contacts contactsCache = null;

  public List<ContactData> list() {
    List<ContactData> contacts = new ArrayList<ContactData>();
    List<WebElement> elements = wd.findElements(By.name("entry"));
    for (WebElement element : elements) {
      List<WebElement> cells = element.findElements(By.tagName("td"));
      int id =  Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("value"));
      String lname = cells.get(1).getText();
      String fname = cells.get(2).getText();
      contacts.add(new ContactData().withId(id).withLname(lname).withFname(fname));
    }
    return contacts;
  }

  public Contacts all() {
    if (contactsCache != null) {
      return new Contacts(contactsCache);
    }
    contactsCache = new Contacts();
    List<WebElement> elements = wd.findElements(By.name("entry"));
    for (WebElement element : elements) {
      List<WebElement> cells = element.findElements(By.tagName("td"));
      int id =  Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("value"));
      String lname = cells.get(1).getText();
      String fname = cells.get(2).getText();
      contactsCache.add(new ContactData().withId(id).withLname(lname).withFname(fname));
    }
    return contactsCache;
  }

}
