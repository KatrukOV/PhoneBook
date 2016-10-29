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
@PrimaryKeyJoinColumn(name="person_Id", referencedColumnName="Id")
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
  @Column(name = "password", nullable = false, length = 100)
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  public Set<Contact> getContacts() {
    return contacts;
  }

  public void setContacts(Set<Contact> contacts) {
    this.contacts = contacts;
  }
}
