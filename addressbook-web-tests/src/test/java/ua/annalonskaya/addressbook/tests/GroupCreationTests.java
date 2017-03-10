package ua.annalonskaya.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ua.annalonskaya.addressbook.model.GroupData;

import java.util.HashSet;
import java.util.List;


public class GroupCreationTests extends TestBase{

  @Test
  public void testGroupCreation() {
    app.getNavigationHelper().gotoGroupPage();
    List<GroupData> before = app.getGroupHelper().getGroupList();
    GroupData group = new GroupData("test1", "test4", "test5");
    app.getGroupHelper().createGroup(group);
    List<GroupData> after = app.getGroupHelper().getGroupList();
    Assert.assertEquals(after.size(), before.size() + 1);

// Вычисление максимального идентификатора. Превращаем список в поток. По потоку пробегается ф-ция-сравниватель и находит максимальный эл-т (сравниваются объекты типа GroupData
// путем сравнения их идентификаторов). На выходе этой ф-ции будет группа с макс-ым идентификатором и мы берем её идентификатор.

    group.setId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
    before.add(group);
    Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after));
  }

}




//  int max = 0;
//    for (GroupData g : after) {
//            if (g.getId() > max) {
//            max = g.getId();
//            }
//            }
//или:
//        int max1 = after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId();
//        group.setId(max1);