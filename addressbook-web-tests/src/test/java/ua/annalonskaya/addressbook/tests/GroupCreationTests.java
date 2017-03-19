package ua.annalonskaya.addressbook.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ua.annalonskaya.addressbook.model.GroupData;
import ua.annalonskaya.addressbook.model.Groups;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class GroupCreationTests extends TestBase {

//  @DataProvider  // делаем провайдер тестовых данных. Это специальный метод, к-ый помечается аннотацией @DataProvider. Здесь тестовые данные создаются непосредственно в провайдере
//  public Iterator<Object[]> validGroups() {   // возвращаемое значение этого метода - Iterator<Object[]> - итератор массивов объектов
//    List<Object[]> list = new ArrayList<Object[]>();  // сначала делаем список этих массивов объектов
//    list.add(new Object[] {new GroupData().withName("test1").withHeader("header 1").withtFooter("footer 1")});   // заполняем список тестовыми данными
//    list.add(new Object[] {new GroupData().withName("test2").withHeader("header 2").withtFooter("footer 2")});
//    list.add(new Object[] {new GroupData().withName("test3").withHeader("header 3").withtFooter("footer 3")});
//    return list.iterator();  // возвращается итератор этого списка. Тестовый фреймворк по очереди при помощи итератора из списка вытаскивает один набор
//                             // параметров за другим и запускает тестовый метод несколько раз и помещает полученную инф-цию о рез-ах в отчет
//  }

  @DataProvider  // загружаем тестовые данные из внешнего файла
  public Iterator<Object[]> validGroups() throws IOException {
    List<Object[]> list = new ArrayList<Object[]>();
    BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/groups.csv"))); // В классе Reader нет метода для чтения
    // строчки целиком. Поэтому делаем обертку, вместо обычного Reader исп-ем BufferedReader(обычный Reader заворачиваем в буферизованный)
    String line = reader.readLine();           // readLine() - читает первую строчку и сразу её возвращает (тип возвращ-го значения String)
    while (line != null) {                     // чтобы читать все строчки устраиваем цикл. До тех пор, пока line не равно 0 продолжаем выполнение этого цикла
      String[] split = line.split(";"); // каждую строку делим на части split() и рез-т помещаем в локальную переменную split
      list.add(new Object[]{new GroupData()   // и строим из полученных кусочков объект и добавляем его в список
              .withName(split[0]).withHeader(split[1]).withtFooter(split[2])});
      line = reader.readLine(); // на каждой следующей итерации читаем следующую строчку из того же самого файла.
    }
    return list.iterator();
  }

  @Test (dataProvider = "validGroups")
  public void testGroupCreation(GroupData group) {
    app.goTo().groupPage();
    Groups before = app.group().all();
    app.group().create(group);
    assertThat(app.group().count(),equalTo(before.size() + 1));  // проверка для получения кол-ва групп. Если не совпадает, то следующей проверки не будет.
    Groups after = app.group().all();
    assertThat(after, equalTo(
            before.withAdded(group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));  // выполняем статический импорт (исп-ся для статических глобальных ф-ций)
    //    MatcherAssert.assertThat(after, CoreMatchers.equalTo(before));  // используем класс MatcherAssert. equalTo()-это метод в классе CoreMatchers. Проверяем совпадения двух объектов.
  }

  @Test
  public void testBadGroupCreation() {
    app.goTo().groupPage();
    Groups before = app.group().all();
    GroupData group = new GroupData().withName("test'");
    app.group().create(group);
    assertThat(app.group().count(),equalTo(before.size()));
    Groups after = app.group().all();
    assertThat(after, equalTo(before));
  }

}

// поток объектов типа GroupData превращаем в поток идентификаторов(чисел) при помощи функции mapToInt(). Она в качестве параметра принимает
// описание того, как объект преобразуется в число. Т.е. в качестве параметра в mapToInt() мы передаем анонимную ф-цию, к-ая будет последовательно
// применяться ко всем эл-ам потока и каждый из них будет последовательно преобразовываться в число. В рез-те мы из потока объектов типа GroupData
// получаем поток целых чисел. (g) -> g.getId() - анониманя ф-ция, к-ая в качестве параметра принимает группу, а в качестве рез-та выдает идентификатор
// этой группы, т.е. преобразует объект в число. Мы получили поток целых чисел. У него есть ф-ция mаx(), к-ая уже не принимает никаких параметров,
// т.к. сравнивать числа Java умеет сама. Вызываем метод mаx() и преобразуем рез-т в целое число getAsInt(). Это и будет максимальный среди
// идентификаторов всех групп.
//    group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt());


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