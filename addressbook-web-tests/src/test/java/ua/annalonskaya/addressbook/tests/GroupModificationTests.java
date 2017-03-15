package ua.annalonskaya.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ua.annalonskaya.addressbook.model.GroupData;
import ua.annalonskaya.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class GroupModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().groupPage();
    if (app.group().all().size() == 0) {
      app.group().create(new GroupData().withName("test1"));
    }
  }

  @Test
  public void testGroupModification() {
    Groups before = app.group().all();
    GroupData modifiedGroup = before.iterator().next();
    GroupData group = new GroupData()
            .withId(modifiedGroup.getId()).withName("test1").withHeader("test4").withtFooter("test5");
    app.group().modify(group);
    assertThat(app.group().count(),equalTo(before.size()));
    Groups after = app.group().all();
    assertThat(after, equalTo(before.without(modifiedGroup).withAdded(group)));
  }
}


//    before.remove(index);
//    before.add(group);
//    // Comparator<? super GroupData> byId - локальная переменная
//    // (g1, g2)-> Integer.compare(g1.getId(), g2.getId()) - лямбда-выражение - анонимная ф-ция, к-ая на вход принимает два параметра
//    // g1, g2 (две группы, к-ые мы будем сравнивать) и выполняет сравнение идентификаторов
//    Comparator<? super GroupData> byId = (g1, g2)-> Integer.compare(g1.getId(), g2.getId());
//    before.sort(byId);  // у списка есть метод sort(), к-ый в качестве параметра принимает компаратор (т.е. описание правил сравнения объектов). Сортируем старый список
//    after.sort(byId);  // сортируем новый список
//    Assert.assertEquals(before, after);  // теперь можно сравнивать два списка, т.к. они упорядочены по Id
////    Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after)); раньше использовали множества для сравнения ,т.к. списки были не упорядоченные
//  }


