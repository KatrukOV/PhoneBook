package com.katruk.dao.mysql;

import com.katruk.dao.ContactDao;
import com.katruk.domain.entity.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ContactDaoMySql extends CrudRepository<Contact, Integer>, ContactDao {


}
