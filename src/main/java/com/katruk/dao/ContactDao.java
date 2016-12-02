package com.katruk.dao;

import com.katruk.domain.entity.Contact;

import java.util.Optional;

public interface ContactDao {

  Optional<Contact> getContactById(Long contactId);

  Contact saveAndFlush(Contact contact);

  void delete(Long contactId);


}

