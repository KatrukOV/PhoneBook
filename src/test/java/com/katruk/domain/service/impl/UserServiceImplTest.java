package com.katruk.domain.service.impl;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.katruk.dao.UserDao;
import com.katruk.domain.dto.UserDto;
import com.katruk.domain.entity.User;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.HashSet;
import java.util.Optional;

public class UserServiceImplTest {

  @InjectMocks
  private UserServiceImpl userService;

  @Mock
  private UserDao userDao;
  private UserDto userDto;
  private User user;
  private Long userId;
  private String login;


  @Before
  public void setUp() throws Exception {
    userDao = mock(UserDao.class);
//    MockitoAnnotations.initMocks(this);

    userService = new UserServiceImpl(userDao);

    userDto = new UserDto();
    userDto.setLastName("LastName");
    userDto.setName("Name");
    userDto.setPatronymic("Patronymic");
    userDto.setLogin("Login");
    userDto.setPassword("Password");

    user = new User();
  }

//  @Test
//  public void createUser() throws Exception {
//    userId = 1L;
//
//    when(userDao.saveAndFlush(any(User.class))).thenAnswer(new Answer<User>() {
//      @Override
//      public User answer(InvocationOnMock invocationOnMock) throws Throwable {
//        User user = invocationOnMock.getArgumentAt(0, User.class);
//        user.setId(userId);
//        return null;
//      }
//    });
//
//    assertNull(user.getId());
//
//    user = userService.createUser(userDto);
//
//    assertNotNull(user.getId());
//    assertEquals(user.getId(), userId);
//  }

  @Test
  public void getUserByLogin() throws Exception {
    login = "Login";

    when(userDao.getUserByLogin(login)).thenReturn(Optional.of(new User()));
    User user = userService.getUserByLogin(login);

    assertNotNull(user);
  }
}