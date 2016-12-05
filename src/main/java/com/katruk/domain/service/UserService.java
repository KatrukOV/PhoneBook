package com.katruk.domain.service;

import com.katruk.domain.dto.UserDto;
import com.katruk.domain.entity.User;

public interface UserService {

  User createUser(UserDto userDto);

  User getUserByLogin(String login);

}
