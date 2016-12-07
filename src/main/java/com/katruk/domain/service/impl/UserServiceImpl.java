package com.katruk.domain.service.impl;

import com.katruk.dao.UserDao;
import com.katruk.domain.dto.UserDto;
import com.katruk.domain.entity.User;
import com.katruk.domain.service.UserService;
import com.katruk.domain.util.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;
import javax.annotation.Resource;


@Service
public class UserServiceImpl implements UserService {

//  @Resource(lookup = "java:com/katruk/dao/UserDaoMySql")
  @Resource(mappedName = "UserDaoMySql")
  private final UserDao userDao;

  public UserServiceImpl(UserDao userDao) {
    this.userDao = userDao;
  }

  //  @Autowired
//  public UserServiceImpl(@Qualifier("${UserDao.class}") UserDao userDao) {
//    this.userDao = userDao;
//  }
//  @Autowired
//  public UserServiceImpl(@Qualifier("UserDaoMySql") UserDao userDao) {
//    this.userDao = userDao;
//  }

  @Override
  public User createUser(UserDto userDto) {
    User newUser = new Converter().makeUserFromDto(userDto);
    return this.userDao.saveAndFlush(newUser);
  }

  @Override
  public User getUserByLogin(String login) {
    return this.userDao.getUserByLogin(login).orElseThrow(() -> new NoSuchElementException(
        String.format("No user present with login: %s", login)));
  }
}