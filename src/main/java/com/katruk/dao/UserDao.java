package com.katruk.dao;

import com.katruk.domain.entity.User;

public interface UserDao {

  User getUserByLogin(String login);

  <S extends User> S save(S s);

}

