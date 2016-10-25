package com.katruk.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.data.annotation.Id;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Person")
public class Person extends Model {

  protected String lastName;
  protected String name;
  protected String patronymic;

  public Person() {
  }

  public Person(int id) {
    super(id);
  }

  public Person(String lastName, String name, String patronymic) {
    this.lastName = lastName;
    this.name = name;
    this.patronymic = patronymic;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "person_id", nullable = false)
  @Override
  public int getId() {
    return super.getId();
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
