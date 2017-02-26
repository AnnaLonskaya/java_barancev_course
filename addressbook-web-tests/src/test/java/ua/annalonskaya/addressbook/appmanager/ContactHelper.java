package ua.annalonskaya.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import ua.annalonskaya.addressbook.model.ContactData;

/**
 * Created by Admin on 20.02.2017.
 */
public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void submitContactCreation() {
    click(By.xpath("(//input[@name='submit'])[2]"));
  }

  public void fillContactForm(ContactData contactData) {
    type(By.name("firstname"), contactData.getFname());
    type(By.name("lastname"), contactData.getLname());
    type(By.name("company"), contactData.getCompany());
    type(By.name("address"), contactData.getAddress());
    type(By.name("email"), contactData.getEmail());
    type(By.name("home"), contactData.getPhone());
    select(By.name("bday"), contactData.getDay());
    select(By.name("bmonth"), contactData.getMonth());
    type(By.name("byear"), contactData.getYear());
  }

  private void select(By locator, int index) {
    (new Select(wd.findElement(locator))).selectByIndex(index);
  }

  public void initContactCreation() {
    click(By.linkText("add new"));
  }

  public void initContactAction() {
    click(By.xpath("(//input[@type='checkbox'])[1]"));
  }

  public void submitContactDeletion() {
    click(By.xpath("(//input[@type='button'])[2]"));
  }

  public void initContactModification() {
    click(By.xpath("//img[@alt='Edit']"));
  }

  public void submitContactModification() {
    click(By.name("update"));
  }

}
