package com.katruk.domain.validator;

import com.katruk.domain.dto.UserDto;
import com.katruk.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

@Component
public class UserValidator implements Validator {

  private final Pattern LOGIN_PATTERN = Pattern.compile("[A-Za-z]+");
  private final Pattern PASSWORD_PATTERN = Pattern.compile("[A-Za-z0-9]+");
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
    validateUserNames(userDto, errors);
    validateLogin(userDto, errors);
    validatePassword(userDto, errors);
    existsUser(userDto, errors);
  }

  private void requiredField(Errors errors) {
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "required");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "patronymic", "required");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "required");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "required");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "required");
  }

  private void validateUserNames(UserDto userDto, Errors errors) {
    if (userDto.getLastName().length() < 4 || userDto.getLastName().length() > 30) {
      errors.rejectValue("lastName", "lastName.size");
    }
    if (userDto.getName().length() < 4 || userDto.getName().length() > 30) {
      errors.rejectValue("name", "name.size");
    }
    if (userDto.getPatronymic().length() < 4 || userDto.getPatronymic().length() > 30) {
      errors.rejectValue("patronymic", "patronymic.size");
    }
  }

  private void validateLogin(UserDto userDto, Errors errors) {
    if (userDto.getLogin().length() < 3 || userDto.getLogin().length() > 30) {
      errors.rejectValue("login", "login.size");
    }
    if (!LOGIN_PATTERN.matcher(userDto.getLogin()).matches()) {
      errors.rejectValue("login", "login.not.english");
    }
  }

  private void validatePassword(UserDto userDto, Errors errors) {
    if (userDto.getPassword().length() < 5) {
      errors.rejectValue("password", "password.size");
    }
    if (!PASSWORD_PATTERN.matcher(userDto.getPassword()).matches()) {
      errors.rejectValue("password", "password.not.english");
    }
    if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
      errors.rejectValue("confirmPassword", "password.different");
    }
  }

  private void existsUser(UserDto userDto, Errors errors) {
    try {
      this.userService.getUserByLogin(userDto.getLogin());
      errors.rejectValue("login", "user.exists");
    } catch (NoSuchElementException e) {
      //log
    }
  }
}