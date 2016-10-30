package com.katruk.domain.service.impl;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import com.katruk.dao.UserDao;
import com.katruk.domain.dto.UserDto;
import com.katruk.domain.entity.User;
import com.katruk.domain.service.UserService;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired()
  @Qualifier("UserDaoMySql")
  private UserDao userDao;

  @Override
  public User getByLogin(String login) {
    return userDao.findByLogin(login);
  }

  @Override
  public UserStatus checkUser(UserDto userDto) {
    User user = userDao.findByLogin(userDto.getLogin());
    String encodedPassword = DigestUtils.sha1Hex(userDto.getPassword());
    if (isNull(user) || !user.getPassword().equals(encodedPassword)) {
      return UserStatus.ABSENT;
    }
    return UserStatus.OK;
  }

  @Override
  public UserValid regUser(UserDto userDto) {
    User user = userDao.findByLogin(userDto.getLogin());
    if (nonNull(user)) {
      return UserValid.EXISTS;
    }
    if (!NAME_PATTERN.matcher(userDto.getLastName()).matches()) {
      return UserValid.INCORRECT_LAST_NAME;
    }
    if (!NAME_PATTERN.matcher(userDto.getName()).matches()) {
      return UserValid.INCORRECT_NAME;
    }
    if (!NAME_PATTERN.matcher(userDto.getPatronymic()).matches()) {
      return UserValid.INCORRECT_PATRONYMIC;
    }
    if (!LOGIN_PATTERN.matcher(userDto.getLogin()).matches()) {
      return UserValid.INCORRECT_LOGIN;
    }
    if (!PASSWORD_PATTERN.matcher(userDto.getPassword()).matches()) {
      return UserValid.INCORRECT_PASSWORD;
    }
    User newUser = new User();
    newUser.setLastName(userDto.getLastName());
    newUser.setName(userDto.getName());
    newUser.setPatronymic(userDto.getPatronymic());
    newUser.setLogin(userDto.getLogin());
    newUser.setPassword(encodePassword(userDto.getPassword()));
    userDao.save(newUser);
    return UserValid.SUCCESS;
  }

  //Sha1Hex encryption method
  private String encodePassword(String password) {
    return DigestUtils.sha1Hex(password);
  }
}
