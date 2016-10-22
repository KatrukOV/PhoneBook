package com.katruk.domain.entity;

public class Person extends Model {

  protected String lastName;
  protected String name;
  protected String patronymic;

  public Person() {
    super();
  }

  public Person(int id) {
    this.id = id;
  }

  public Person(String lastName, String name, String patronymic) {
    this.lastName = lastName;
    this.name = name;
    this.patronymic = patronymic;

  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPatronymic() {
    return patronymic;
  }

  public void setPatronymic(String patronymic) {
    this.patronymic = patronymic;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Person{");
    sb.append("lastName='").append(lastName).append('\'');
    sb.append(", name='").append(name).append('\'');
    sb.append(", patronymic='").append(patronymic).append('\'');
    sb.append('}');
    return sb.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Person person = (Person) o;

    if (!name.equals(person.name)) {
      return false;
    }
    if (!lastName.equals(person.lastName)) {
      return false;
    }
    return patronymic.equals(person.patronymic);

  }

  @Override
  public int hashCode() {
    int result = name.hashCode();
    result = 31 * result + lastName.hashCode();
    result = 31 * result + patronymic.hashCode();
    return result;
  }
}
