package ua.annalonskaya.addressbook;

public class ContactData {
  private final String fname;
  private final String lname;
  private final String company;
  private final String address;
  private final String email;
  private final String phone;
  private int day;
  private int month;
  private final String year;

  public ContactData(String fname, String lname, String company, String address, String email, String phone, int day, int month, String year) {
    this.fname = fname;
    this.lname = lname;
    this.company = company;
    this.address = address;
    this.email = email;
    this.phone = phone;
    this.day = day;
    this.month = month;
    this.year = year;
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

  public int getDay() { return day;}

  public int getMonth() { return month;}

  public String getYear() {return year;}
}
