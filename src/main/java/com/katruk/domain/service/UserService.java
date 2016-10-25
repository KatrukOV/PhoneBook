package com.katruk.domain.service;

import com.katruk.domain.entity.User;

import java.util.regex.Pattern;


public interface UserService {

  Pattern LOGIN_PATTERN = Pattern.compile("^[A-Za-z]{3,}$");
  Pattern PASSWORD_PATTERN = Pattern.compile("^[A-Za-z0-9]{5,}$");

  User getUserByLogin(String login);

  UserStatus regUser(String lastName, String name, String patronymic, String login,
                     String password);

  enum UserStatus {
    SUCCESS,
    EXISTS,
    INCORRECT_LAST_NAME,
    INCORRECT_NAME,
    INCORRECT_PATRONYMIC,
    INCORRECT_LOGIN,
    INCORRECT_PASSWORD,
  }
}
