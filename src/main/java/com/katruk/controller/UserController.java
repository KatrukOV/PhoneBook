package com.katruk.controller;

import static java.util.Objects.nonNull;

import com.katruk.domain.dto.UserDto;
import com.katruk.domain.service.NotificationService;
import com.katruk.domain.service.SecurityService;
import com.katruk.domain.service.UserService;
import com.katruk.domain.validator.UserValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

  private final UserService userService;
  private final UserValidator userValidator;
  private final SecurityService securityService;
  private final NotificationService notifyService;

  @Autowired
  public UserController(UserService userService, UserValidator userValidator,
                        SecurityService securityService, NotificationService notifyService) {
    this.userService = userService;
    this.userValidator = userValidator;
    this.securityService = securityService;
    this.notifyService = notifyService;
  }

  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public String login(Model model, String error, String logout) {
    if (nonNull(error)) {
      model.addAttribute("error", "ERROR_VALUE");
    }
    if (nonNull(logout)) {
      model.addAttribute("logout", "LOGOUT_VALUE");
    }
    return "login";
  }

//  @RequestMapping(value = "/login", method = RequestMethod.POST)
//  public String login(@Valid LoginDto loginDto, BindingResult bindingResult,
//                      Model model, String error, String logout) {
//    if (nonNull(error)) {
//      model.addAttribute("error", "ERROR_VALUE");
//    }
//    if (nonNull(logout)) {
//      model.addAttribute("logout", "LOGOUT_VALUE");
//    }
//
//    if (bindingResult.hasErrors()) {
//      notifyService.addErrorMessage("Please fill the form correctly!");
//    }
//
//    if (this.securityService.autoLogin(loginDto.getLogin(), loginDto.getPassword())) {
//      notifyService.addErrorMessage("Invalid login or password!");
////      return "redirect:/";
//    }
//    notifyService.addInfoMessage("Login successful");
//    return "login";
//  }


  @RequestMapping(value = "/registration", method = RequestMethod.GET)
  public String registration(UserDto userDto, Model model) {
    return "registration";
  }

  @RequestMapping(value = "/registration", method = RequestMethod.POST)
  public String doRegistration(UserDto userDto, BindingResult bindingResult, Model model) {
    this.userValidator.validate(userDto, bindingResult);
    if (bindingResult.hasErrors()) {
      this.notifyService.addErrorMessage("Please fill the form correctly!");
      return "registration";
    }
    this.userService.createUser(userDto);
    this.securityService.autoLogin(userDto.getLogin(), userDto.getConfirmPassword());
    notifyService.addInfoMessage("Registration successful");
    return "redirect:/contacts";
  }
}