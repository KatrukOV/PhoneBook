package com.katruk.domain.service.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.katruk.domain.DefaultEntity;
import com.katruk.domain.entity.User;
import com.katruk.domain.service.UserService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class UserDetailsServiceImplTest {

  @InjectMocks
  private UserDetailsService userDetailsService;

  @Mock
  private UserService userService;

  @Before
  public void setUp() throws Exception {
//    MockitoAnnotations.initMocks(this);
    this.userService =mock(UserService.class);
    this.userDetailsService = new UserDetailsServiceImpl(userService);
  }

  @Test
  public void loadUserByUsername() throws Exception {
    String login = "Login";
    User user = new DefaultEntity().user();
    when(this.userService.getUserByLogin(login)).thenReturn(user);

    UserDetails userDetails = this.userDetailsService.loadUserByUsername(login);

//    verify(this.userDetailsService.loadUserByUsername(login));

    assertNotNull(userDetails);
    assertEquals(user.getLogin(), userDetails.getUsername());
  }

}