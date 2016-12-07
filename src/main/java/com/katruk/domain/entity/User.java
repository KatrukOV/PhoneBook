package com.katruk.domain.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "User")
@PrimaryKeyJoinColumn(name = "person_Id", referencedColumnName = "Id")
//@JsonTypeName("User")
public class User extends Person {

  private String login;
  private String password;
  private Set<Contact> contacts = new HashSet<>();

  public User() {
  }

  @Basic
  @Column(name = "login", nullable = false, unique = true, length = 30)
  @Size(min = 3)
  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  @Basic
  @Column(name = "password", nullable = false)
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER , cascade = CascadeType.REMOVE)
  public Set<Contact> getContacts() {
    return contacts;
  }

  public void setContacts(Set<Contact> contacts) {
    this.contacts = contacts;
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
    User user = (User) o;
    return (login != null ? login.equals(user.login) : user.login == null)
           && (password != null ? password.equals(user.password) : user.password == null)
           && (contacts != null ? contacts.equals(user.contacts) : user.contacts == null);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (login != null ? login.hashCode() : 0);
    result = 31 * result + (password != null ? password.hashCode() : 0);
    result = 31 * result + (contacts != null ? contacts.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "User{" +
           "login='" + login + '\'' +
           ", password='" + password + '\'' +
           "} " + super.toString();
  }
}
