package com.katruk.domain.entity;

import org.hibernate.validator.constraints.Email;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "Contact")
public class Contact extends Model {

  private User user;
  private Person person;
  private String mobilePhone;
  private String homePhone;
  private Address address;
  private String email;

  public Contact() {
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_person_id")
  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "person_id", nullable = false)
  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }

  @Basic
  @Column(name = "mobile_phone", nullable = false, length = 17)
  public String getMobilePhone() {
    return mobilePhone;
  }

  public void setMobilePhone(String mobilePhone) {
    this.mobilePhone = mobilePhone;
  }

  @Basic
  @Column(name = "home_phone", length = 17)
  public String getHomePhone() {
    return homePhone;
  }

  @Basic
  @Email
  @Column(name = "email", length = 30)
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setHomePhone(String homePhone) {
    this.homePhone = homePhone;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "address_id")
  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }
}
