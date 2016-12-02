package com.katruk.domain.validator;

import static org.mockito.Mockito.mock;

import com.katruk.domain.dto.UserDto;
import com.katruk.domain.service.UserService;

import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.validation.Errors;

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