package ua.annalonskaya.addressbook.model;

public class ContactData {
  private int id = Integer.MAX_VALUE;
  private String fname;
  private String lname;
  private String company;
  private String address;
  private String email;
  private String phone;
  private int day;
  private int month;
  private String year;
  private String group;

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

  public String getPhone() {
    return phone;
  }

  public ContactData withPhone(String phone) {
    this.phone = phone;
    return this;
  }

  public int getDay() {
    return day;
  }

  public ContactData withDay(int day) {
    this.day = day;
    return this;
  }

  public int getMonth() {
    return month;
  }

  public ContactData withMonth(int month) {
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

  public String getGroup() {
    return group;
  }

  public ContactData withGroup(String group) {
    this.group = group;
    return this;
  }

  @Override
  public String toString() {

    return "ContactData{" +
            "id='" + id + '\'' +
            ", fname='" + fname + '\'' +
            ", lname='" + lname + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ContactData that = (ContactData) o;

    if (id != that.id) return false;
    if (fname != null ? !fname.equals(that.fname) : that.fname != null) return false;
    return lname != null ? lname.equals(that.lname) : that.lname == null;
  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (fname != null ? fname.hashCode() : 0);
    result = 31 * result + (lname != null ? lname.hashCode() : 0);
    return result;
  }

}
