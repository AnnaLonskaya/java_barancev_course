package ua.annalonskaya.addressbook.model;

public class GroupData {
  private int id = Integer.MAX_VALUE;
  private String name;  // убираем ключевое слово final, делаем их модифицируемыми, чтобы их можно было менять, после того, как конструктор отработал
  private String header;
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

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    return result;
  }

}
