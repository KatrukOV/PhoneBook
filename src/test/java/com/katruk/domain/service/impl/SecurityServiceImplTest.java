package com.katruk.domain.service.impl;

import static org.junit.Assert.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.katruk.domain.DefaultEntity;
import com.katruk.domain.entity.User;
import com.katruk.domain.service.SecurityService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class SecurityServiceImplTest {

  @InjectMocks
  private SecurityService securityService;

  @Mock
  private AuthenticationManager authenticationManager;
  @Mock
  private UserDetailsService userDetailsService;

  @Before
  public void setUp() throws Exception {
    // TODO: 03.12.2016   initMocks
//    MockitoAnnotations.initMocks(this);
    authenticationManager = mock(AuthenticationManager.class);
    userDetailsService = mock(UserDetailsService.class);

    this.securityService = new SecurityServiceImpl(authenticationManager, userDetailsService);
  }

  @Test
  public void autoLogin() throws Exception {
    String login = "Login";
    String password = "Password";
    when(this.userDetailsService.loadUserByUsername(login)).thenReturn(mock(UserDetails.class));
    when(this.authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .thenReturn(mock(Authentication.class));

    this.securityService.autoLogin(login, password);

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    assertNotNull(authentication);
  }

  @Test
  public void getLogin_null() throws Exception {
    Authentication authenticationBefore = SecurityContextHolder.getContext().getAuthentication();

    assertNull(authenticationBefore);
  }

  @Test
  public void getLogin_not_null() throws Exception {
    SecurityContextHolder.getContext().setAuthentication(mock(Authentication.class));
    Authentication authenticationAfter = SecurityContextHolder.getContext().getAuthentication();

    assertNotNull(authenticationAfter);
  }

}