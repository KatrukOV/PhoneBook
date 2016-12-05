package com.katruk.domain.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import com.katruk.domain.DefaultEntity;
import com.katruk.domain.entity.User;
import com.katruk.domain.service.UserService;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@RunWith(JUnit4.class)
public class UserDetailsServiceImplTest {

  @Rule
  public MockitoRule rule = MockitoJUnit.rule();
  @Mock
  private UserService userService;
  private UserDetailsService userDetailsService;

  @Before
  public void setUp() throws Exception {
    this.userDetailsService = new UserDetailsServiceImpl(userService);
  }

  @Test
  public void loadUserByUsername() throws Exception {
    //given
    String login = "Login";
    User user = new DefaultEntity().user();

    //when
    when(this.userService.getUserByLogin(login)).thenReturn(user);
    UserDetails userDetails = this.userDetailsService.loadUserByUsername(login);

    //then
    assertNotNull(userDetails);
    assertEquals(user.getLogin(), userDetails.getUsername());
  }
}