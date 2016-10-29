package com.katruk.domain.service.impl;

import com.katruk.dao.UserDao;
import com.katruk.domain.entity.User;
import com.katruk.domain.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserDao userDao;

  @Override
  public User getUserByLogin(String login) {
    return userDao.findUserByLogin(login);
  }

  @Override
  public UserStatus regUser(String lastName, String name, String patronymic,
                            String login, String password) {

    User user = userDao.findUserByLogin(login);

    System.out.println(">>>>>login= "+login);
    System.out.println(">>>>>user= "+user);

    if (null != user) {
      return UserStatus.EXISTS;
    }
    if (!NAME_PATTERN.matcher(lastName).matches()) {
      return UserStatus.INCORRECT_LAST_NAME;
    }
    if (!NAME_PATTERN.matcher(name).matches()) {
      return UserStatus.INCORRECT_NAME;
    }
    if (!NAME_PATTERN.matcher(patronymic).matches()) {
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
    newUser.setPassword(encodePassword(password));
    userDao.save(newUser);

    System.out.println(">>> new User= "+ newUser);

    return UserStatus.SUCCESS;
  }

  //Sha1Hex encryption method
  private String encodePassword(String password) {
    return DigestUtils.sha1Hex(password);
  }
}
