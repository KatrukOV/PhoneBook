package com.katruk.domain.service;

import com.katruk.domain.dto.ContactDto;
import com.katruk.domain.entity.Contact;

import java.util.Set;
import java.util.regex.Pattern;

public interface ContactService {

  Set<ContactDto> getContactDtoByUserLogin(String login);

  ContactDto getById(Long contactId);

  Contact addContact(ContactDto contactDto);

  Contact editContact(ContactDto contactDto);

  void deleteContact(Long contactId);

}
