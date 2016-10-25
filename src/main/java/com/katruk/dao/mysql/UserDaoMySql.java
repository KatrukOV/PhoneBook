package com.katruk.dao.mysql;

import com.katruk.dao.UserDao;
import com.katruk.domain.entity.User;
import javax.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Profile("phone_book")
@Repository
@Transactional
public class UserDaoMySql implements UserDao {

  @Autowired
  private SessionFactory sessionFactory;

  @Override
  public User getUserByLogin(String login) {
    return sessionFactory.getCurrentSession().get(User.class, login);
  }

  @Override
  public void save(User user) {
    sessionFactory.getCurrentSession().save(user);
  }
}
