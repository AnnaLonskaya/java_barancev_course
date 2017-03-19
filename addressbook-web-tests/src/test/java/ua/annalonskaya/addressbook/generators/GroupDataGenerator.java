package ua.annalonskaya.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.thoughtworks.xstream.XStream;
import ua.annalonskaya.addressbook.model.GroupData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

// создали пакет generators для генерации тестовых данных и сохранения их в файл. В нем создаем класс GroupDataGenerator, к-ый будет генерировать инф-цию
// о группах. Это должен быть запускаемый класс.
// Подключем библиотеку JCommander для обработки опций командной строки (count и file, они теперь будут атрибутами).

public class GroupDataGenerator {

  @Parameter(names = "-c", description = "Group count")  // кол-во групп
  public int count;

  @Parameter(names = "-f", description = "Target file")
  public String file;  // JCommander напрямую не поддерживает работу с файлами (только числа, строки, списки)

  @Parameter(names = "-d", description = "Data format")  // формат xml или csv ( -c 10 -f src/test/resources/groups.xml -d xml)
  public String format;

  public static void main(String[] args) throws IOException {
    GroupDataGenerator generator = new GroupDataGenerator();  // создаем обект текущего класса
    JCommander jCommander = new JCommander(generator);       // создаем объект типа JCommander и помещаем его в локальную переменную. Параметр типа generator-объект, в к-ом должны быть заполнены соответствующие атрибуты (count и file)
    try {
      jCommander.parse(args);  // вызываем метод parse() и передаем в качестве параметра аргументы args
    } catch (ParameterException ex) {
      jCommander.usage();  // выводим на консоль с помощью метода usage() текст, к-ый содержит инф-цию о доступный опциях
      return;
    }
    generator.run();
  }

  private void run() throws IOException {
    List<GroupData> groups = generateGroups(count);
    if (format.equals("csv")) {
      saveAsCsv(groups, new File(file));
    } else if (format.equals("xml")){
      saveAsXml(groups, new File(file));
    } else {
      System.out.println("Unrecognized format " + format);
    }
  }

  private void saveAsXml(List<GroupData> groups, File file) throws IOException {  // 1-ый параметр это список групп, к-ый нужно сохранять, 2-ой - файл, в к-ый нужно сохранять
    XStream xstream = new XStream();     // создаем объект типа XStream
    xstream.processAnnotations(GroupData.class);  // для данных типа GroupData исп-ем тег group (меняем название тега <ua.annalonskaya.addressbook.model.GroupData>)
    String xml = xstream.toXML(groups);  // в качестве параметра передаем тот объект, к-ый нужно сериализовать, т.е. превратить из объектного представления в
    Writer writer = new FileWriter(file);                                                                                       // строчку в формате xml
    writer.write(xml);
    writer.close();
  }

  private void saveAsCsv(List<GroupData> groups, File file) throws IOException {  // сохраняем список в файл. Каждая группа будет сохраняться в виде отдельной
    Writer writer = new FileWriter(file);       // открываем файл на запись        //строки, к-ая состоит из 3-х частей: имя, header, footer и они разделены ";"
    for (GroupData group : groups) {             // проходим в цикле по всем группам, к-ые находятся в списке groups
      writer.write(String.format("%s;%s;%s\n", group.getName(), group.getHeader(), group.getFooter()));   // каждую из них записываем, "\n" перевод на следующую строку
    }
    writer.close();       // закрываем файл
  }

  private List<GroupData> generateGroups(int count) {
    List<GroupData> groups = new ArrayList<GroupData>();
    for ( int i = 0; i < count; i++) {
      groups.add(new GroupData().withName(String.format("test %s", i)).withHeader(String.format("header %s", i))
              .withtFooter(String.format("footer %s", i)));
    }
    return groups;
  }
}
// -c 10 -f src/test/resources/groups.csv



//public class GroupDataGenerator {
//
//  public static void main(String[] args) throws IOException { // возможность передавать данные при запуске программы из командной строки через массив параметров (String[] args)
//    int count = Integer.parseInt(args[0]);  //в качестве параметроы будут передаваться кол-во групп и путь к файлу. Integer.parseInt-преобразуем строку в число
//    File file = new File(args[1]);
//
//    List<GroupData> groups = generateGroups(count);              // Разделим задачу на две части: 1. Генерация данных
//    save(groups, file);                                         // 2. Сохранение этих данных в файл
//
//  }
//
//  private static void save(List<GroupData> groups, File file) throws IOException {  // сохраняем список в файл. Каждая группа будет сохраняться в виде отдельной
//    Writer writer = new FileWriter(file);       // открываем файл на запись        //строки, к-ая состоит из 3-х частей: имя, header, footer и они разделены ";"
//    for (GroupData group : groups) {             // проходим в цикле по всем группам, к-ые находятся в списке groups
//      writer.write(String.format("%s;%s%s\n", group.getName(), group.getHeader(), group.getFooter()));   // каждую из них записываем, "\n" перевод на следующую строку
//    }
//    writer.close();       // закрываем файл
//  }
//
//  private static List<GroupData> generateGroups(int count) {
//    List<GroupData> groups = new ArrayList<GroupData>();
//    for ( int i = 0; i < count; i++) {
//      groups.add(new GroupData().withName(String.format("test %s", i)).withHeader(String.format("header %s", i))
//              .withtFooter(String.format("footer %s", i)));
//    }
//    return groups;
//  }
//}

// открываем Edit Configurations, в поле Programm arguments вводим: 10 src/test/resources/groups.csv (кол-во групп и путь к файлу)
// также меняем Working directory D:\Automation\Barancev\java_barancev_course\addressbook-web-tests