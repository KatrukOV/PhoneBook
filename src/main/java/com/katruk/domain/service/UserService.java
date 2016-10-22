package com.katruk.domain.service;

import com.katruk.domain.entity.User;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

  Map<Integer, User> users = new HashMap<>();

  public UserService() {

  }

  public User getUser(Integer id) {
    if (users.containsKey(id)) {
      return users.get(id);
    } else {
      return null;
    }

  }

  public Map<Integer, User> getAllUser() {
    return users;
  }


}
