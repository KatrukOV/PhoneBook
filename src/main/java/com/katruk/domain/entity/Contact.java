package com.katruk.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Email;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

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

  public Contact(int id) {
    super(id);
  }

  public Contact(Person person, String MobilePhone) {
    this.person = person;
    this.mobilePhone = MobilePhone;
  }
//todo bilder
  public Contact(Person person, String MobilePhone, String HomePhone,
                 Address address, String email) {
    this.person = person;
    this.mobilePhone = MobilePhone;
    this.homePhone = HomePhone;
    this.address = address;
    this.email = email;
  }

  @ManyToOne()
  @JoinColumn(name = "user_person_id")
  @JsonIgnore
  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  //todo
  @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
  @JoinTable(name = "contact_person",
      joinColumns = @JoinColumn(name = "contact_id"),
      inverseJoinColumns = @JoinColumn(name = "person_id"))
  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }

  @Basic
  @Column(name = "mobile_phone", nullable = false)
  @Pattern(regexp = "^\\+380\\d{9,}$")
  public String getMobilePhone() {
    return mobilePhone;
  }

  public void setMobilePhone(String mobilePhone) {
    this.mobilePhone = mobilePhone;
  }

  @Basic
  @Column(name = "home_phone")
  @Pattern(regexp = "^\\+380\\d{9,}$")
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

  //todo
  @JoinColumn(name = "address_id")
  @JsonIgnore
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

    Contact contact = (Contact) o;

    if (!user.equals(contact.user)) {
      return false;
    }
    if (!person.equals(contact.person)) {
      return false;
    }
    if (!mobilePhone.equals(contact.mobilePhone)) {
      return false;
    }
    if (homePhone != null ? !homePhone.equals(contact.homePhone) : contact.homePhone != null) {
      return false;
    }
    if (address != null ? !address.equals(contact.address) : contact.address != null) {
      return false;
    }
    return email != null ? email.equals(contact.email) : contact.email == null;
  }

  @Override
  public int hashCode() {
    int result = user.hashCode();
    result = 31 * result + person.hashCode();
    result = 31 * result + mobilePhone.hashCode();
    result = 31 * result + (homePhone != null ? homePhone.hashCode() : 0);
    result = 31 * result + (address != null ? address.hashCode() : 0);
    result = 31 * result + (email != null ? email.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Contact{");
    sb.append("user=").append(user);
    sb.append(", person=").append(person);
    sb.append(", mobilePhone='").append(mobilePhone).append('\'');
    sb.append(", homePhone='").append(homePhone).append('\'');
    sb.append(", address=").append(address);
    sb.append(", email='").append(email).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
