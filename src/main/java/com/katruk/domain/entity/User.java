package com.katruk.domain.entity;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User extends Person {

  private String login;
  private String password;  //sha1Hex encrypted
  private List<Contact> contacts = new ArrayList<>();

  public User() {
  }

  public User(int id) {
    super(id);
  }

  public User(String lastName, String name, String patronymic, String login,
              String password) {
    this.lastName = lastName;
    this.name = name;
    this.patronymic = patronymic;
    this.login = login;
    this.password = encodePassword(password);
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = encodePassword(password);
  }

  //Sha1Hex encryption method
  private String encodePassword(String password) {
    return DigestUtils.sha1Hex(password);
  }

  public List<Contact> getContacts() {
    return contacts;
  }

  public void setContacts(List<Contact> contacts) {
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

    if (!login.equals(user.login)) {
      return false;
    }
    if (!password.equals(user.password)) {
      return false;
    }
    return contacts.equals(user.contacts);

  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + login.hashCode();
    result = 31 * result + password.hashCode();
    result = 31 * result + contacts.hashCode();
    return result;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("User{");
    sb.append("lastName='").append(lastName).append('\'');
    sb.append(", name='").append(name).append('\'');
    sb.append(", patronymic='").append(patronymic).append('\'');
    sb.append(", login='").append(login).append('\'');
    //without password
    sb.append(", contacts=").append(contacts);
    sb.append('}');
    return sb.toString();
  }

}
