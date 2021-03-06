package ua.annalonskaya.addressbook.model;

import com.google.common.collect.ForwardingSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Groups extends ForwardingSet<GroupData> {  // список объектов типа GroupData.

  private Set<GroupData> delegate;


  // Берем множество из существующего объекта, к-ый передан в качестве параметра, строим новое множество, к-ое содержит теже самые эл-ты и присваиваем это
  // новое множество в качестве атрибута в новый создаваемый этим конструктором объект.
  public Groups(Groups groups) {  // конструктор строит копию существующего объекта.
    this.delegate = new HashSet<GroupData>(groups.delegate);
  }

  public Groups() {  // создаем конструктор без параметров
    this.delegate = new HashSet<GroupData>();
  }

  public Groups(Collection<GroupData> groups) {  // создаем конструктор, к-ый по произвольной коллекции строит объект типа Groups
    this.delegate = new HashSet<GroupData>(groups);  // строим новый HashSet (т.е. множество объектов типа GroupData), из коллекции
  }


  @Override
  protected Set<GroupData> delegate() {  // обязательный метод
    return delegate;
  }

  public Groups withAdded (GroupData group) {
    Groups groups = new Groups(this);  // в качестве параметра передаем this. Создали копию объекта.
    groups.add(group);  // в эту копию добавляем объект, к-ый передан в качестве параметра
    return groups;   // возаращаем построенную копию с добпвленной группой.
  }

  // Создаем метод, к-ый будет делать копию, из к-ой удалена какая-то группа.
  public Groups without (GroupData group) {
    Groups groups = new Groups(this);
    groups.remove(group);
    return groups;
  }

}

// Применяем fluent интерфейс к спискам. Создаем класс, к-ый ведет себя как Set(множество), и в него можно добавить свои собственные методы. Для реализации
// этого класса используем библиотеку Guava (сделала компания Google). В ней есть специальный набор вспомогательных классов, к-ые предназначены для построения
// коллекций с расширенным набором методов (это ForwardingSet<> и ForwardingList<>). Библиотека Guava реал-ет шаблон проектирования Декоратор. Суть в том, что
// все вызовы методов делегируются к какому-то объекту(реальному списку или множеству), к-ый вложен внутрь обертки, а также мы можем в этой обертке реализовать
// свои собственные дополнительные методы. Также можно переопределить поведение существующих методов. Наша задача - чтобы стандартные методы делегировались вложенному
// списку или множеству, эту задачу на себя берет класс ForwardingSet<>, а мы добавляем свои собственные методы. 1. Сначала создаем объект, к-му всё будет делегироваться:
//   private Set<GroupData> delegate;
// А метод delegate() возвращает этот объект. Потом добавляем свои собственные методы withAdded()
// Чтобы можно было вытягивать вызовы методов в цепочки и строить из них каскады, нужно чтобы возвращался объект типа groups. Можно добавить в уже существующее
// множество private Set<GroupData> delegate; тот объект, к-ый передан в качестве параметра и вернуть this, т.е. тот объект, в к-ом вызывается метод withAdded().
// Мы делаем по другому, будем делать копию, так что старый объект остается неизменным, а метод withAdded() возвращает новый объект с добавленной новой группой.
// Так мы сможем работать и со старым множеством без добавленной группы и с новым множеством с добавленной группой.