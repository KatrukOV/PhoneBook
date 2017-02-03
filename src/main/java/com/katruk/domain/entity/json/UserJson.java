package com.katruk.domain.entity.json;

public class UserJson {

  private Long userId;
  private String lastName;
  private String name;
  private String patronymic;
  private String login;
  private String password;

  public UserJson() {
  }

  @Override
  public String toString() {
    return "UserJson{" +
           "userId=" + userId +
           ", lastName='" + lastName + '\'' +
           ", name='" + name + '\'' +
           ", patronymic='" + patronymic + '\'' +
           ", login='" + login + '\'' +
           ", password='" + password + '\'' +
           '}';
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPatronymic() {
    return patronymic;
  }

  public void setPatronymic(String patronymic) {
    this.patronymic = patronymic;
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
}