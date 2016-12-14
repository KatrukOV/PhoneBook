package com.katruk.domain.service;

import com.katruk.domain.entity.User;
import com.katruk.domain.entity.dto.UserDto;

public interface UserService {

  User createUser(UserDto userDto);

  User getUserByLogin(String login);

}
