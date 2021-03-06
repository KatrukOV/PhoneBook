package com.katruk.domain.entity;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Contact")
public class Contact extends Model {

  private User user;
  private Person person = new Person();
  private String mobilePhone;
  private String homePhone;
  private Address address = new Address();
  private String email;

  public Contact() {
  }

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_person_id")
  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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

  public void setHomePhone(String homePhone) {
    this.homePhone = homePhone;
  }

  @Basic
  @Column(name = "email", length = 30)
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @ManyToOne(fetch = FetchType.EAGER ,cascade = CascadeType.ALL)
  @JoinColumn(name = "address_id")
  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
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
    Contact contact = (Contact) o;
    return (person != null ? person.equals(contact.person) : contact.person == null)
           && (mobilePhone != null ? mobilePhone.equals(contact.mobilePhone)
                                   : contact.mobilePhone == null)
           && (homePhone != null ? homePhone.equals(contact.homePhone) : contact.homePhone == null)
           && (address != null ? address.equals(contact.address) : contact.address == null)
           && (email != null ? email.equals(contact.email) : contact.email == null);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (person != null ? person.hashCode() : 0);
    result = 31 * result + (mobilePhone != null ? mobilePhone.hashCode() : 0);
    result = 31 * result + (homePhone != null ? homePhone.hashCode() : 0);
    result = 31 * result + (address != null ? address.hashCode() : 0);
    result = 31 * result + (email != null ? email.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Contact{" +
           ", person=" + person +
           ", mobilePhone='" + mobilePhone + '\'' +
           ", homePhone='" + homePhone + '\'' +
           ", address=" + address +
           ", email='" + email + '\'' +
           "} " + super.toString();
  }
}
