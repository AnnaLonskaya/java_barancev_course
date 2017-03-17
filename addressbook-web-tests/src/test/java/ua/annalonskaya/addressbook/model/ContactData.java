package ua.annalonskaya.addressbook.model;

public class ContactData {
  private int id = Integer.MAX_VALUE;
  private String fname;
  private String lname;
  private String company;
  private String address;
  private String email;
  private String email2;
  private String email3;
  private String allEmails;
  private String homePhone;
  private String mobilePhone;
  private String workPhone;
  private String allPhones;
  private int day;
  private int month;
  private String year;
  private String group;
  private String allContactDetails;

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

  public String getAllContactDetails() {
    return allContactDetails;
  }

  public ContactData withAllContactDetails(String allContactDetails) {
    this.allContactDetails = allContactDetails;
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
