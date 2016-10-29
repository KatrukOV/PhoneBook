package com.katruk.domain.service.impl;

import com.katruk.dao.ContactDao;
import com.katruk.domain.entity.Address;
import com.katruk.domain.entity.Contact;
import com.katruk.domain.entity.Person;
import com.katruk.domain.service.AddressService;
import com.katruk.domain.service.ContactService;

import com.katruk.domain.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService {

  @Autowired
  private ContactDao contactDao;

  @Autowired
  private PersonService personService;

  @Autowired
  private AddressService addressService;


  @Override
  public Contact getById(Integer id) {
    return contactDao.findOne(id);
  }

  @Override
  public ContactStatus addContact(Contact contact) {
//    if (null == contactDao.findOne(contact.getId())) {
//      return ContactStatus.EXISTS;
//    }
    ContactStatus x = isContactValid(contact);
    if (x != null) return x;
    Person person = personService.save(contact.getPerson());
    contact.setPerson(person);
    Address address = addressService.save(contact.getAddress());
    contact.setAddress(address);

    System.out.println("Before saving");
    contactDao.save(contact);
    return ContactStatus.SUCCESS;
  }

  private ContactStatus isContactValid(Contact contact) {
    if (contact.getPerson().getLastName().length() < 4) {
      return ContactStatus.INCORRECT_LAST_NAME;
    }
    if (contact.getPerson().getName().length() < 4) {
      return ContactStatus.INCORRECT_NAME;
    }
    if (contact.getPerson().getPatronymic().length() < 4) {
      return ContactStatus.INCORRECT_PATRONYMIC;
    }
    if (!PHONE_PATTERN.matcher(contact.getMobilePhone()).matches()) {
      return ContactStatus.INCORRECT_MOBILE_PHONE;
    }
    if (!contact.getHomePhone().isEmpty() &&
        !PHONE_PATTERN.matcher(contact.getHomePhone()).matches()) {
      return ContactStatus.INCORRECT_HOME_PHONE;
    }
    if (!contact.getEmail().isEmpty() &&
        !EMAIL_PATTERN.matcher(contact.getEmail()).matches()) {
      return ContactStatus.INCORRECT_EMAIL;
    }
    return null;
  }

  @Override
  public ContactStatus editContact(Contact contact) {
    if (contact.getPerson().getLastName().length() < 4) {
      return ContactStatus.INCORRECT_LAST_NAME;
    }
    if (contact.getPerson().getName().length() < 4) {
      return ContactStatus.INCORRECT_NAME;
    }
    if (contact.getPerson().getPatronymic().length() < 4) {
      return ContactStatus.INCORRECT_PATRONYMIC;
    }
    if (!PHONE_PATTERN.matcher(contact.getMobilePhone()).matches()) {
      return ContactStatus.INCORRECT_MOBILE_PHONE;
    }
    if (!contact.getHomePhone().isEmpty() &&
        !PHONE_PATTERN.matcher(contact.getHomePhone()).matches()) {
      return ContactStatus.INCORRECT_HOME_PHONE;
    }
    if (!contact.getEmail().isEmpty() &&
        !EMAIL_PATTERN.matcher(contact.getEmail()).matches()) {
      return ContactStatus.INCORRECT_EMAIL;
    }
    contactDao.save(contact);
    return ContactStatus.SUCCESS;
  }

  @Override
  public void deleteContact(Integer id) {
    Contact contact = contactDao.findOne(id);
    if (contact != null) {
      contactDao.delete(contact);
    } else {
      throw new RuntimeException();
    }
  }
}
