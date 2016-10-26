package com.katruk.domain.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Address")
public class Address extends Model {

  private String city;
  private String street;
  private String building;
  private int apartment;

  public Address() {
  }

  public Address(int id) {
    super(id);
  }

  public Address(String city, String street, String building, int apartment) {
    this.city = city;
    this.street = street;
    this.building = building;
    this.apartment = apartment;
  }

  @Basic
  @Column(name = "city", length = 30)
  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  @Basic
  @Column(name = "street", length = 30)
  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  @Basic
  @Column(name = "building", length = 10)
  public String getBuilding() {
    return building;
  }

  public void setBuilding(String building) {
    this.building = building;
  }

  @Basic
  @Column(name = "apartment")
  public int getApartment() {
    return apartment;
  }

  public void setApartment(int apartment) {
    this.apartment = apartment;
  }

}
