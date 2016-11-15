package com.katruk.config;

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
public class SpringSecurityWebAppConfig extends WebSecurityConfigurerAdapter {

  private final UserDetailsService userDetailsService;

  @Autowired
  public SpringSecurityWebAppConfig(UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

//  @Autowired
//  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//    System.out.println("!!! configureGlobal auth= "+ auth);
//    auth.userDetailsService(this.userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
//    System.out.println("!!! configureGlobal after auth= "+ auth);
//  }

  @Autowired
  public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
    System.out.println("!!! configAuthentication auth= "+ auth);
    auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    System.out.println("!!! configAuthentication after auth= "+ auth);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
        .antMatchers(/*"/",*/ "/registration", "/static/**").permitAll()
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
//        .and()
//        .csrf()
    ;
  }

//  @Autowired
//  public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {

  // In memory authentication for testing
//    auth
//        .inMemoryAuthentication()
//        .withUser("user").password("password").roles("ADMIN");

  // Uncomment this to use the database
//    auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
//  }

}

//  @Bean(name = "passwordEncoder")
//  public PasswordEncoder passwordencoder() {
//    return new BCryptPasswordEncoder();
//  }



/*
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
        .antMatchers("/", "/home").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage("/login")
        .permitAll()
        .and()
        .logout()
        .permitAll();
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDS);
  }

  @Override
  protected UserDetailsService userDetailsService() {
    return userDS;
  }
}

 */

//    http.csrf().disable()
//        .authorizeRequests()
//        .antMatchers("/", "/resources/**", "/registration/**").permitAll()
//        .anyRequest().authenticated();
//    http.formLogin()
//        .loginPage("/login")
//        .loginProcessingUrl("/${contextPath}/login")
//        .failureUrl("/login?error")
//        .usernameParameter("login")
//        .passwordParameter("password")
//        .permitAll();
//    http.logout()
//        .permitAll()
//        .logoutUrl("/logout")
//        .logoutSuccessUrl("/login?logout")
//        .invalidateHttpSession(true);