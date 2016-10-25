package com.katruk.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

  @RequestMapping(value = "/*")
  public String index(HttpSession session) {
    if (null != session.getAttribute("login")) {
      return "redirect:/user";
    } else {
      return "index";
    }
  }
}

