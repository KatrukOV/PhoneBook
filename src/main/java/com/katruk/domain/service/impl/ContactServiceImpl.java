package com.katruk.domain.service.impl;

import static java.util.Objects.nonNull;

import com.katruk.dao.ContactDao;
import com.katruk.domain.dto.ContactDto;
import com.katruk.domain.entity.Address;
import com.katruk.domain.entity.Contact;
import com.katruk.domain.entity.Person;
import com.katruk.domain.entity.User;
import com.katruk.domain.service.ContactService;
import com.katruk.domain.service.SecurityService;
import com.katruk.domain.service.UserService;
import com.katruk.domain.util.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class ContactServiceImpl implements ContactService {

  private final ContactDao contactDao;
  private final SecurityService securityService;
  private final UserService userService;

  @Autowired
  public ContactServiceImpl(ContactDao contactDao, SecurityService securityService,
                            UserService userService) {
    this.contactDao = contactDao;
    this.securityService = securityService;
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
    String login = this.securityService.getLogin();
    User user = this.userService.getUserByLogin(login);
    contact.setUser(user);
    System.out.println(">>> addContact contact" + contact);
    return this.contactDao.saveAndFlush(contact);
  }

  @Override
  public Contact editContact(ContactDto contactDto) {
    Contact contact = this.contactDao.getContactById(contactDto.getContactId())
        .orElseThrow(() -> new NoSuchElementException(
            String.format("Contact with id=%s not found", contactDto.getContactId())));
    String login = this.securityService.getLogin();
    User user = this.userService.getUserByLogin(login);
    contact.setUser(user);
    System.out.println(">>> editContact before contact" + contact);
    System.out.println(">>> editContact before contact.user" + contact.getUser());
    contact = updateContact(contact, contactDto);
    System.out.println(">>> editContact after contact" + contact);
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
    System.out.println(">>> getContactDtoByUserLogin user=" + user);
    Set<Contact> contactSet = this.contactDao.getContactByUserId(user.getId());
    Set<ContactDto> contactDtoSet = new HashSet<>();
    ContactDto contactDto;
    for (Contact contact : contactSet) {
      System.out.println(">>> contact=" + contact);
      contactDto = new Converter().makeDtoFromContact(contact);
      contactDtoSet.add(contactDto);
    }
    return contactDtoSet;
  }

  private Contact createContact(ContactDto contactDto) {
    System.out.println(">>> Service 1 contactDto=" + contactDto);
    Contact contact = new Contact();

    Person person = new Person();
    person.setLastName(contactDto.getLastName());
    person.setName(contactDto.getName());
    person.setPatronymic(contactDto.getPatronymic().trim());

    Address address = null;
    if (addressNotEmptyField(contactDto)) {
      address = new Address();
      address.setCity(contactDto.getCity().trim());
      address.setStreet(contactDto.getStreet().trim());
      address.setBuilding(contactDto.getBuilding().trim());
      address.setApartment(contactDto.getApartment());
    }

    String login = this.securityService.getLogin();
    User user = this.userService.getUserByLogin(login);

    contact.setPerson(person);
    contact.setAddress(address);
    contact.setUser(user);
    contact.setMobilePhone(contactDto.getMobilePhone());
    contact.setHomePhone(contactDto.getHomePhone());
    contact.setEmail(contactDto.getEmail());
    System.out.println(">>> Service 2 contact=" + contact);
    return contact;
  }

  private boolean addressNotEmptyField(ContactDto contactDto) {
    return nonNull(contactDto.getCity()) || nonNull(contactDto.getStreet())
           || nonNull(contactDto.getBuilding()) || contactDto.getApartment() > 0;
  }

  private Contact updateContact(Contact contact, ContactDto contactDto) {

    contact.setId(contactDto.getContactId());

    Person person = new Person();
    person.setLastName(contactDto.getLastName());
    person.setName(contactDto.getName());
    person.setPatronymic(contactDto.getPatronymic());

    Address address = null;
    if (addressNotEmptyField(contactDto)) {
      address = new Address();
      address.setCity(contactDto.getCity().trim());
      address.setStreet(contactDto.getStreet().trim());
      address.setBuilding(contactDto.getBuilding().trim());
      address.setApartment(contactDto.getApartment());
    }

    contact.setPerson(person);
    contact.setAddress(address);
    contact.setMobilePhone(contactDto.getMobilePhone());
    contact.setHomePhone(contactDto.getHomePhone());
    contact.setEmail(contactDto.getEmail());
    return contact;
  }
}
