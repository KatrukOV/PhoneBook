package com.katruk.domain.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginDto {

  @NotNull
  @Size(min=4, message = "too short")
  private String login;

  @NotNull
  @Size(min=5, message = "too short")
  private String password;

  public LoginDto() {
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "LoginDto{" +
           "login='" + login + '\'' +
           ", password='" + password + '\'' +
           '}';
  }
}
