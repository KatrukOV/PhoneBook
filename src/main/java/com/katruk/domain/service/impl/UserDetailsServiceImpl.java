package com.katruk.domain.service.impl;

import com.katruk.domain.entity.User;
import com.katruk.domain.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserService userService;

  @Autowired
//  @Qualifier("UserDaoMySql")
  public UserDetailsServiceImpl(UserService userService) {
    this.userService = userService;
  }

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

//    System.out.println(">>>>> loadUserByUsername login = " + login);
//    boolean enabled = true;
//    boolean accountNonExpired = true;
//    boolean credentialsNonExpired = true;
//    boolean accountNonLocked = true;

    User user = this.userService.getUserByLogin(login);
//    System.out.println(">>>>> loadUserByUsername user = " + user);
    Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

    grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
    System.out.println(">>>>> loadUserByUsername user.getLogin()=" + user.getLogin()
                       + " user.getPassword()=" + user.getPassword()
                       + " grantedAuthorities = " + grantedAuthorities);
    return new org.springframework.security.core.userdetails.User(user.getLogin(),
                                                                  user.getPassword(),
//                                                                  enabled,
//                                                                  accountNonExpired,
//                                                                  credentialsNonExpired,
//                                                                  accountNonLocked,
                                                                  grantedAuthorities);
  }
}