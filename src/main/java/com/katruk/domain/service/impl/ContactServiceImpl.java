package com.katruk.domain.service.impl;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import com.katruk.dao.ContactDao;
import com.katruk.domain.dto.ContactDto;
import com.katruk.domain.entity.Address;
import com.katruk.domain.entity.Contact;
import com.katruk.domain.entity.Person;
import com.katruk.domain.entity.User;
import com.katruk.domain.service.AddressService;
import com.katruk.domain.service.ContactService;
import com.katruk.domain.service.PersonService;
import com.katruk.domain.service.SecurityService;
import com.katruk.domain.service.UserService;
import com.katruk.domain.util.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Resource;

@Service
public class ContactServiceImpl implements ContactService {

  @Resource(name = "${ContactDao.class}")
  private final ContactDao contactDao;
  private final SecurityService securityService;
  private final PersonService personService;
  private final AddressService addressService;
  private final UserService userService;

  @Autowired
  public ContactServiceImpl(ContactDao contactDao, SecurityService securityService,
                            PersonService personService, AddressService addressService,
                            UserService userService) {
    this.contactDao = contactDao;
    this.securityService = securityService;
    this.personService = personService;
    this.addressService = addressService;
    this.userService = userService;
  }

  @Override
  public Set<ContactDto> getAllContact() {
    String login = this.securityService.getLogin();
    return getContactDtoByUserLogin(login);
  }

  @Override
  public Set<ContactDto> getContactByLastName(String lastName) {
    String login = this.securityService.getLogin();
    Set<ContactDto> allContactDto = getContactDtoByUserLogin(login);
    Set<ContactDto> contactDtoSet = new HashSet<>();
    for (ContactDto contact : allContactDto) {
      if ((contact.getLastName().toLowerCase()).contains(lastName.toLowerCase())) {
        contactDtoSet.add(contact);
      }
    }
    return contactDtoSet;
  }

  @Override
  public Set<ContactDto> getContactByName(String name) {
    String login = this.securityService.getLogin();
    Set<ContactDto> allContactDto = getContactDtoByUserLogin(login);
    Set<ContactDto> contactDtoSet = new HashSet<>();
    for (ContactDto contact : allContactDto) {
      if ((contact.getName().toLowerCase()).contains(name.toLowerCase())) {
        contactDtoSet.add(contact);
      }
    }
    return contactDtoSet;
  }

  @Override
  public Set<ContactDto> getContactByPhone(String phone) {
    String login = this.securityService.getLogin();
    Set<ContactDto> allContactDto = getContactDtoByUserLogin(login);
    Set<ContactDto> contactDtoSet = new HashSet<>();
    for (ContactDto contact : allContactDto) {
      if (containsNumber(phone, contact)) {
        contactDtoSet.add(contact);
      }
    }
    return contactDtoSet;
  }

  @Override
  public ContactDto getContactById(Long contactId) {
    Contact contact = this.contactDao.getContactById(contactId)
        .orElseThrow(() -> new NoSuchElementException(
            String.format("Contact with id=%s not found", contactId)));
    return new Converter().makeDtoFromContact(contact);
  }

  @Override
  public Contact addContact(ContactDto contactDto) {
    Contact contact = createContact(contactDto);
    return this.contactDao.saveAndFlush(contact);
  }

  @Override
  public Contact editContact(ContactDto contactDto) {
    Contact contact = this.contactDao.getContactById(contactDto.getContactId())
        .orElseThrow(() -> new NoSuchElementException(
            String.format("Contact with id=%s not found", contactDto.getContactId())));
    contact = updateContact(contact, contactDto);
    return this.contactDao.saveAndFlush(contact);
  }

  @Override
  public void deleteContact(Long contactId) {
    this.contactDao.delete(contactId);
  }

  private boolean containsNumber(String phone, ContactDto contact) {
    String mobilePhone = contact.getMobilePhone();
    mobilePhone = mobilePhone.replaceAll("-", "").trim();

    String homePhone = contact.getHomePhone();
    homePhone = homePhone.replaceAll("-", "").trim();

    phone = phone.replaceAll("-", "").trim();
    return mobilePhone.contains(phone) || homePhone.contains(phone);
  }

  private Set<ContactDto> getContactDtoByUserLogin(String login) {
    User user = this.userService.getUserByLogin(login);
    Set<ContactDto> contactDtoSet = new HashSet<>();
    ContactDto contactDto;
    for (Contact contact : user.getContacts()) {
      contactDto = new Converter().makeDtoFromContact(contact);
      contactDtoSet.add(contactDto);
    }
    return contactDtoSet;
  }

  private Contact createContact(ContactDto contactDto) {
    Contact contact = new Contact();

    Person person = createPerson(contactDto);
    person = this.personService.save(person);

    Address address = createAddress(contactDto);
    if (nonNull(address)) {
      address = this.addressService.save(address);
    }
    String login = this.securityService.getLogin();
    User user = this.userService.getUserByLogin(login);

    contact.setPerson(person);
    contact.setAddress(address);
    contact.setUser(user);
    contact.setMobilePhone(contactDto.getMobilePhone().trim());
    contact.setHomePhone(contactDto.getHomePhone().trim());
    contact.setEmail(contactDto.getEmail().trim());

    return contact;
  }

  private Address createAddress(ContactDto contactDto) {
    Address address = null;
    if (addressNotEmptyField(contactDto)) {
      address = new Address();
      address.setCity(contactDto.getCity().trim());
      address.setStreet(contactDto.getStreet().trim());
      address.setBuilding(contactDto.getBuilding().trim());
      address.setApartment(contactDto.getApartment());
    }
    return address;
  }

  private boolean addressNotEmptyField(ContactDto contactDto) {
    return nonNull(contactDto.getCity()) || nonNull(contactDto.getStreet())
           || nonNull(contactDto.getBuilding()) || contactDto.getApartment() > 0;
  }

  private Person createPerson(ContactDto contactDto) {
    Person person = new Person();
    person.setLastName(contactDto.getLastName().trim());
    person.setName(contactDto.getName().trim());
    person.setPatronymic(contactDto.getPatronymic().trim());
    return person;
  }

  private Contact updateContact(Contact contact, ContactDto contactDto) {
    Person person = this.personService.getPersonById(contact.getPerson().getId());
    person = updatePerson(person, contactDto);
    contact.setPerson(person);
    Address address = null;
    if (nonNull(contact.getAddress())) {
      address = this.addressService.getAddressById(contact.getAddress().getId());
    }
    address = updateAddress(address, contactDto);
    contact.setAddress(address);

    if (!contactDto.getMobilePhone().equals(contact.getMobilePhone())) {
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
    if (!(contactDto.getLastName().trim()).equals(person.getLastName())) {
      person.setLastName(contactDto.getLastName().trim());
    }
    if (!(contactDto.getName().trim()).equals(person.getName())) {
      person.setName(contactDto.getName().trim());
    }
    if (!(contactDto.getPatronymic().trim()).equals(person.getPatronymic())) {
      person.setPatronymic(contactDto.getPatronymic().trim());
    }
    return this.personService.save(person);
  }

  //todo do refactor
  private Address updateAddress(Address address, ContactDto contactDto) {
    if (isNull(address)) {
      address = new Address();
    }

    if (!addressNotEmptyField(contactDto)) {
      return null;
    }

    if (nonNull(contactDto.getCity()) && !(contactDto.getCity().trim()).equals(address.getCity())) {
      address.setCity(contactDto.getCity().trim());
    }
    if (contactDto.getStreet() != null
        && !(contactDto.getStreet().trim()).equals(address.getStreet())) {
      address.setStreet(contactDto.getStreet().trim());
    }
    if (contactDto.getBuilding() != null
        && !(contactDto.getBuilding().trim()).equals(address.getBuilding())) {
      address.setBuilding(contactDto.getBuilding().trim());
    }
    if (contactDto.getApartment() <= 0
        && !contactDto.getApartment().equals(address.getApartment())) {
      address.setApartment(contactDto.getApartment());
    }
    return this.addressService.save(address);
  }
}
