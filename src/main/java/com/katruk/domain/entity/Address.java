package com.katruk.domain.entity;

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

  public int getApartment() {
    return apartment;
  }

  public void setApartment(int apartment) {
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

    Address address = (Address) o;

    if (apartment != address.apartment) {
      return false;
    }
    if (!city.equals(address.city)) {
      return false;
    }
    if (!street.equals(address.street)) {
      return false;
    }
    return building.equals(address.building);

  }

  @Override
  public int hashCode() {
    int result = city.hashCode();
    result = 31 * result + street.hashCode();
    result = 31 * result + building.hashCode();
    result = 31 * result + apartment;
    return result;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Address{");
    sb.append("city='").append(city).append('\'');
    sb.append(", street='").append(street).append('\'');
    sb.append(", building='").append(building).append('\'');
    sb.append(", apartment=").append(apartment);
    sb.append('}');
    return sb.toString();
  }
}
