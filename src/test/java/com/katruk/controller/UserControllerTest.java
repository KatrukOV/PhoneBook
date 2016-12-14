package com.katruk.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.katruk.domain.DefaultEntity;
import com.katruk.domain.entity.dto.UserDto;
import com.katruk.domain.entity.User;
import com.katruk.domain.service.MessageService;
import com.katruk.domain.service.SecurityService;
import com.katruk.domain.service.UserService;
import com.katruk.domain.validator.UserValidator;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DirectFieldBindingResult;

@RunWith(JUnit4.class)
public class UserControllerTest {

  @Rule
  public MockitoRule rule = MockitoJUnit.rule();
  @Mock
  private UserService userService;
  @Mock
  private UserValidator userValidator;
  @Mock
  private SecurityService securityService;
  @Mock
  private MessageService messageService;
  private Model model;
  private UserController userController;

  @Before
  public void setUp() throws Exception {
    this.userController =
        new UserController(userService, userValidator, securityService, messageService);
    this.model = new ExtendedModelMap();
  }

  @Test
  public void login() throws Exception {
    //when
    String result = this.userController.login(model, null, null);

    //then
    assertEquals("login", result);
  }

  @Test
  public void login_error() throws Exception {
    //given
    String error = "error";

    //when
    this.userController.login(model, error, null);

    //then
    assertTrue(model.containsAttribute(error));
  }

  @Test
  public void login_logout() throws Exception {
    //given
    String logout = "logout";

    //when
    this.userController.login(model, null, logout);

    //then
    assertTrue(model.containsAttribute(logout));
  }

  @Test
  public void registration() throws Exception {
    //when
    String result = this.userController.registration(null);

    //then
    assertEquals("registration", result);
  }

  @Test
  public void doRegistration_success() throws Exception {
    //given
    UserDto userDto = new DefaultEntity().userDto();
    User user = new DefaultEntity().user();
    BindingResult error = new DirectFieldBindingResult(userDto, "userDto");

    //when
    whenDefaultScript(user);
    String result = this.userController.doRegistration(userDto, error);

    //then
    assertEquals("redirect:/contacts", result);
  }

  @Test
  public void doRegistration_fail() throws Exception {
    //given
    UserDto userDto = new DefaultEntity().userDto();
    User user = new DefaultEntity().user();
    BindingResult error = new DirectFieldBindingResult(userDto, "userDto");
    error.reject("error");

    //when
    whenDefaultScript(user);
    String result = this.userController.doRegistration(userDto, error);

    //then
    verify(this.userValidator,times(1)).validate(any(UserDto.class), any(BindingResult.class));
    verify(this.securityService, times(0)).autoLogin(anyString(), anyString());
    verify(this.messageService, times(0)).addInfo(anyString());
    verify(this.messageService, times(1)).addError(anyString());
    verify(this.userService, times(0)).createUser(any(UserDto.class));
    assertEquals("registration", result);
  }

  private void whenDefaultScript(User user) {
    doNothing().when(this.userValidator).validate(any(UserDto.class), any(BindingResult.class));
    doNothing().when(this.messageService).addError(anyString());
    doNothing().when(this.messageService).addInfo(anyString());
    doNothing().when(this.securityService).autoLogin(anyString(), anyString());
    when(this.userService.createUser(any(UserDto.class))).thenReturn(user);
  }
}