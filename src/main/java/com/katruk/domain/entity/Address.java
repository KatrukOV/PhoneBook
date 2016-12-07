package com.katruk.domain.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Address")
//@JsonTypeName("Address")
public class Address extends Model {

  private String city;
  private String street;
  private String building;
  private Integer apartment;

  public Address() {
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
  public Integer getApartment() {
    return apartment;
  }

  public void setApartment(Integer apartment) {
    this.apartment = apartment;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    Address address = (Address) o;
    return (city != null ? city.equals(address.city) : address.city == null)
           && (street != null ? street.equals(address.street) : address.street == null)
           && (building != null ? building.equals(address.building) : address.building == null)
           && (apartment != null ? apartment.equals(address.apartment) : address.apartment == null);
  }

  @Override
  public int hashCode() {
    int result = city != null ? city.hashCode() : 0;
    result = 31 * result + (street != null ? street.hashCode() : 0);
    result = 31 * result + (building != null ? building.hashCode() : 0);
    result = 31 * result + (apartment != null ? apartment.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Address{" +
           "city='" + city + '\'' +
           ", street='" + street + '\'' +
           ", building='" + building + '\'' +
           ", apartment=" + apartment +
           "} " + super.toString();
  }
}
