package ua.annalonskaya.addressbook.tests;

import org.testng.annotations.Test;
import ua.annalonskaya.addressbook.model.GroupData;
import ua.annalonskaya.addressbook.model.Groups;

import java.sql.*;

public class DbConnectionTest {

  @Test   // тест проверяет, что мы можем звлечь из БД какую-то инф-цию
  public void testDbConnection() {
    // устанавливаем соединение с базой данных. Для этого исп-ся DriverManager, к-ый нах-ся в пакете java.sql, он входит в стандартную библ-ку Java.
    // DriverManager по адресу БД, к-ый передается в метод getConnection() догадывается какой именно драйвер должен исп-ся для установления соединения с БД
    // автоматически (главное правильно указать адрес БД).
    Connection conn = null;
    try {
      conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/addressbook?serverTimezone=UTC&user=root&password=");
      Statement st = conn.createStatement();  // теперь извлекаем из БД какую-то инф-цию. Для этого создаем объект типа  Statement
      ResultSet rs = st.executeQuery("select group_id, group_name, group_header, group_footer from group_list");// в качестве параметра передаем запрос к БД
      // создаем локальную пер-ую rs типа ResultSet (это некий аналог коллекции, это набор строк, к-ый извлекается из таблицы)
      Groups groups = new Groups(); // создаем объект типа Groups и будем добавлять созданные объекты в него и в конце после завершения цикла получаем полную коллекцию объектов
      while (rs.next()) {                     // пока есть еще какие-то эл-ты в этом множестве рез-ов делаем:
        groups.add(new GroupData().withId(rs.getInt("group_id")).withName(rs.getString("group_name"))
                .withHeader(rs.getString("group_header")).withtFooter(rs.getString("group_footer")));
      }
      // чтобы не было потери ресурсов нужно соединение с БД закрывать, ресурсы после использования нужно освобождать:
      rs.close();                  // закрываем ResultSet, т.е. мы больше не собираемся читать из него какие-то данные и можно освободить память
      st.close();                  // закрываем Statement, т.е. мы не собираемся больше выполнять никакие запросы
      conn.close();                // закрываем соединение с БД

      System.out.println(groups);  // выводим коллекцию объектов на консоль

    } catch (SQLException ex) {
      // handle any errors
      System.out.println("SQLException: " + ex.getMessage());
      System.out.println("SQLState: " + ex.getSQLState());
      System.out.println("VendorError: " + ex.getErrorCode());
    }
  }
}
