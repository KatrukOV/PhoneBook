package com.katruk.domain.dto;

import java.util.concurrent.atomic.AtomicReference;

public class ContactDto {

  private Long contactId = 0L;
  private String lastName = "";
  private String name = "";
  private String patronymic = "";
  private String mobilePhone = "";
  private String homePhone = "";
  private String email = "";
  private String city = "";
  private String street = "";
  private String building = "";
  private Integer apartment = 0;

  public ContactDto() {
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

  private ContactDto(Builder builder) {
    this.contactId = builder.contactId;
    this.lastName = builder.lastName;
    this.name = builder.name;
    this.patronymic = builder.patronymic;
    this.mobilePhone = builder.mobilePhone;
    this.homePhone = builder.homePhone;
    this.email = builder.email;
    this.city = builder.city;
    this.street = builder.street;
    this.building = builder.building;
    this.apartment = builder.apartment;
  }

  public static class Builder {

    private Long contactId;
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

    public Builder() {
    }

    public Builder contactId(Long contactId) {
      this.contactId = contactId;
      return this;
    }

    public Builder lastName(String lastName) {
      this.lastName = lastName;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder patronymic(String patronymic) {
      this.patronymic = patronymic;
      return this;
    }

    public Builder mobilePhone(String mobilePhone) {
      this.mobilePhone = mobilePhone;
      return this;
    }

    public Builder homePhone(String homePhone) {
      this.homePhone = homePhone;
      return this;
    }

    public Builder email(String email) {
      this.email = email;
      return this;
    }

    public Builder city(String city) {
      this.city = city;
      return this;
    }

    public Builder street(String street) {
      this.street = street;
      return this;
    }

    public Builder building(String building) {
      this.building = building;
      return this;
    }

    public Builder apartment(Integer apartment) {
      this.apartment = apartment;
      return this;
    }

    public ContactDto build() {
      return new ContactDto(this);
    }
  }

  @Override
  public String toString() {
    final AtomicReference<StringBuilder> sb =
        new AtomicReference<>(new StringBuilder("ContactJson{"));
    sb.get().append("contactId=").append(contactId);
    sb.get().append(", lastName='").append(lastName).append('\'');
    sb.get().append(", name='").append(name).append('\'');
    sb.get().append(", patronymic='").append(patronymic).append('\'');
    sb.get().append(", mobilePhone='").append(mobilePhone).append('\'');
    sb.get().append(", homePhone='").append(homePhone).append('\'');
    sb.get().append(", email='").append(email).append('\'');
    sb.get().append(", city='").append(city).append('\'');
    sb.get().append(", street='").append(street).append('\'');
    sb.get().append(", building='").append(building).append('\'');
    sb.get().append(", apartment=").append(apartment);
    sb.get().append('}');
    return sb.get().toString();
  }
}
