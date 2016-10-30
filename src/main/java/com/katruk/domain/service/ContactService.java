package com.katruk.domain.service;

import com.katruk.domain.dto.ContactDto;
import com.katruk.domain.entity.Contact;

import java.util.Set;
import java.util.regex.Pattern;

public interface ContactService {

  Pattern PHONE_PATTERN = Pattern.compile("^\\+380\\(\\d{2}\\)\\d{3}-{0,1}\\d{2}-{0,1}\\d{2}$");
  Pattern EMAIL_PATTERN = Pattern.compile("^([A-Za-z0-9_-]+\\.)*[A-Za-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$");

  Set<ContactDto> getByUserLogin(String login);
  ContactDto getById(Integer contactId);
  ContactStatus addContact(ContactDto contactDto);
  ContactStatus editContact(ContactDto contactDto);
  void deleteContact(Integer contactId);

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
