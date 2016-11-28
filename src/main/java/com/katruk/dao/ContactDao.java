package com.katruk.dao;

import com.katruk.domain.entity.Contact;

import java.util.Collection;
import java.util.Optional;

public interface ContactDao {

  Optional<Contact> findOneById(Long contactId);

  Contact saveAndFlush(Contact contact);

  void delete(Long contactId);


}

