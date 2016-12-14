package com.katruk.domain.util;

import static java.util.Objects.nonNull;

import com.katruk.domain.entity.Address;
import com.katruk.domain.entity.Contact;
import com.katruk.domain.entity.Person;
import com.katruk.domain.entity.User;
import com.katruk.domain.entity.dto.ContactDto;
import com.katruk.domain.entity.dto.UserDto;
import com.katruk.domain.entity.json.ContactJson;
import com.katruk.domain.entity.json.UserJson;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Converter {

  public User makeUserFromDto(final UserDto userDto) {
    User newUser = new User();
    newUser.setLastName(userDto.getLastName());
    newUser.setName(userDto.getName());
    newUser.setPatronymic(userDto.getPatronymic());
    newUser.setLogin(userDto.getLogin());
    newUser.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
    return newUser;
  }

  public ContactDto makeDtoFromContact(final Contact contact) {
    return new ContactDto.Builder()
        .contactId(contact.getId())
        .lastName(contact.getPerson().getLastName())
        .name(contact.getPerson().getName())
        .patronymic(contact.getPerson().getPatronymic())
        .mobilePhone(contact.getMobilePhone())
        .homePhone(contact.getHomePhone())
        .email(contact.getEmail())
        .city(contact.getAddress().getCity())
        .street(contact.getAddress().getStreet())
        .building(contact.getAddress().getBuilding())
        .apartment(contact.getAddress().getApartment())
        .build();
  }

  public Contact makeContactFromJson(ContactJson contactJson, User user) {

    System.out.println(">>> createContact 1 contactJson=" + contactJson);
    Contact contact = new Contact();
    contact.setUser(user);
    contact.setId(contactJson.getContactId());
    Person person = new Person();
    person.setLastName(contactJson.getLastName());
    person.setName(contactJson.getName());
    person.setPatronymic(contactJson.getPatronymic());
    contact.setPerson(person);
    Address address = new Address();
    address.setCity(contactJson.getCity());
    address.setStreet(contactJson.getStreet());
    address.setBuilding(contactJson.getBuilding());
    address.setApartment(contactJson.getApartment());
    contact.setAddress(address);
    contact.setMobilePhone(contactJson.getMobilePhone());
    contact.setHomePhone(contactJson.getHomePhone());
    contact.setEmail(contactJson.getEmail());
    System.out.println(">>> createContact 2 contact=" + contact);
    return contact;
  }

  public ContactJson makeJsonFromContact(Contact contact) {
    System.out.println(">>> createContactJson 1 contact=" + contact);
    System.out.println(">>> createContactJson 1 contact.user=" + contact.getUser());
    ContactJson contactJson = new ContactJson();
    contactJson.setContactId(contact.getId());
    contactJson.setUserId(contact.getUser().getId());
    contactJson.setLastName(contact.getPerson().getLastName());
    contactJson.setName(contact.getPerson().getName());
    contactJson.setPatronymic(contact.getPerson().getPatronymic());
    contactJson.setMobilePhone(contact.getMobilePhone());
    contactJson.setHomePhone(contact.getHomePhone());
    contactJson.setEmail(contact.getEmail());
    if (nonNull(contact.getAddress())) {
      contactJson.setCity(contact.getAddress().getCity());
      contactJson.setStreet(contact.getAddress().getStreet());
      contactJson.setBuilding(contact.getAddress().getBuilding());
      contactJson.setApartment(contact.getAddress().getApartment());
    }
    System.out.println(">>> createContactJson 2 contactJson=" + contactJson);
    return contactJson;
  }

  public User makeUserFromJson(UserJson userJson) {
    User user = new User();
    user.setId(userJson.getUserId());
    user.setLastName(userJson.getLastName());
    user.setName(userJson.getName());
    user.setPatronymic(userJson.getPatronymic());
    user.setLogin(userJson.getLogin());
    user.setPassword(userJson.getPassword());
    return user;
  }
  public UserJson makeJsonFromUser(User user) {
    UserJson userJson = new UserJson();
    userJson.setUserId(user.getId());
    userJson.setLastName(user.getLastName());
    userJson.setName(user.getName());
    userJson.setPatronymic(user.getPatronymic());
    userJson.setLogin(user.getLogin());
    userJson.setPassword(user.getPassword());
    return userJson;
  }

}
