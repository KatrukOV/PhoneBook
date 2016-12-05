package com.katruk.domain.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.katruk.domain.service.SecurityService;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@RunWith(JUnit4.class)
public class SecurityServiceImplTest {

  @Rule
  public MockitoRule rule = MockitoJUnit.rule();
  @Mock
  private AuthenticationManager authenticationManager;
  @Mock
  private UserDetailsService userDetailsService;
  private SecurityService securityService;

  @Before
  public void setUp() throws Exception {
    this.securityService = new SecurityServiceImpl(authenticationManager, userDetailsService);
  }

  @Test
  public void autoLogin() throws Exception {
    //given
    String login = "Login";
    String password = "Password";

    //when
    when(this.userDetailsService.loadUserByUsername(login)).thenReturn(mock(UserDetails.class));
    when(this.authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .thenReturn(mock(Authentication.class));
    this.securityService.autoLogin(login, password);

    //then
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    assertNotNull(authentication);
  }

  @Test
  public void getLogin() throws Exception {
    //given
    String login = "Login";

    //when
    when(this.securityService.getLogin()).thenReturn(login);
    String result = this.securityService.getLogin();

    //then
    assertNotNull(result);
    assertEquals(login, result);
  }
}