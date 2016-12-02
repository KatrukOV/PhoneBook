package com.katruk.domain.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.katruk.dao.UserDao;
import com.katruk.domain.DefaultEntity;
import com.katruk.domain.dto.UserDto;
import com.katruk.domain.entity.User;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Optional;

public class UserServiceImplTest {

  @InjectMocks
  private UserServiceImpl userService;

  @Mock
  private UserDao userDao;
  private UserDto userDto;
  private User user;



  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    this.userService = new UserServiceImpl(userDao);
    this.userDto = new DefaultEntity().userDto();
    this.user = new DefaultEntity().user();

  }
  @Test
  public void createUserEasy() throws Exception {

    when(this.userDao.saveAndFlush(any(User.class))).thenAnswer(returnsFirstArg());

    User user = this.userService.createUser(this.userDto);

    assertNotNull(user);
  }


  @Test
  public void createUser_and_check_by_id() throws Exception {
    Long userId = 2L;

    when(this.userDao.saveAndFlush(any(User.class))).thenAnswer(new Answer<User>() {
      @Override
      public User answer(InvocationOnMock invocationOnMock) throws Throwable {
        User user = invocationOnMock.getArgumentAt(0, User.class);
        user.setId(userId);
        return user;
      }
    });

    User user = this.userService.createUser(this.userDto);

    assertNotNull(user);
    assertEquals(user.getId(), userId);
  }

  @Test
  public void getUserByLogin() throws Exception {
    String login = "Login";

    when(this.userDao.getUserByLogin(login)).thenReturn(Optional.of(new User()));
    User user = userService.getUserByLogin(login);

    assertNotNull(user);
  }
}