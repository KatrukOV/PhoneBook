package com.katruk.dao;

import com.katruk.domain.entity.Contact;

import java.util.Collection;
import java.util.Optional;

public interface ContactDao {

  Optional<Contact> findOneById(Long contactId);

  Contact saveAndFlush(Contact contact);

  void delete(Contact contact);

//  Collection<Contact> findByLastName(String lastName);
//
//  Collection<Contact> findByName(String name);
//
//  Collection<Contact> findByMobilePhoneLike(String mobilePhone);

}

