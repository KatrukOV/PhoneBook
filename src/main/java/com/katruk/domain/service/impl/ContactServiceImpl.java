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
import com.katruk.domain.util.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class ContactServiceImpl implements ContactService {

  private final ContactDao contactDao;
  private final PersonService personService;
  private final AddressService addressService;
  private final UserService userService;

  @Autowired
  public ContactServiceImpl(ContactDao contactDao, PersonService personService,
                            AddressService addressService, UserService userService) {
    this.contactDao = contactDao;
    this.personService = personService;
    this.addressService = addressService;
    this.userService = userService;
  }

  @Override
  public Set<ContactDto> getContactDtoByUserLogin(String login) {

//    System.out.println(">>> getContactDtoByUserLogin login=" + login);

    User user = this.userService.getUserByLogin(login);

    System.out.println(">>> getContactDtoByUserLogin user=" + user);

    Set<ContactDto> contactDtoSet = new HashSet<>();
    ContactDto contactDto;
    for (Contact contact : user.getContacts()) {
      contactDto = new Converter().makeDtoFromContact(contact);
      contactDtoSet.add(contactDto);
    }
    return contactDtoSet;
  }

  //todo
  @Override
  public ContactDto getById(Long contactId) {
    Contact contact = this.contactDao.findOneById(contactId)
        .orElseThrow(() -> new NoSuchElementException(
            String.format("Contact with id=%s not found", contactId)));
    return new Converter().makeDtoFromContact(contact);
  }

  @Override
  public Contact addContact(ContactDto contactDto) {
    System.out.println("begin addContact contactDto= " + contactDto);
    Contact contact = createContact(contactDto);
    return this.contactDao.saveAndFlush(contact);
  }

  @Override
  public Contact editContact(ContactDto contactDto) {
    Contact contact = this.contactDao.findOneById(contactDto.getContactId())
        .orElseThrow(() -> new NoSuchElementException(
            String.format("Contact with id=%s not found", contactDto.getContactId())));
    System.out.println(">>> contact to update " + contact);
    contact = updateContact(contact, contactDto);
    return this.contactDao.saveAndFlush(contact);
  }

  private Contact createContact(ContactDto contactDto) {
    Contact contact = new Contact();
    Person person = new Person();
    person.setLastName(contactDto.getLastName());
    person.setName(contactDto.getName());
    person.setPatronymic(contactDto.getPatronymic());

    System.out.println("Before saving person =" + person);
    person = this.personService.save(person);

    Address address = new Address();
    address.setCity(contactDto.getCity());
    address.setStreet(contactDto.getStreet());
    address.setBuilding(contactDto.getBuilding());
    address.setApartment(contactDto.getApartment());

    System.out.println("Before saving address =" + address);
    address = this.addressService.save(address);

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String login = authentication.getName();
    System.out.println(">>> createContact login =" + login);
    User user = this.userService.getUserByLogin(login);

    contact.setPerson(person);
    contact.setAddress(address);
    contact.setUser(user);
    contact.setMobilePhone(contactDto.getMobilePhone());
    contact.setHomePhone(contactDto.getHomePhone());
    contact.setEmail(contactDto.getEmail());
    return contact;
  }

  private Contact updateContact(Contact contact, ContactDto contactDto) {
    Person person = this.personService.getPersonById(contact.getPerson().getId());
    person = updatePerson(person, contactDto);
    Address address = this.addressService.getAddressById(contact.getAddress().getId());
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
    return this.personService.save(person);
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
    return this.addressService.save(address);
  }

  @Override
  public void deleteContact(Long contactId) {
    Contact contact = this.contactDao.findOneById(contactId)
        .orElseThrow(() -> new NoSuchElementException(
            String.format("Contact with id=%s not found", contactId)));
    System.out.println(">>> deleteContact contact= " + contact);
    this.contactDao.delete(contact);

  }
}
