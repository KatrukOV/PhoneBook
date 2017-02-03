package com.katruk.config;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserDetailsService userDetailsService;
  private final Logger logger;

  @Autowired
  public SpringSecurityConfig(UserDetailsService userDetailsService, Logger logger) {
    this.userDetailsService = userDetailsService;
    this.logger = logger;
  }

  @Autowired
  public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    this.logger.info("Authentication was configured");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
        .antMatchers("/registration", "/static/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage("/login")
        .defaultSuccessUrl("/contacts", false)
        .usernameParameter("login")
        .passwordParameter("password")
        .permitAll()
        .and()
        .logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .logoutSuccessUrl("/login")
        .permitAll()
        .invalidateHttpSession(true);
  }
}