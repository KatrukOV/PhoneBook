package com.katruk.domain.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import com.katruk.dao.UserDao;
import com.katruk.domain.DefaultEntity;
import com.katruk.domain.dto.UserDto;
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

import java.util.NoSuchElementException;
import java.util.Optional;

@RunWith(JUnit4.class)
public class UserServiceImplTest {

  @Rule
  public MockitoRule rule = MockitoJUnit.rule();

  @Mock
  private UserDao userDao;
  @Spy
  private UserDto userDto;
  private UserService userService;

  @Before
  public void setUp() throws Exception {
    this.userService = new UserServiceImpl(userDao);
    this.userDto = new DefaultEntity().userDto();
  }

  @Test
  public void createUserEasy() throws Exception {
    //when
    when(this.userDao.saveAndFlush(any(User.class))).thenAnswer(returnsFirstArg());
    User user = this.userService.createUser(this.userDto);

    //then
    assertNotNull(user);
  }


  @Test
  public void createUser_and_check_by_id() throws Exception {
    //given
    Long userId = 2L;

    //when
    when(this.userDao.saveAndFlush(any(User.class))).thenAnswer(new Answer<User>() {
      @Override
      public User answer(InvocationOnMock invocationOnMock) throws Throwable {
        User user = invocationOnMock.getArgumentAt(0, User.class);
        user.setId(userId);
        return user;
      }
    });
    User user = this.userService.createUser(this.userDto);

    //then
    assertNotNull(user);
    assertEquals(user.getId(), userId);
  }

  @Test
  public void getUserByLogin() throws Exception {
    //given
    String login = "Login";

    //when
    when(this.userDao.getUserByLogin(login)).thenReturn(Optional.of(new User()));
    User user = userService.getUserByLogin(login);

    //then
    assertNotNull(user);
  }

  @Test(expected = NoSuchElementException.class)
  public void getUserByLogin_then_return_optional_empty() throws Exception {
    //when
    when(this.userDao.getUserByLogin(anyString())).thenReturn(Optional.empty());
    this.userService.getUserByLogin(anyString());
  }
}