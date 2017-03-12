package ua.annalonskaya.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ua.annalonskaya.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class GroupModificationTests extends TestBase{

  @BeforeMethod
  public void ensurePreconditions() {
    app.getNavigationHelper().gotoGroupPage();
    if (! app.getGroupHelper().isThereAGroup()) {
      app.getGroupHelper().createGroup(new GroupData("test1", null, null));
    }
  }

  @Test
  public void testGroupModification() {
    List<GroupData> before = app.getGroupHelper().getGroupList();
    int index = before.size() - 1;
    GroupData group = new GroupData(before.get(index).getId(),"test1", "test4", "test5");
    app.getGroupHelper().modifyGroup(index, group);
    List<GroupData> after = app.getGroupHelper().getGroupList();
    Assert.assertEquals(after.size(), before.size());

    before.remove(index);
    before.add(group);
    // Comparator<? super GroupData> byId - локальная переменная
    // (g1, g2)-> Integer.compare(g1.getId(), g2.getId()) - лямбда-выражение - анонимная ф-ция, к-ая на вход принимает два параметра
    // g1, g2 (две группы, к-ые мы будем сравнивать) и выполняет сравнение идентификаторов
    Comparator<? super GroupData> byId = (g1, g2)-> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);  // у списка есть метод sort(), к-ый в качестве параметра принимает компаратор (т.е. описание правил сравнения объектов). Сортируем старый список
    after.sort(byId);  // сортируем новый список
    Assert.assertEquals(before, after);  // теперь можно сравнивать два списка, т.к. они упорядочены по Id
//    Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after)); раньше использовали множества для сравнения ,т.к. списки были не упорядоченные
  }

}
