package com.katruk.domain.service.impl;

import com.katruk.dao.UserDao;
import com.katruk.domain.entity.User;
import com.katruk.domain.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserDao userDao;

  @Override
  public User getUserByLogin(String login) {
    User result = userDao.getUserByLogin(login);
    if (null != result) {
      return result;
    }
    return null;
  }

  @Override
  public UserStatus regUser(String lastName, String name, String patronymic,
                            String login, String password) {
    User user = userDao.getUserByLogin(login);
    if (null != user) {
      return UserStatus.EXISTS;
    }
    if (lastName.length() < 5) {
      return UserStatus.INCORRECT_LAST_NAME;
    }
    if (name.length() < 5) {
      return UserStatus.INCORRECT_NAME;
    }
    if (patronymic.length() < 5) {
      return UserStatus.INCORRECT_PATRONYMIC;
    }
    if (!LOGIN_PATTERN.matcher(login).matches()) {
      return UserStatus.INCORRECT_LOGIN;

    }
    if (!PASSWORD_PATTERN.matcher(password).matches()) {
      return UserStatus.INCORRECT_PASSWORD;
    }
    User newUser = new User();
    newUser.setLastName(lastName);
    newUser.setName(name);
    newUser.setPatronymic(patronymic);
    newUser.setLogin(login);
    newUser.setPassword(password);
    userDao.save(newUser);
    return UserStatus.SUCCESS;
  }
}
