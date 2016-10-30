package com.katruk.domain.service;

import com.katruk.domain.dto.UserDto;
import com.katruk.domain.entity.User;

import java.util.regex.Pattern;

public interface UserService {

  Pattern LOGIN_PATTERN = Pattern.compile("^[A-Za-z]{3,}$");
  Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z]{4,}$");
  Pattern PASSWORD_PATTERN = Pattern.compile("^[A-Za-z0-9]{5,}$");

  User getByLogin(String login);

  UserStatus checkUser(UserDto userDto);

  UserValid regUser(UserDto userDto);

  enum UserValid {
    SUCCESS,
    EXISTS,
    INCORRECT_LAST_NAME,
    INCORRECT_NAME,
    INCORRECT_PATRONYMIC,
    INCORRECT_LOGIN,
    INCORRECT_PASSWORD,
  }
  enum UserStatus {
    OK,
    ABSENT
  }
}
