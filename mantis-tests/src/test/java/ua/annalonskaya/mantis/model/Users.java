package ua.annalonskaya.mantis.model;


import com.google.common.collect.ForwardingSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Users extends ForwardingSet<UserData> {

  private Set<UserData> delegate;

  public Users(Users users) {
    this.delegate = new HashSet<UserData>(users.delegate);
  }

  public Users() {
    this.delegate = new HashSet<UserData>();
  }

  public Users(Collection<UserData> users) {
    this.delegate = new HashSet<UserData>(users);
  }

  @Override
  protected Set<UserData> delegate() {
    return delegate;
  }

  public Users withAdded(UserData user) {
    Users users = new Users(this);  // в качестве параметра передаем this. Создали копию объекта.
    users.add(user);  // в эту копию добавляем объект, к-ый передан в качестве параметра
    return users;   // возаращаем построенную копию с добпвленной группой.
  }

  // Создаем метод, к-ый будет делать копию, из к-ой удалена какая-то группа.
  public Users without(UserData user) {
    Users users = new Users(this);
    users.remove(user);
    return users;
  }

}

