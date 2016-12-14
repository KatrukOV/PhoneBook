package com.katruk.domain.validator;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.katruk.domain.DefaultEntity;
import com.katruk.domain.entity.dto.UserDto;
import com.katruk.domain.entity.User;
import com.katruk.domain.service.UserService;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DirectFieldBindingResult;

import java.util.NoSuchElementException;

@RunWith(JUnit4.class)
public class UserValidatorTest {

  @Rule
  public MockitoRule rule = MockitoJUnit.rule();

  @Mock
  private UserService userService;

  @Spy
  private UserDto userDto;
  private BindingResult errors;
  private UserValidator userValidator;

  @Before
  public void setUp() throws Exception {
    this.userValidator = new UserValidator(userService);
    this.userDto = new DefaultEntity().userDto();
    this.errors = new DirectFieldBindingResult(userDto, "userDto");
  }

  @Test
  public void supports() throws Exception {
    //when
    boolean result = this.userValidator.supports(userDto.getClass());

    //then
    assertTrue(result);
  }

  @Test
  public void validate_good_user_without_error() throws Exception {
    //when
    when(userService.getUserByLogin(anyString())).thenThrow(new NoSuchElementException());
    this.userValidator.validate(userDto, errors);

    //then
    verify(userService, times(1)).getUserByLogin(anyString());
    assertFalse(errors.hasErrors());
  }

  @Test
  public void validate_user_with_incorrect_last_name_small_size() throws Exception {
    //given
    this.userDto.setLastName("L");

    //when
    when(this.userService.getUserByLogin(anyString())).thenReturn(mock(User.class));
    this.userValidator.validate(userDto, errors);

    //then
    verify(userService, times(1)).getUserByLogin(anyString());
    assertNotNull(errors.getFieldError("lastName"));
  }

  @Test
  public void validate_user_with_incorrect_last_name_large_size() throws Exception {
    //given
    this.userDto.setLastName("InThisUserDtoLastNameHaveVeryLargeSize");

    //when
    when(userService.getUserByLogin(anyString())).thenReturn(mock(User.class));
    this.userValidator.validate(userDto, errors);

    //then
    verify(userService, times(1)).getUserByLogin(anyString());
    assertNotNull(errors.getFieldError("lastName"));
  }

  @Test
  public void validate_user_with_incorrect_name_small_size() throws Exception {
    //given
    this.userDto.setName("N");

    //when
    when(userService.getUserByLogin(anyString())).thenReturn(mock(User.class));
    this.userValidator.validate(userDto, errors);

    //then
    verify(userService, times(1)).getUserByLogin(anyString());
    assertNotNull(errors.getFieldError("name"));
  }

  @Test
  public void validate_user_with_incorrect_name_large_size() throws Exception {
    //given
    this.userDto.setName("InThisUserDtoNameHaveVeryLargeSize");

    //when
    when(userService.getUserByLogin(anyString())).thenReturn(mock(User.class));
    this.userValidator.validate(userDto, errors);

    //then
    verify(userService, times(1)).getUserByLogin(anyString());
    assertNotNull(errors.getFieldError("name"));
  }

  @Test
  public void validate_user_with_incorrect_patronymic_small_size() throws Exception {
    //given
    this.userDto.setPatronymic("P");

    //when
    when(userService.getUserByLogin(anyString())).thenReturn(mock(User.class));
    this.userValidator.validate(userDto, errors);

    //then
    verify(userService, times(1)).getUserByLogin(anyString());
    assertNotNull(errors.getFieldError("patronymic"));
  }

  @Test
  public void validate_user_with_incorrect_patronymic_large_size() throws Exception {
    //given
    this.userDto.setPatronymic("InThisUserDtoPatronymicHaveVeryLargeSize");

    //when
    when(userService.getUserByLogin(anyString())).thenReturn(mock(User.class));
    this.userValidator.validate(userDto, errors);

    //then
    verify(userService, times(1)).getUserByLogin(anyString());
    assertNotNull(errors.getFieldError("patronymic"));
  }

  @Test
  public void validate_user_with_incorrect_login_small_size() throws Exception {
    //given
    this.userDto.setLogin("L");

    //when
    when(userService.getUserByLogin(anyString())).thenReturn(mock(User.class));
    this.userValidator.validate(userDto, errors);

    //then
    verify(userService, times(1)).getUserByLogin(anyString());
    assertNotNull(errors.getFieldError("login"));
  }

  @Test
  public void validate_user_with_incorrect_login_large_size() throws Exception {
    //given
    this.userDto.setLogin("InThisUserDtoLoginHaveVeryLargeSize");

    //when
    when(userService.getUserByLogin(anyString())).thenReturn(mock(User.class));
    this.userValidator.validate(userDto, errors);

    //then
    verify(userService, times(1)).getUserByLogin(anyString());
    assertNotNull(errors.getFieldError("login"));
  }

  @Test
  public void validate_user_with_incorrect_login_not_english() throws Exception {
    //given
    this.userDto.setLogin("Логин");

    //when
    when(userService.getUserByLogin(anyString())).thenReturn(mock(User.class));
    this.userValidator.validate(userDto, errors);

    //then
    verify(userService, times(1)).getUserByLogin(anyString());
    assertNotNull(errors.getFieldError("login"));
  }

  @Test
  public void validate_user_with_incorrect_password_small_size() throws Exception {
    //given
    this.userDto.setPassword("P");

    //when
    when(userService.getUserByLogin(anyString())).thenReturn(mock(User.class));
    this.userValidator.validate(userDto, errors);

    //then
    verify(userService, times(1)).getUserByLogin(anyString());
    assertNotNull(errors.getFieldError("password"));
  }

  @Test
  public void validate_user_with_incorrect_password_not_english() throws Exception {
    //given
    this.userDto.setPassword("Пароль");

    //when
    when(userService.getUserByLogin(anyString())).thenReturn(mock(User.class));
    this.userValidator.validate(userDto, errors);

    //then
    verify(userService, times(1)).getUserByLogin(anyString());
    assertNotNull(errors.getFieldError("password"));
  }


  @Test
  public void validate_user_with_do_not_match_confirm_password() throws Exception {
    //given
    this.userDto.setConfirmPassword("anotherPassword");

    //when
    when(userService.getUserByLogin(anyString())).thenReturn(mock(User.class));
    this.userValidator.validate(userDto, errors);

    //then
    verify(userService, times(1)).getUserByLogin(anyString());
    assertNotNull(errors.getFieldError("confirmPassword"));
  }

  @Test
  public void validate_user_with_exists_user() throws Exception {
    //given

    //when
    when(userService.getUserByLogin(anyString())).thenAnswer(new Answer<User>() {
      @Override
      public User answer(InvocationOnMock invocationOnMock) throws Throwable {
        User user = new User();
        user.setLogin("Login");
        return user;
      }
    });
    this.userValidator.validate(userDto, errors);

    //then
    verify(userService, times(1)).getUserByLogin(anyString());
    assertNotNull(errors.getFieldError("login"));
  }
}