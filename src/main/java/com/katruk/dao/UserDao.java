package com.katruk.dao;

import com.katruk.domain.entity.User;

import java.util.Optional;

public interface UserDao {

  Optional<User> getUserByLogin(String login);

  User saveAndFlush(User user);

}

