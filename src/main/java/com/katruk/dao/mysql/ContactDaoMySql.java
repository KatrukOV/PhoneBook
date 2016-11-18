package com.katruk.dao.mysql;

import com.katruk.dao.ContactDao;
import com.katruk.domain.entity.Contact;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository(value = "ContactDaoMySql")
@Transactional
public interface ContactDaoMySql extends JpaRepository<Contact, Long>, ContactDao {

}