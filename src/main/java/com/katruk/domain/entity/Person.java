package com.katruk.domain.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Person")
public class Person extends Model {

  protected String lastName;
  protected String name;
  protected String patronymic;

  public Person() {
  }

  @Basic
  @Column(name = "last_name", nullable = false, length = 30)
  @Size(min = 4)
  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @Basic
  @Column(name = "name", nullable = false, length = 30)
  @Size(min = 4)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Basic
  @Column(name = "patronymic", nullable = false, length = 30)
  @Size(min = 4)
  public String getPatronymic() {
    return patronymic;
  }

  public void setPatronymic(String patronymic) {
    this.patronymic = patronymic;
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

    Person person = (Person) o;

    return (lastName != null ? lastName.equals(person.lastName) : person.lastName == null)
           && (name != null ? name.equals(person.name) : person.name == null)
           && (patronymic != null ? patronymic.equals(person.patronymic)
                                  : person.patronymic == null);
  }

  @Override
  public int hashCode() {
    int result = lastName != null ? lastName.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (patronymic != null ? patronymic.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Person{" +
           "lastName='" + lastName + '\'' +
           ", name='" + name + '\'' +
           ", patronymic='" + patronymic + '\'' +
           "} " + super.toString();
  }
}
