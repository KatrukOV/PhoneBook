package com.katruk.domain.entity;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "User")
public class User extends Person {

  private String login;
  private String password;  //sha1Hex encrypted
  private Set<Contact> contacts = new HashSet<>();

  public User() {
  }

  public User(int id) {
    super(id);
  }

  //todo bilder
  public User(String lastName, String name, String patronymic, String login,
              String password) {
    this.lastName = lastName;
    this.name = name;
    this.patronymic = patronymic;
    this.login = login;
    this.password = encodePassword(password);
  }

//  @Id
//  @GeneratedValue(strategy = GenerationType.AUTO)
//  @Column(name = "user_person_id", nullable = false)
//  @Override

  @OneToOne(mappedBy = "Person")
  @PrimaryKeyJoinColumn
  @Column(name = "user_person_id", nullable = false)
  @Override
  public int getId() {
    return super.getId();
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
  @Column(name = "password", nullable = false, length = 30)
  @Size(min = 5)
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

  @OneToMany(mappedBy = "Contact", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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
