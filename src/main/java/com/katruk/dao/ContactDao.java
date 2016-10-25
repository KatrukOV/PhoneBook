package com.katruk.dao;

import com.katruk.domain.entity.Contact;
import com.katruk.domain.entity.User;

public interface ContactDao {

  Contact getById(Integer id);
  void save(Contact contact);
  void edit(Contact contact);
  void delete(Contact contact);

}

