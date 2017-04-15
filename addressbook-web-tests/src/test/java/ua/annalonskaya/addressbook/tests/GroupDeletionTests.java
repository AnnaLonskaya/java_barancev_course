package ua.annalonskaya.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ua.annalonskaya.addressbook.model.GroupData;
import ua.annalonskaya.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().groups().size() == 0) {  // делаем проверку условия через прямое обращение к БД
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test1"));
    }
  }

  @Test
  public void testGroupDeletion() {
    Groups before = app.db().groups();
    //  Используем объект GroupData. Чтобы извлечь элемент из множества сначала получаем итератор iterator(), к-ый позволяет
    // последовательно перебирать эл-ты, а потом вызываем метод next(), он вернет какой-нибудь первый попавшийся эл-т множества
    GroupData deletedGroup = before.iterator().next();
    app.goTo().groupPage();
    app.group().delete(deletedGroup);
    assertThat(app.group().count(),equalTo(before.size() - 1));
    Groups after = app.db().groups();
    assertThat(after, equalTo(before.without(deletedGroup)));
    verifyGroupListInUI();
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
