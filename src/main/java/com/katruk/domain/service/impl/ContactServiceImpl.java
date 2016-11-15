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
  public Set<ContactDto> getByUserLogin(String login) {

    System.out.println(">>> getByUserLogin login=" + login);

    User user = userService.getUserByLogin(login);

    System.out.println(">>> user" + user);

    Set<ContactDto> contactDtoSet = new HashSet<>();
    ContactDto contactDto;
    for (Contact contact : user.getContacts()) {
      contactDto = new Converter().makeContactDto(contact);
      contactDtoSet.add(contactDto);
    }
    return contactDtoSet;
  }

  //todo
  @Override
  public ContactDto getById(Long contactId) {
    Contact contact = contactDao.findOneById(contactId)
        .orElseThrow(() -> new NoSuchElementException(
            String.format("Contact with id=%s not found", contactId)));
    return new Converter().makeContactDto(contact);
  }

  @Override
  public Contact addContact(ContactDto contactDto) {
    System.out.println("begin addContact contactDto= " + contactDto);
    Contact contact = createContact(contactDto);
    return contactDao.saveAndFlush(contact);
  }

  @Override
  public Contact editContact(ContactDto contactDto) {
    return null;
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

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String login = authentication.getName();
    System.out.println(">>> createContact login =" + login);
    User user = userService.getUserByLogin(login);

    contact.setPerson(person);
    contact.setAddress(address);
    contact.setUser(user);
    contact.setMobilePhone(contactDto.getMobilePhone());
    contact.setHomePhone(contactDto.getHomePhone());
    contact.setEmail(contactDto.getEmail());
    return contact;
  }

//  private ContactStatus isContactValid(ContactDto contactDto) {
//    if (contactDto.getLastName().length() < 4) {
//      return ContactStatus.INCORRECT_LAST_NAME;
//    }
//    if (contactDto.getName().length() < 4) {
//      return ContactStatus.INCORRECT_NAME;
//    }
//    if (contactDto.getPatronymic().length() < 4) {
//      return ContactStatus.INCORRECT_PATRONYMIC;
//    }
//    if (!PHONE_PATTERN.matcher(contactDto.getMobilePhone()).matches()) {
//      return ContactStatus.INCORRECT_MOBILE_PHONE;
//    }
//    if (!contactDto.getHomePhone().isEmpty() &&
//        !PHONE_PATTERN.matcher(contactDto.getHomePhone()).matches()) {
//      return ContactStatus.INCORRECT_HOME_PHONE;
//    }
//    if (!contactDto.getEmail().isEmpty() &&
//        !EMAIL_PATTERN.matcher(contactDto.getEmail()).matches()) {
//      return ContactStatus.INCORRECT_EMAIL;
//    }
//    return null;
//  }

//  @Override
//  public ContactStatus editContact(ContactDto contactDto) {
//    ContactStatus incorrectStatus = isContactValid(contactDto);
//    if (incorrectStatus != null) {
//      return incorrectStatus;
//    }
//    Contact contact = contactDao.findOneById(contactDto.getContactId())
//        .orElseThrow(() -> new NoSuchElementException(
//            String.format("Person=%s not found", contactDto.getContactId())));
//    System.out.println("contact to update " + contact);
//    System.out.println("contact to update for id " + contact.getContactId());
//    contact = updateContact(contact, contactDto);
//    contactDao.saveAndFlush(contact);
//    return ContactStatus.SUCCESS;
//  }

  private Contact updateContact(Contact contact, ContactDto contactDto) {
    Person person = personService.getPersonById(contact.getPerson().getId());
    person = updatePerson(person, contactDto);
    Address address = addressService.getAddressById(contact.getAddress().getId());
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
  public void deleteContact(Long contactId) {
    Contact contact = contactDao.findOneById(contactId)
        .orElseThrow(() -> new NoSuchElementException(
            String.format("Contact=%s not found", contactId)));
    if (contact != null) {
      contactDao.delete(contact);
    } else {
      //todo logic
      throw new RuntimeException();
    }
  }
}
