package ua.annalonskaya.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ua.annalonskaya.addressbook.model.GroupData;

import java.util.Set;


public class GroupCreationTests extends TestBase{

  @Test
  public void testGroupCreation() {
    app.goTo().groupPage();
    Set<GroupData> before = app.group().all();
    GroupData group = new GroupData().withName("test2");
    app.group().create(group);
    Set<GroupData> after = app.group().all();
    Assert.assertEquals(after.size(), before.size() + 1);

    // поток объектов типа GroupData превращаем в поток идентификаторов(чисел) при помощи функции mapToInt(). Она в качестве параметра принимает
    // описание того, как объект преобразуется в число. Т.е. в качестве параметра в mapToInt() мы передаем анонимную ф-цию, к-ая будет последовательно
    // применяться ко всем эл-ам потока и каждый из них будет последовательно преобразовываться в число. В рез-те мы из потока объектов типа GroupData
    // получаем поток целых чисел. (g) -> g.getId() - анониманя ф-ция, к-ая в качестве параметра принимает группу, а в качестве рез-та выдает идентификатор
    // этой группы, т.е. преобразует объект в число. Мы получили поток целых чисел. У него есть ф-ция mаx(), к-ая уже не принимает никаких параметров,
    // т.к. сравнивать числа Java умеет сама. Вызываем метод mаx() и преобразуем рез-т в целое число getAsInt(). Это и будет максимальный среди
    // идентификаторов всех групп.
    group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt());
    before.add(group);
    Assert.assertEquals(before, after);
  }

}


// Вычисление максимального идентификатора. Превращаем список в поток. По потоку пробегается ф-ция-сравниватель и находит максимальный эл-т (сравниваются объекты типа GroupData
// путем сравнения их идентификаторов). На выходе этой ф-ции будет группа с макс-ым идентификатором и мы берем её идентификатор. Это лямда-выражение
//
//    group.withId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
//    before.add(group);
//    Comparator<? super GroupData> byId = (g1, g2)-> Integer.compare(g1.getId(), g2.getId());
//    before.sort(byId);
//    after.sort(byId);
//    Assert.assertEquals(before, after);
//  }
//
//}


//  int max = 0;
//    for (GroupData g : after) {
//            if (g.getId() > max) {
//            max = g.getId();
//            }
//            }
//или:
//        int max1 = after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId();
//        group.withId(max1);