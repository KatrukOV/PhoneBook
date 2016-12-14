package com.katruk.domain.service;

import com.katruk.domain.entity.Contact;
import com.katruk.domain.entity.dto.ContactDto;

import java.util.Set;

public interface ContactService {

  ContactDto getContactById(Long contactId);

  Contact addContact(ContactDto contactDto);

  Contact editContact(ContactDto contactDto);

  void deleteContact(Long contactId);

  Set<ContactDto> getAllContact();

  Set<ContactDto> getContactByLastName(String lastName);

  Set<ContactDto> getContactByName(String name);

  Set<ContactDto> getContactByPhone(String Phone);

}
