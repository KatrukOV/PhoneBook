package com.katruk.dao;

import com.katruk.domain.entity.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

public interface UserDao  {

  User findByLogin(String login);
  User save(User user);

}

