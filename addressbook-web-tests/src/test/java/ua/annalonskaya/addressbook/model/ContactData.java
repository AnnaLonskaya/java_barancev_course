package ua.annalonskaya.addressbook.model;

public class ContactData {
  private int id;
  private final String fname;
  private final String lname;
  private final String company;
  private final String address;
  private final String email;
  private final String phone;
  private int day;
  private int month;
  private final String year;
  private String group;


  public ContactData(int id, String lname, String fname, String company, String address, String email, String phone, int day, int month, String year, String group) {
    this.id = id;
    this.fname = fname;
    this.lname = lname;
    this.company = company;
    this.address = address;
    this.email = email;
    this.phone = phone;
    this.day = day;
    this.month = month;
    this.year = year;
    this.group = group;
  }

  public ContactData(String lname, String fname, String company, String address, String email, String phone, int day, int month, String year, String group) {
    this.id = 0;
    this.fname = fname;
    this.lname = lname;
    this.company = company;
    this.address = address;
    this.email = email;
    this.phone = phone;
    this.day = day;
    this.month = month;
    this.year = year;
    this.group = group;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getFname() {
    return fname;
  }

  public String getLname() {
    return lname;
  }

  public String getCompany() {
    return company;
  }

  public String getAddress() {
    return address;
  }

  public String getEmail() {
    return email;
  }

  public String getPhone() {
    return phone;
  }

  public int getDay() {
    return day;
  }

  public int getMonth() {
    return month;
  }

  public String getYear() {
    return year;
  }

  public String getGroup() {
    return group;
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
