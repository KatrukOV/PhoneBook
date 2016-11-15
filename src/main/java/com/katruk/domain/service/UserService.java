package com.katruk.domain.service;

import com.katruk.domain.dto.UserDto;
import com.katruk.domain.entity.User;

import java.util.Optional;

public interface UserService {

//  Optional<User> getUserById(long id);

  User getUserByLogin(String login);

  User createUser(UserDto userDto);
}
