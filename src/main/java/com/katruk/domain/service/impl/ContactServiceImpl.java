package com.katruk.domain.service.impl;

import static java.util.Objects.isNull;

import com.katruk.dao.ContactDao;
import com.katruk.domain.dto.ContactDto;
import com.katruk.domain.entity.Address;
import com.katruk.domain.entity.Contact;
import com.katruk.domain.entity.Person;
import com.katruk.domain.entity.User;
import com.katruk.domain.service.AddressService;
import com.katruk.domain.service.ContactService;
import com.katruk.domain.service.PersonService;
import com.katruk.domain.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ContactServiceImpl implements ContactService {

  @Autowired(required = false)
  @Qualifier("ContactDaoMySql")
  private ContactDao contactDao;

  @Autowired
  private PersonService personService;

  @Autowired
  private AddressService addressService;

  @Autowired
  private UserService userService;

  @Override
  public Set<ContactDto> getByUserLogin(String login) {

    System.out.println(">>> login"+login);

    User user = userService.getByLogin(login);

    System.out.println(">>> user"+user);

    Set<ContactDto> contactDtoSet = new HashSet<>();
    ContactDto contactDto;
    for (Contact contact : user.getContacts()) {
      contactDto = new ContactDto.Builder()
          .id(contact.getId())
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
      contactDtoSet.add(contactDto);
    }
    return contactDtoSet;
  }

  @Override
  public ContactDto getById(Integer contactId) {
    Contact contact = contactDao.findOne(contactId);
    return new ContactDto.Builder()
        .id(contact.getId())
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

  @Override
  public ContactStatus addContact(ContactDto contactDto) {
    System.out.println("begin contactDto= " + contactDto);
    ContactStatus incorrectStatus = isContactValid(contactDto);
    if (incorrectStatus != null) {
      return incorrectStatus;
    }
    Contact contact = createContact(contactDto);
    System.out.println("Before saving contact= " + contact);
    contactDao.save(contact);
    return ContactStatus.SUCCESS;
  }

  private Contact createContact(ContactDto contactDto) {
    Contact contact = new Contact();
    Person person = new Person();
    person.setLastName(contactDto.getLastName());
    person.setName(contactDto.getName());
    person.setPatronymic(contactDto.getPatronymic());

    System.out.println("Before saving person =" + person);
    person = personService.save(person);

    Address address = new Address();
    address.setCity(contactDto.getCity());
    address.setStreet(contactDto.getStreet());
    address.setBuilding(contactDto.getBuilding());
    address.setApartment(contactDto.getApartment());

    System.out.println("Before saving address =" + address);
    address = addressService.save(address);

    User user = userService.getByLogin(contactDto.getLogin());
    contact.setPerson(person);
    contact.setAddress(address);
    contact.setUser(user);
    contact.setMobilePhone(contactDto.getMobilePhone());
    contact.setHomePhone(contactDto.getHomePhone());
    contact.setEmail(contactDto.getEmail());
    return contact;
  }

  private ContactStatus isContactValid(ContactDto contactDto) {
    if (contactDto.getLastName().length() < 4) {
      return ContactStatus.INCORRECT_LAST_NAME;
    }
    if (contactDto.getName().length() < 4) {
      return ContactStatus.INCORRECT_NAME;
    }
    if (contactDto.getPatronymic().length() < 4) {
      return ContactStatus.INCORRECT_PATRONYMIC;
    }
    if (!PHONE_PATTERN.matcher(contactDto.getMobilePhone()).matches()) {
      return ContactStatus.INCORRECT_MOBILE_PHONE;
    }
    if (!contactDto.getHomePhone().isEmpty() &&
        !PHONE_PATTERN.matcher(contactDto.getHomePhone()).matches()) {
      return ContactStatus.INCORRECT_HOME_PHONE;
    }
    if (!contactDto.getEmail().isEmpty() &&
        !EMAIL_PATTERN.matcher(contactDto.getEmail()).matches()) {
      return ContactStatus.INCORRECT_EMAIL;
    }
    return null;
  }

  @Override
  public ContactStatus editContact(ContactDto contactDto) {
    ContactStatus incorrectStatus = isContactValid(contactDto);
    if (incorrectStatus != null) {
      return incorrectStatus;
    }
    Contact contact = contactDao.findOne(contactDto.getId());
    System.out.println("contact to update " + contact);
    System.out.println("contact to update for id " + contact.getId());
    contact = updateContact(contact, contactDto);
    contactDao.save(contact);
    return ContactStatus.SUCCESS;
  }

  private Contact updateContact(Contact contact, ContactDto contactDto) {
    Person person = personService.getById(contact.getPerson().getId());
    person = updatePerson(person, contactDto);
    Address address = addressService.getById(contact.getAddress().getId());
    address = updateAddress(address, contactDto);
    contact.setPerson(person);
    contact.setAddress(address);

    if (contactDto.getMobilePhone() != null &&
        !contactDto.getMobilePhone().equals(contact.getMobilePhone())) {
      contact.setMobilePhone(contactDto.getMobilePhone());
    }
    if (contactDto.getHomePhone() != null &&
        !contactDto.getHomePhone().equals(contact.getHomePhone())) {
      contact.setHomePhone(contactDto.getHomePhone());
    }
    if (contactDto.getEmail() != null &&
        !contactDto.getEmail().equals(contact.getEmail())) {
      contact.setEmail(contactDto.getEmail());
    }
    return contact;
  }

  private Person updatePerson(Person person, ContactDto contactDto) {
    if (!contactDto.getLastName().equals(person.getLastName())) {
      person.setLastName(contactDto.getLastName());
    }
    if (!contactDto.getName().equals(person.getName())) {
      person.setName(contactDto.getName());
    }
    if (!contactDto.getPatronymic().equals(person.getPatronymic())) {
      person.setPatronymic(contactDto.getPatronymic());
    }
    return personService.save(person);
  }

  private Address updateAddress(Address address, ContactDto contactDto) {
    if (isNull(address)) {
      address = new Address();
    }
    if (contactDto.getCity() != null && !contactDto.getCity().equals(address.getCity())) {
      address.setCity(contactDto.getCity());
    }
    if (contactDto.getStreet() != null && !contactDto.getStreet().equals(address.getStreet())) {
      address.setStreet(contactDto.getStreet());
    }
    if (contactDto.getBuilding() != null &&
        !contactDto.getBuilding().equals(address.getBuilding())) {
      address.setBuilding(contactDto.getBuilding());
    }
    if (contactDto.getApartment() != null &&
        !contactDto.getApartment().equals(address.getApartment())) {
      address.setApartment(contactDto.getApartment());
    }
    return addressService.save(address);
  }

  @Override
  public void deleteContact(Integer contactId) {
    Contact contact = contactDao.findOne(contactId);
    if (contact != null) {
      contactDao.delete(contact);
    } else {
      throw new RuntimeException();
    }
  }
}
