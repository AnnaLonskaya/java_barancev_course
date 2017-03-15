package ua.annalonskaya.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ua.annalonskaya.addressbook.model.GroupData;
import ua.annalonskaya.addressbook.model.Groups;

import java.util.ArrayList;
import java.util.List;

public class GroupHelper extends HelperBase {

  public GroupHelper(WebDriver wd) {
    super(wd);
  }

  public void returnToGroupPage() {
    click(By.linkText("group page"));
  }

  public void submitGroupCreation() {
    click(By.name("submit"));
  }

  public void fillGroupForm(GroupData groupData) {
    type(By.name("group_name"), groupData.getName());
    type(By.name("group_header"), groupData.getHeader());
    type(By.name("group_footer"), groupData.getFooter());
  }

  public void deleteSelectedGroups() {
    click(By.name("delete"));
  }

  public void selectGroup(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();
  }

  public void selectGroupById(int id) {
    wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
  }

  public void initGroupCreation() {
    click(By.name("new"));
  }

  public void initGroupModification() {
    click(By.name("edit"));
  }

  public void submitGroupModification() {
    click(By.name("update"));
  }

  public void create (GroupData group) {
    initGroupCreation();
    fillGroupForm(group);
    submitGroupCreation();
    groupsCache = null;
    returnToGroupPage();
  }

//  public void modify(int index, GroupData group) {
//    selectGroup(index);
//    initGroupModification();
//    fillGroupForm(group);
//    submitGroupModification();
//    returnToGroupPage();
//  }

  public void modify(GroupData group) {
    selectGroupById(group.getId());
    initGroupModification();
    fillGroupForm(group);
    submitGroupModification();
    groupsCache = null;
    returnToGroupPage();
  }

  public void delete(int index) {
    selectGroup(index);
    deleteSelectedGroups();
    returnToGroupPage();
  }

  public void delete(GroupData group) {
    selectGroupById(group.getId());
    deleteSelectedGroups();
    groupsCache = null;
    returnToGroupPage();
  }

  public boolean isThereAGroup() {
    return (isElementPresent(By.name("selected[]")));
  }

  public int count() {
    return  wd.findElements(By.name("selected[]")).size();
  }

  private Groups groupsCache = null; // создаем новое поле аттрибут GroupHelper, к-ый мзначально равен null (пустой)

  public List<GroupData> list() {
    List<GroupData> groups = new ArrayList<GroupData>();
    List<WebElement> elements = wd.findElements(By.cssSelector("span.group"));
    for (WebElement element : elements) {
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));  // Integer.parseInt - преобразовать строку в число
      String name = element.getText();
      groups.add(new GroupData().withId(id).withName(name));
    }
    return groups;
  }

  public Groups all() {
    if (groupsCache != null) {
      return new Groups(groupsCache);  // возвращаем не сам кэш, а его копию, чтобы его нечаянно никто не испортил
    }
    groupsCache = new Groups();
    List<WebElement> elements = wd.findElements(By.cssSelector("span.group"));
    for (WebElement element : elements) {
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));  // Integer.parseInt - преобразовать строку в число
      String name = element.getText();
      groupsCache.add(new GroupData().withId(id).withName(name));
    }
    return new Groups(groupsCache);
  }

//  public Set<GroupData> all() {  // метод возвращает множество
//    Set<GroupData> groups = new HashSet<GroupData>();  // HashSet - популярная реализация множеств
//    List<WebElement> elements = wd.findElements(By.cssSelector("span.group"));
//    for (WebElement element : elements) {
//      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));  // Integer.parseInt - преобразовать строку в число
//      String name = element.getText();
//      groups.add(new GroupData().withId(id).withName(name));
//    }
//    return groups;
//  }

}
