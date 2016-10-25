package com.katruk.domain.service.impl;

import com.katruk.dao.ContactDao;
import com.katruk.domain.entity.Contact;
import com.katruk.domain.service.ContactService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService {

  @Autowired
  private ContactDao contactDao;

  @Override
  public ContactStatus addContact(Contact contact) {
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
    contactDao.edit(contact);
    return ContactStatus.SUCCESS;
  }

  @Override
  public void deleteContact(Integer id) {
    Contact contact = contactDao.getById(id);
    if (contact != null) {
      contactDao.delete(contact);
    } else {
      throw new RuntimeException();
    }
  }
}
