package com.katruk.domain.entity;

public class Contact extends Model {

  private Person person;
  private String telMobile;
  private String telHome;
  private Address address;
  private String mail;

  public Contact() {

  }

  public Contact(int id) {
    super(id);
  }

  public Contact(Person person, String telMobile) {
    this.person = person;
    this.telMobile = telMobile;
  }

  public Contact(Person person, String telMobile, String telHome,
                 Address address, String mail) {
    this.person = person;
    this.telMobile = telMobile;
    this.telHome = telHome;
    this.address = address;
    this.mail = mail;
  }

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }

  public String getTelMobile() {
    return telMobile;
  }

  public void setTelMobile(String telMobile) {
    this.telMobile = telMobile;
  }

  public String getTelHome() {
    return telHome;
  }

  public void setTelHome(String telHome) {
    this.telHome = telHome;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public String getMail() {
    return mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Contact contact = (Contact) o;

    if (!person.equals(contact.person)) {
      return false;
    }
    if (!telMobile.equals(contact.telMobile)) {
      return false;
    }
    if (telHome != null ? !telHome.equals(contact.telHome) : contact.telHome != null) {
      return false;
    }
    if (address != null ? !address.equals(contact.address) : contact.address != null) {
      return false;
    }
    return mail != null ? mail.equals(contact.mail) : contact.mail == null;

  }

  @Override
  public int hashCode() {
    int result = person.hashCode();
    result = 31 * result + telMobile.hashCode();
    result = 31 * result + (telHome != null ? telHome.hashCode() : 0);
    result = 31 * result + (address != null ? address.hashCode() : 0);
    result = 31 * result + (mail != null ? mail.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Contact{");
    sb.append("person=").append(person);
    sb.append(", telMobile='").append(telMobile).append('\'');
    sb.append(", telHome='").append(telHome).append('\'');
    sb.append(", address=").append(address);
    sb.append(", mail='").append(mail).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
