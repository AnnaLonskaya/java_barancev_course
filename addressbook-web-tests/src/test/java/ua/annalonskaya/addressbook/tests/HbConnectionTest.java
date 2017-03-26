package ua.annalonskaya.addressbook.tests;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ua.annalonskaya.addressbook.model.GroupData;

import java.util.List;

public class HbConnectionTest {

  private SessionFactory sessionFactory;   // поле типа SessionFactory

  @BeforeClass
  protected void setUp() throws Exception {    // метод, к-ый устанавливает соединение с БД (код берем из http://docs.jboss.org/hibernate/orm/current/quickstart/html_single/#hibernate-gsg-tutorial-basic-test)
    // это стандартная процедура инициализации, во время к-ой будет прочитан конфигурац-ый файл, из него извлечена вся инф-ция о БД, проверено, что есть
    // доступ к ней, извлечена инф-ция о меппингах, т.е. о привязках объектов к таблицам и проверено, что эта привязка корректна (т.е. есть все необходимые
    // таблицы и поля в них).
    // A SessionFactory is set up once for an application!
    final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure() // configures settings from hibernate.cfg.xml
            .build();
    try {
      sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
    }
    catch (Exception e) {
      e.printStackTrace();
      // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
      // so destroy it manually.
      StandardServiceRegistryBuilder.destroy( registry );
    }
  }

  @Test   // извлекаем инф-цию о группах из БД (пример кода: Example 6. Obtaining a list of entities)
  public void testHbConnection() {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    List<GroupData> result = session.createQuery("from GroupData").list();
    for (GroupData group : result) {
      System.out.println(group);
    }
    session.getTransaction().commit();
    session.close();
  }

}

// <!--На сайте http://webcache.googleusercontent.com/search?q=cache:http://docs.jboss.org/hibernate/orm/current/quickstart/html_single/&gws_rd=cr&ei=JMrWWOOcOIKzsQHYmrz4Cg
// скачиваем hibernate-tutorials.zip : annotations->src->test->resources->hibernate.cfg.xml и копируем полностью в этот файл и адаптируем под нашу БД -->

