package com.katruk.domain.validator;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.katruk.domain.dto.UserDto;
import com.katruk.domain.service.UserService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.Errors;

//@RunWith(MockitoJUnitRunner.class)
public class UserValidatorTest {

  @InjectMocks
  private UserValidator userValidator;

  @Mock
  private UserService userService;

  @Mock
  private UserDto userDto;

  @Mock
  private Errors errors;

  @Before
  public void setUp() throws Exception {

//    MockitoAnnotations.initMocks(this);
    userService = mock(UserService.class);
    userValidator = new UserValidator(userService);

    userDto.setLastName("LastName");
    userDto.setName("Name");
    userDto.setPatronymic("Patronymic");
    userDto.setLogin("Login");
    userDto.setPassword("Password");
    userDto.setConfirmPassword("Password");
  }

//  @Test
//  public void validate() throws Exception {
////    when(userValidator.validate(userDto, errors))
//
//    userValidator.validate(userDto, errors);
//
//    assertFalse(errors.hasErrors());
//
//    verify(userValidator, times(1)).validate(userDto, errors);
//  }

}