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
    type(By.name("home"), contactData.getHomePhone());
    select(By.name("bday"), contactData.getDay());
    select(By.name("bmonth"), contactData.getMonth());
    type(By.name("byear"), contactData.getYear());
    attach(By.name("photo"), contactData.getPhoto());  // передаем в качестве параметра не просто рез-т выполнения getPhoto(), нужно преобразовать его
                                                     // в строку, к-ая содержит полный абсолютный путь к этому файлу

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
//    WebElement checkbox = wd.findElement(By.cssSelector(String.format("input[value='%s']", id))); метод String.format()
    WebElement row = checkbox.findElement(By.xpath("./../.."));
    List<WebElement> cells = row.findElements(By.tagName("td"));
    cells.get(7).findElement(By.tagName("a")).click();

//    wd.findElement(By.xpath(String.format("//input[@value='%s']/../../td[8]/a", id))).click();
//    wd.findElement(By.xpath(String.format("//tr[.//input[@value='%s']]/td[8]/a", id))).click();
//    wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
  }

  public void initContactDeletionById(int id) {
    WebElement checkbox = wd.findElement(By.cssSelector("input[value='" + id + "']"));
    checkbox.click();
  }

  public void initContactDetailedPageById(int id) {
    WebElement checkbox = wd.findElement(By.cssSelector("input[value='" + id + "']"));
    WebElement row = checkbox.findElement(By.xpath("./../.."));
    List<WebElement> cells = row.findElements(By.tagName("td"));
    cells.get(6).findElement(By.tagName("a")).click();
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

  public void goToDetailedPage (ContactData contact) {
    initContactDetailedPageById(contact.getId());
  }

  public boolean isThereAContact() {
    return (isElementPresent(By.name("entry")));
  }

  public int count() {
    return  wd.findElements(By.name("selected[]")).size();
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
      String address = cells.get(3).getText();
      String allEmails = cells.get(4).getText();
      String allPhones = cells.get(5).getText();
      contactsCache.add(new ContactData().withId(id).withLname(lname).withFname(fname).withAddress(address)
              .withAllEmails(allEmails).withAllPhones(allPhones));
//      String[] phones = cells.get(5).getText().split("\n");// split() разбить строку на фрагменты и в качестве разделителя исп-ть регулярные выражения), "\n" - перевод строки
//      contactsCache.add(new ContactData().withId(id).withLname(lname).withFname(fname).withAddress(address)
//              .withEmail(email).withHomePhone(phones[0]).withMobilePhone(phones[1]).withWorkPhone(phones[2]));
    }
    return contactsCache;
  }

  public ContactData infoFromEditForm(ContactData contact) {
    initContactModificationById(contact.getId());
    String fname = wd.findElement(By.name("firstname")).getAttribute("value");
    String lname = wd.findElement(By.name("lastname")).getAttribute("value");
    String address = wd.findElement(By.name("address")).getText();
    String email = wd.findElement(By.name("email")).getAttribute("value");
    String email2 = wd.findElement(By.name("email2")).getAttribute("value");
    String email3 = wd.findElement(By.name("email3")).getAttribute("value");
    String home = wd.findElement(By.name("home")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    wd.navigate().back();
    return new ContactData().withId(contact.getId()).withFname(fname).withLname(lname).withAddress(address)
            .withEmail(email).withEmail2(email2).withEmail3(email3).withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work);
  }

  public ContactData infoFromDetailedPage(ContactData contact) {
    initContactDetailedPageById(contact.getId());
    String allContactDetails = wd.findElement(By.xpath("//div[@id='content']")).getText();
    wd.navigate().back();
    return new ContactData().withId(contact.getId()).withAllContactDetails(allContactDetails);
  }

}
