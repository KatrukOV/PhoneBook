package com.katruk.domain.entity.json;

public class ContactJson {

  private Long contactId;
  private Long userId;
  private String lastName;
  private String name;
  private String patronymic;
  private String mobilePhone;
  private String homePhone;
  private String email;
  private String city;
  private String street;
  private String building;
  private Integer apartment;

  public ContactJson() {
  }

  @Override
  public String toString() {
    return "ContactJson{" +
           "contactId=" + contactId +
           ", userId=" + userId +
           ", lastName='" + lastName + '\'' +
           ", name='" + name + '\'' +
           ", patronymic='" + patronymic + '\'' +
           ", mobilePhone='" + mobilePhone + '\'' +
           ", homePhone='" + homePhone + '\'' +
           ", email='" + email + '\'' +
           ", city='" + city + '\'' +
           ", street='" + street + '\'' +
           ", building='" + building + '\'' +
           ", apartment=" + apartment +
           '}';
  }

  public Long getContactId() {
    return contactId;
  }

  public void setContactId(Long contactId) {
    this.contactId = contactId;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPatronymic() {
    return patronymic;
  }

  public void setPatronymic(String patronymic) {
    this.patronymic = patronymic;
  }

  public String getMobilePhone() {
    return mobilePhone;
  }

  public void setMobilePhone(String mobilePhone) {
    this.mobilePhone = mobilePhone;
  }

  public String getHomePhone() {
    return homePhone;
  }

  public void setHomePhone(String homePhone) {
    this.homePhone = homePhone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getBuilding() {
    return building;
  }

  public void setBuilding(String building) {
    this.building = building;
  }

  public Integer getApartment() {
    return apartment;
  }

  public void setApartment(Integer apartment) {
    this.apartment = apartment;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }
}
