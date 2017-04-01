package ua.annalonskaya.addressbook.model;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

@XStreamAlias("contact")
@Entity
@Table(name = "addressbook")
public class ContactData {
  @XStreamOmitField
  @Id
  @Column(name = "id")
  private int id = Integer.MAX_VALUE;

  @Expose
  @Column(name = "firstname")
  private String fname;

  @Expose
  @Column(name = "lastname")
  private String lname;

  @Expose
  private String company;

  @Expose
  @Type(type = "text")
  private String address;

  @Expose
  @Type(type = "text")
  private String email;

  @Expose
  @Type(type = "text")
  private String email2;

  @Expose
  @Type(type = "text")
  private String email3;

  @Transient
  private String allEmails;

  @Expose
  @Column(name = "home")
  @Type(type = "text")
  private String homePhone;

  @Expose
  @Column(name = "mobile")
  @Type(type = "text")
  private String mobilePhone;

  @Expose
  @Column(name = "work")
  @Type(type = "text")
  private String workPhone;

  @Transient
  private String allPhones;

  @Expose
  @Column(name = "bday")
  @Type(type = "byte")
  private byte day;

  @Expose
  @Column(name = "bmonth")
  @Type(type = "string")
  private String month;

  @Expose
  @Column(name = "byear")
  @Type(type = "string")
  private String year;

  @Transient  // поле будет пропущено и не будет извлекаться из БД  (hibernate)
  private String allContactDetails;

  @Expose
  @Column(name = "photo")
  @Type(type = "text")
  private String photo;  // тип атрибута File переименовываем на String, т.к. hibernate не сможет прочитать его из БД, а преобразование в File делаем внутри геттера и сеттера

  @Transient
  private int age;

  @ManyToMany (fetch = FetchType.EAGER) // аннотация прим-ся к атрибутам, к-ые представляют собой коллекцию объектов какого-то другого типа (в клаасе ContactData будет множество групп, а в классе GroupData будет множество контактов)
  @JoinTable(name = "address_in_groups", // в качестве связующей таблицы исп-ся таблица  "address_in_groups". fetch = FetchType.EAGER - из БД извлекается как можно больше информации за один заход
          joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
  private Set<GroupData> groups = new HashSet<GroupData>();  // согласно документации нужно сразу инициализировать это свойство, т.е. создать пустое множество

  public int getId() {
    return id;
  }

  public ContactData withId(int id) {
    this.id = id;
    return this;
  }

  public String getFname() {
    return fname;
  }

  public ContactData withFname(String fname) {
    this.fname = fname;
    return this;
  }

  public String getLname() {
    return lname;
  }

  public ContactData withLname(String lname) {
    this.lname = lname;
    return this;
  }

  public String getCompany() {
    return company;
  }

  public ContactData withCompany(String company) {
    this.company = company;
    return this;
  }

  public String getAddress() {
    return address;
  }

  public ContactData withAddress(String address) {
    this.address = address;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public ContactData withEmail(String email) {
    this.email = email;
    return this;
  }

  public String getEmail2() {
    return email2;
  }

  public ContactData withEmail2(String email2) {
    this.email2 = email2;
    return this;
  }

  public String getEmail3() {
    return email3;
  }

  public ContactData withEmail3(String email3) {
    this.email3 = email3;
    return this;
  }


  public String getAllEmails() {
    return allEmails;
  }

  public ContactData withAllEmails(String allEmails) {
    this.allEmails = allEmails;
    return this;
  }

  public String getHomePhone() {
    return homePhone;
  }

  public ContactData withHomePhone(String homePhone) {
    this.homePhone = homePhone;
    return this;
  }

  public String getMobilePhone() {
    return mobilePhone;
  }

  public ContactData withMobilePhone(String mobilePhone) {
    this.mobilePhone = mobilePhone;
    return this;
  }

  public String getWorkPhone() {
    return workPhone;
  }

  public ContactData withWorkPhone(String workPhone) {
    this.workPhone = workPhone;
    return this;
  }

  public String getAllPhones() {
    return allPhones;

  }

  public ContactData withAllPhones(String allPhones) {
    this.allPhones = allPhones;
    return this;
  }

  public int getDay() {
    return new Integer(day);
  }

  public ContactData withDay(int day) {
    this.day = (byte) day;
    return this;
  }

  public String getMonth() {
    return month;
  }

  public ContactData withMonth(String month) {
    this.month = month;
    return this;
  }

  public String getYear() {
    return year;
  }

  public ContactData withYear(String year) {
    this.year = year;
    return this;
  }

  public int getAge() {
    return age;
  }

  public ContactData withAge(int age) {
    this.age = age;
    return this;
  }

  public Groups getGroups() {   // делаем так, чтобы геттер возвращал объект типа Groups, для этого внутри делаем преобразование (множество превратить в
    return new Groups(groups);  // объект типа Groups). При этом создается копия
  }

  public String getAllContactDetails() {
    return allContactDetails;
  }

  public ContactData withAllContactDetails(String allContactDetails) {
    this.allContactDetails = allContactDetails;
    return this;
  }

  public File getPhoto() {
    if (photo != null) {
      return new File(photo);
    } else {
      return null;
    }
  }

  public ContactData withPhoto(File photo) {
    this.photo = photo.getPath();
    return this;
  }

  public ContactData inGroup(GroupData group) {
    groups.add(group);
    return this;
  }

  @Override
  public String toString() {
    return "ContactData{" +
            "id=" + id +
            ", fname='" + fname + '\'' +
            ", lname='" + lname + '\'' +
            ", address='" + address + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ContactData that = (ContactData) o;

    if (id != that.id) return false;
    if (fname != null ? !fname.equals(that.fname) : that.fname != null) return false;
    if (lname != null ? !lname.equals(that.lname) : that.lname != null) return false;
    return address != null ? address.equals(that.address) : that.address == null;
  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (fname != null ? fname.hashCode() : 0);
    result = 31 * result + (lname != null ? lname.hashCode() : 0);
    result = 31 * result + (address != null ? address.hashCode() : 0);
    return result;
  }

}
