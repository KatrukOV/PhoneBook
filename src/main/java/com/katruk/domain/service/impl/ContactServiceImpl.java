package com.katruk.domain.service.impl;

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
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class ContactServiceImpl implements ContactService {

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

    Person person = this.personService.create(contactDto);
    Address address = this.addressService.create(contactDto);

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

  private Contact updateContact(Contact contact, ContactDto contactDto) {

    Person person = this.personService.updatePerson(contact.getPerson().getId(), contactDto);

    Long addressId = null;
    if (nonNull(contact.getAddress())) {
      addressId = contact.getAddress().getId();
    }
    Address address = this.addressService.updateAddress(addressId, contactDto);

    contact.setPerson(person);
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
}
