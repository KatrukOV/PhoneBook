package com.katruk.domain.service.impl;

import com.katruk.domain.service.SecurityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailsService;

  @Autowired
  public SecurityServiceImpl(AuthenticationManager authenticationManager,
                             UserDetailsService userDetailsService) {
    this.authenticationManager = authenticationManager;
    this.userDetailsService = userDetailsService;
  }

  @Override
  public void autoLogin(String login, String password) {
//    System.out.println(">>>>> autoLogin login = " + login + " password= " + password);
    UserDetails userDetails = this.userDetailsService.loadUserByUsername(login);

    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(userDetails, password,
                                                userDetails.getAuthorities());
    this.authenticationManager.authenticate(authenticationToken);
    System.out.println(">>>>> authenticationToken  = " + authenticationToken);

    if (authenticationToken.isAuthenticated()) {
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      //todo logger    String.format("Successfully %s auto logged in", login)
    }
  }

  @Override
  public String getLogin() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication.getName();
  }

  @Override
  public String findLogged() {
    Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();

    System.out.println(">>>>> findLogged userDetails = " + userDetails);
    if (userDetails instanceof UserDetails) {
      return ((UserDetails) userDetails).getUsername();
    }
    return null;
  }
}
