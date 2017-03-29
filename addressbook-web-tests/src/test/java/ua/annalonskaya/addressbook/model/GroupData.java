package ua.annalonskaya.addressbook.model;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@XStreamAlias("group") // аннотация для создания xml файлов (исп-ем тег group)
@Entity  // эта аннотация объявляет класс GroupData привязанным к БД (hibernate)
@Table(name = "group_list")  // уквзываем название таблицы, в к-ой хранится инф-ция об объектах такого типа  (hibernate)
public class GroupData {
  @XStreamOmitField  // пропустить это поле, не сохранять его в формате xml
  @Id  // атрибут id исп-ся как идентификатор, поэтому ему присваивается особая аннотация (hibernate)
  @Column(name = "group_id") // привязка к столбцу таблицы
  private int id = Integer.MAX_VALUE;

  @Expose // gson библиотека и её аннотация, помечаем поля, к-ые должны быть сериализованы (записаться в json файл)
  @Column(name = "group_name")
  private String name;  // убираем ключевое слово final, делаем их модифицируемыми, чтобы их можно было менять, после того, как конструктор отработал

  @Expose
  @Column(name = "group_header")
  @Type(type = "text") // добавляем описание типа. Это поле многострочное и в БД оно хранится по другому (не так как name, там удалось автоматически сделать преобразование типа)
  private String header;

  @Expose
  @Column(name = "group_footer")
  @Type(type = "text")
  private String footer;

  public int getId() {
    return id;
  }

  public GroupData withId (int id) {  //  // withId (сеттер). меняем возвращаемое значение метода. Метод будет возвращать объект, в к-ом он вызван
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public GroupData withName(String name) {  // setName (сеттер)
    this.name = name;
    return this;
  }

  public String getHeader() {
    return header;
  }

  public GroupData withHeader(String header) {  // setHeader (сеттер)
    this.header = header;
    return this;
  }

  public String getFooter() {
    return footer;
  }

  public GroupData withtFooter(String footer) {  // setFooter (сеттер)
    this.footer = footer;
    return this;
  }

  @Override
  public String toString() {
    return "GroupData{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    GroupData groupData = (GroupData) o;

    if (id != groupData.id) return false;
    return name != null ? name.equals(groupData.name) : groupData.name == null;
  }

  // Это хеширование. Прежде чем сравнивать объекты при помощи метода equals(), сначала выполняем более быструю проверку и сравниваем хеш-коды объектов
  // У равных объектов должны быть равные хеш-коды. Если они не совпали, то проверки equals() не будет.
  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    return result;
  }

}
