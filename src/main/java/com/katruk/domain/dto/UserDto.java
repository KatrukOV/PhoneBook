package com.katruk.domain.dto;

public class UserDto {

  //  private Long id = 0L;
//  private final String LOGIN_PATTERN = "[A-Za-z]";
//  private final String PASSWORD_PATTERN = "[A-Za-z0-9]";

//  @NotEmpty(message = "REQUIRED")
//  @Length(min = 4, max = 30, message ="INCORRECT_SIZE_LAST_NAME")
  private String lastName = "";

//  @NotEmpty(message = "REQUIRED")
//  @Length(min = 4, max = 30, message ="INCORRECT_SIZE_NAME")
  private String name = "";
//
//  @NotEmpty(message = "REQUIRED")
//  @Length(min = 4, max = 30, message ="INCORRECT_SIZE_PATRONYMIC")
  private String patronymic = "";
//
//  @NotEmpty(message = "REQUIRED")
//  @Length(min = 4, max = 30, message ="INCORRECT_SIZE_LOGIN")
//  @Pattern(regexp = LOGIN_PATTERN, message = "LOGIN_NOT_ENGLISH")
  private String login = "";

//  @NotEmpty(message = "REQUIRED")
//  @Pattern(regexp = PASSWORD_PATTERN, message = "PASSWORD_NOT_ENGLISH")
  private String password = "";

//  @NotEmpty(message = "REQUIRED")
  private String confirmPassword = "";

  public UserDto() {
  }

//  public Long getContactId() {
//    return id;
//  }
//
//  public void setId(Long id) {
//    this.id = id;
//  }

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

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }

  @Override
  public String toString() {
    return "UserDto{" +
           "lastName='" + lastName + '\'' +
           ", name='" + name + '\'' +
           ", patronymic='" + patronymic + '\'' +
           ", login='" + login + '\'' +
           ", password='" + password + '\'' +
           ", confirmPassword='" + confirmPassword + '\'' +
           '}';
  }
}

/*

  public UserDto(Builder builder) {
    this.id = builder.id;
    this.lastName = builder.lastName;
    this.name = builder.name;
    this.patronymic = builder.patronymic;
    this.login = builder.login;
    this.password = builder.password;
    this.confirmPassword = builder.confirmPassword;
  }

  public static class Builder {

    private Integer id;
    private String lastName;
    private String name;
    private String patronymic;
    private String login;
    private String password;
    private String confirmPassword;

    public Builder() {
    }

    public Builder id(Integer id) {
      this.id = id;
      return this;
    }

    public Builder lastName(String lastName) {
      this.lastName = lastName;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder patronymic(String patronymic) {
      this.patronymic = patronymic;
      return this;
    }

    public Builder login(String login) {
      this.login = login;
      return this;
    }

    public Builder password(String password) {
      this.password = password;
      return this;
    }

    public Builder confirmPassword(String confirmPassword) {
      this.confirmPassword = confirmPassword;
      return this;
    }

    public UserDto build() {
      return new UserDto(this);
    }
  }
 */