package ua.annalonskaya.addressbook.generators;

import ua.annalonskaya.addressbook.model.GroupData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

// создали пакет generators для генерации тестовых данных и сохранения их в файл. В нем создаем класс GroupDataGenerator, к-ый будет генерировать инф-цию
// о группах. Это должен быть запускаемый класс.

public class GroupDataGenerator {

  public static void main(String[] args) throws IOException { // возможность передавать данные при запуске программы из командной строки через массив параметров (String[] args)
    int count = Integer.parseInt(args[0]);  //в качестве параметроы будут передаваться кол-во групп и путь к файлу. Integer.parseInt-преобразуем строку в число
    File file = new File(args[1]);

    List<GroupData> groups = generateGroups(count);              // Разделим задачу на две части: 1. Генерация данных
    save(groups, file);                                         // 2. Сохранение этих данных в файл

  }

  private static void save(List<GroupData> groups, File file) throws IOException {  // сохраняем список в файл. Каждая группа будет сохраняться в виде отдельной
    Writer writer = new FileWriter(file);       // открываем файл на запись        //строки, к-ая состоит из 3-х частей: имя, header, footer и они разделены ";"
    for (GroupData group : groups) {             // проходим в цикле по всем группам, к-ые находятся в списке groups
      writer.write(String.format("%s;%s%s\n", group.getName(), group.getHeader(), group.getFooter()));   // каждую из них записываем, "\n" перевод на следующую строку
    }
    writer.close();       // закрываем файл
  }

  private static List<GroupData> generateGroups(int count) {
    List<GroupData> groups = new ArrayList<GroupData>();
    for ( int i = 0; i < count; i++) {
      groups.add(new GroupData().withName(String.format("test %s", i)).withHeader(String.format("header %s", i))
              .withtFooter(String.format("footer %s", i)));
    }
    return groups;
  }
}

// открываем Edit Configurations, в поле Programm arguments вводим: 10 src/test/resources/groups.csv (кол-во групп и путь к файлу)
// также меняем Working directory D:\Automation\Barancev\java_barancev_course\addressbook-web-tests