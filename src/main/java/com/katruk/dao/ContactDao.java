package com.katruk.dao;

import com.katruk.domain.entity.Contact;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

//@Repository
public interface ContactDao {

  Contact findOne(Integer id);
  Contact save(Contact contact);
  void delete(Contact contact);

}

