package com.katruk.ui.controller;

import com.katruk.domain.entity.User;
import com.katruk.domain.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {

  @Autowired
  private UserService userService;

  @RequestMapping("/all")
  public Map<Integer, User> getAllUser() {
    return userService.getAllUser();
  }

  @RequestMapping("{id}")
  public User getUser(@PathVariable("id") Integer id) {
    return userService.getUser(id);
  }

}
