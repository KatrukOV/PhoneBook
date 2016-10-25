package com.katruk.dao;

import com.katruk.domain.entity.User;

public interface UserDao {

  User getUserByLogin(String login);

  void save(User user);
}

