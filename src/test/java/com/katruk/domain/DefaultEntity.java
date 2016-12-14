package com.katruk.domain;

import com.katruk.domain.entity.dto.ContactDto;
import com.katruk.domain.entity.dto.UserDto;
import com.katruk.domain.entity.Address;
import com.katruk.domain.entity.Contact;
import com.katruk.domain.entity.Person;
import com.katruk.domain.entity.User;

import java.util.HashSet;
import java.util.Set;

public class DefaultEntity {

  private Address address;
  private Person person;
  private User user;
  private UserDto userDto;
  private Contact contact;
  private ContactDto contactDto;

  public Address address() {
    address = new Address();
    address.setId(1L);
    address.setCity("City");
    address.setStreet("Street");
    address.setBuilding("12/4");
    address.setApartment(15);
    return address;
  }

  public Person person() {
    person = new Person();
    person.setId(1L);
    person.setLastName("LastName");
    person.setName("Name");
    person.setPatronymic("Patronymic");
    return person;
  }

  public User user() {
    user = new User();
    user.setId(1L);
    user.setLastName("LastName");
    user.setName("Name");
    user.setPatronymic("Patronymic");
    user.setLogin("Login");
    user.setPassword("Password");
    Set<Contact> contactSet = new HashSet<>();
    contactSet.add(contact());
    user.setContacts(contactSet);
    return user;
  }

  public UserDto userDto() {
    userDto = new UserDto();
    userDto.setLastName("LastName");
    userDto.setName("Name");
    userDto.setPatronymic("Patronymic");
    userDto.setLogin("Login");
    userDto.setPassword("Password");
    userDto.setConfirmPassword("Password");
    return userDto;
  }

  public Contact contact() {
    contact = new Contact();
    contact.setId(1L);
    contact.setPerson(person());
    contact.setMobilePhone("+380(67)4445566");
    contact.setHomePhone("+380(44)4445566");
    contact.setEmail("email@same.com");
    contact.setAddress(address());
    contact.setUser(new User());
    return contact;
  }

  public ContactDto contactDto() {
    contactDto = new ContactDto.Builder()
        .contactId(1L)
        .lastName("LastName")
        .name("Name")
        .patronymic("Patronymic")
        .mobilePhone("+380(67)4445566")
        .homePhone("+380(44)4445566")
        .email("email@same.com")
        .city("City")
        .street("Street")
        .building("12/4")
        .apartment(15)
        .build();
    return contactDto;
  }
}
