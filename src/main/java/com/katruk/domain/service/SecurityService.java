package com.katruk.domain.service;

public interface SecurityService {

  void autoLogin(String login, String password);

  String getLogin();

}
