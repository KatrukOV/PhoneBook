package com.katruk.dao.mysql;

import com.katruk.dao.ContactDao;
import com.katruk.domain.entity.Contact;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Profile("phone_book")
@Repository
@Transactional
public class ContactDaoMySql implements ContactDao {

  @Autowired
  private SessionFactory sessionFactory;

  @Override
  public Contact getById(Integer id) {
    return sessionFactory.getCurrentSession().get(Contact.class, id);
  }

  @Override
  public void save(Contact contact) {
    sessionFactory.getCurrentSession().save(contact);
  }

  @Override
  public void edit(Contact contact) {
    sessionFactory.getCurrentSession().update(contact);
  }

  @Override
  public void delete(Contact contact) {
    sessionFactory.getCurrentSession().delete(contact);
  }
}
