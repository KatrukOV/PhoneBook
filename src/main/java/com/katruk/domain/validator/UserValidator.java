package com.katruk.domain.validator;


import static java.util.Objects.nonNull;

import com.katruk.domain.dto.UserDto;
import com.katruk.domain.entity.User;
import com.katruk.domain.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class UserValidator implements Validator {

  private final Pattern LOGIN_PATTERN = Pattern.compile("[A-Za-z]");
  private final Pattern PASSWORD_PATTERN = Pattern.compile("[A-Za-z0-9]");

  private final UserService userService;

  @Autowired
  public UserValidator(UserService userService) {
    this.userService = userService;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return UserDto.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    UserDto userDto = (UserDto) target;

    requiredField(errors);
    validateUserNames(errors, userDto);
    validateLogin(errors, userDto);
    validatePassword(errors, userDto);
    existsUser(errors, userDto);
  }

  private void requiredField(Errors errors) {
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "REQUIRED");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "REQUIRED");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "patronymic", "REQUIRED");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "REQUIRED");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "REQUIRED");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "REQUIRED");
  }

  private void validateUserNames(Errors errors, UserDto userDto) {
    if (userDto.getLastName().length() < 4 || userDto.getLastName().length() > 30) {
      errors.rejectValue("lastName", "INCORRECT_SIZE_LAST_NAME");
    }
    if (userDto.getName().length() < 4 || userDto.getName().length() > 30) {
      errors.rejectValue("name", "INCORRECT_SIZE_NAME");
    }
    if (userDto.getPatronymic().length() < 4 || userDto.getPatronymic().length() > 30) {
      errors.rejectValue("patronymic", "INCORRECT_SIZE_PATRONYMIC");
    }
  }

  private void validateLogin(Errors errors, UserDto userDto) {
    if (userDto.getLogin().length() < 3 || userDto.getLastName().length() > 30) {
      errors.rejectValue("login", "INCORRECT_SIZE_LOGIN");
    }
    if (LOGIN_PATTERN.matcher(userDto.getLogin()).matches()) {
      errors.rejectValue("login", "LOGIN_NOT_ENGLISH");
    }
  }

  private void validatePassword(Errors errors, UserDto userDto) {
    if (userDto.getPassword().length() < 5) {
      errors.rejectValue("password", "INCORRECT_SIZE_PASSWORD");
    }
    if (PASSWORD_PATTERN.matcher(userDto.getPassword()).matches()) {
      errors.rejectValue("password", "PASSWORD_NOT_ENGLISH");
    }
    if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
      errors.rejectValue("confirmPassword", "DIFFERENT_PASSWORD");
    }
  }

  private void existsUser(Errors errors, UserDto userDto) {
    User user = this.userService.getUserByLogin(userDto.getLogin());
    if (nonNull(user.getLogin())) {
      errors.rejectValue("login", "USER_EXISTS");
    }
  }
}