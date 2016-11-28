package com.katruk.domain.service.impl;

import com.katruk.dao.UserDao;
import com.katruk.domain.dto.UserDto;
import com.katruk.domain.entity.User;
import com.katruk.domain.service.UserService;
import com.katruk.domain.util.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserDao userDao;

  @Autowired()
  public UserServiceImpl(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public User getUserByLogin(String login) {
    return this.userDao.getUserByLogin(login).orElse(new User());
  }

  @Override
  public User createUser(UserDto userDto) {
    User newUser = new Converter().makeUserFromDto(userDto);
    return this.userDao.saveAndFlush(newUser);
  }
}