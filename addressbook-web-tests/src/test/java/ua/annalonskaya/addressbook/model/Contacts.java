package ua.annalonskaya.addressbook.model;

import com.google.common.collect.ForwardingSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Contacts extends ForwardingSet<ContactData> {

  private Set<ContactData> delegate;

  public Contacts(Contacts contacts) {
    this.delegate = new HashSet<ContactData>(contacts.delegate);
  }

  public Contacts() {
    this.delegate = new HashSet<ContactData>();
  }

  public Contacts(Collection<ContactData> contacts) {
    this.delegate = new HashSet<ContactData>(contacts);
  }

  @Override
  protected Set<ContactData> delegate() {
    return delegate;
  }

  public Contacts withAdded (ContactData contact) {
    Contacts contacts = new Contacts(this);  // в качестве параметра передаем this. Создали копию объекта.
    contacts.add(contact);  // в эту копию добавляем объект, к-ый передан в качестве параметра
    return contacts;   // возаращаем построенную копию с добпвленной группой.
  }

  // Создаем метод, к-ый будет делать копию, из к-ой удалена какая-то группа.
  public Contacts without (ContactData contact) {
    Contacts contacts = new Contacts(this);
    contacts.remove(contact);
    return contacts;
  }

}
