package com.katruk.dao;

import com.katruk.domain.entity.Contact;

import java.util.Optional;
import java.util.Set;

public interface ContactDao {

  Optional<Contact> getContactById(Long contactId);

  Set<Contact> getContactByUserId(Long userId);

  Contact saveAndFlush(Contact contact);

  void delete(Long contactId);

}

