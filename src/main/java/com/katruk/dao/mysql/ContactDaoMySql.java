package com.katruk.dao.mysql;

import com.katruk.dao.ContactDao;
import com.katruk.domain.entity.Contact;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Profile(value = "mysql")
@Transactional
public interface ContactDaoMySql extends JpaRepository<Contact, Long>, ContactDao {

}
