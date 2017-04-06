package ua.annalonskaya.mantis.appmanager;

import org.hibernate.Session;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ua.annalonskaya.mantis.model.UserData;
import ua.annalonskaya.mantis.model.Users;

import java.util.List;


public class DbHelper {

  private ApplicationManager app;
  private final org.hibernate.SessionFactory sessionFactory;

  public DbHelper(ApplicationManager app) {
    // A SessionFactory is set up once for an application!
    this.app = app;
    final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

  }

  public Users users() {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    List<UserData> result = session.createQuery("from UserData").list();
    for (UserData user : result) {
      System.out.println(user);
    }
    session.getTransaction().commit();
    session.close();
    return new Users(result);
  }

}
