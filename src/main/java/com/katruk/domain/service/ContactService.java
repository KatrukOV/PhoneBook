package com.katruk.domain.service;

import com.katruk.domain.entity.Contact;

import java.util.regex.Pattern;

public interface ContactService {

  Pattern PHONE_PATTERN = Pattern.compile("^\\+380\\(\\d{2}\\)\\d{3}-{0,1}\\d{2}-{0,1}\\d{2}$");
//  Pattern EMAIL_PATTERN = Pattern.compile("^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$");
  Pattern EMAIL_PATTERN = Pattern.compile("^([A-Za-z0-9_-]+\\.)*[A-Za-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$");

  Contact getById(Integer id);
  ContactStatus addContact(Contact contact);
  ContactStatus editContact(Contact contact);
  void deleteContact(Integer id);

  enum ContactStatus {
    SUCCESS,
    INCORRECT_LAST_NAME,
    INCORRECT_NAME,
    INCORRECT_PATRONYMIC,
    INCORRECT_MOBILE_PHONE,
    INCORRECT_HOME_PHONE,
    INCORRECT_EMAIL,
  }
}
