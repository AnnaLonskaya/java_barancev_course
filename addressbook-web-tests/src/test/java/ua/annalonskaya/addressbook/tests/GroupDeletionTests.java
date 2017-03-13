package ua.annalonskaya.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ua.annalonskaya.addressbook.model.GroupData;

import java.util.Set;

public class GroupDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().groupPage();
    if (app.group().all().size() == 0) {
      app.group().create(new GroupData().withName("test1"));
    }
  }

  @Test
  public void testGroupDeletion() {
    Set<GroupData> before = app.group().all();
    //  Используем объект GroupData. Чтобы извлечь элемент из множества сначала получаем итератор iterator(), к-ый позволяет
    // последовательно перебирать эл-ты, а потом вызываем метод next(), он вернет какой-нибудь первый попавшийся эл-т множества
    GroupData deletedGroup = before.iterator().next();
    app.group().delete(deletedGroup);
    Set<GroupData> after = app.group().all();
    Assert.assertEquals(after.size(), before.size() - 1);

    before.remove(deletedGroup);
    Assert.assertEquals(before, after);
  }

//  @Test
//  public void testGroupDeletion() {
//    List<GroupData> before = app.group().list();
//    int index = before.size() - 1;
//    app.group().delete(index);
//    List<GroupData> after = app.group().list();
//    Assert.assertEquals(after.size(), before.size() - 1);
//
//    before.remove(index);
//    Assert.assertEquals(before, after);
//  }

}
