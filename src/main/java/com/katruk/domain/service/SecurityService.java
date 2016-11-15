package com.katruk.domain.service;

public interface SecurityService {

  String findLogged();

  void autoLogin(String login, String password);
}
