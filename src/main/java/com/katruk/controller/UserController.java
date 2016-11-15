package com.katruk.controller;

import static java.util.Objects.nonNull;

import com.katruk.domain.dto.UserDto;
import com.katruk.domain.service.SecurityService;
import com.katruk.domain.service.UserService;
import com.katruk.domain.validator.UserValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class UserController {

  private final UserService userService;
  private final UserValidator userValidator;
  private final SecurityService securityService;

  @Autowired
  public UserController(UserService userService, UserValidator userValidator,
                        SecurityService securityService) {
    this.userService = userService;
    this.userValidator = userValidator;
    this.securityService = securityService;
  }

  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public String login(Model model, String error, String logout) {

    System.out.println(">>> page login, error= " + error + " logout= " + logout);

    if (nonNull(error)) {
      model.addAttribute("error", "ERROR_VALUE");
    }
    if (nonNull(logout)) {
      model.addAttribute("logout", "LOGOUT_VALUE");
    }
    return "login";
  }

  @RequestMapping(value = "/registration", method = RequestMethod.GET)
  public String registration(UserDto userDto,
                             BindingResult bindingResult,
                             Model model) {

    System.out.println(">>>> registration get");

//    model.addAttribute("userDto", new UserDto());
//    model.addAttribute("userDto", userDto);
    return "registration";
  }

  @RequestMapping(value = "/registration", method = RequestMethod.POST)
  public String doRegistration(UserDto userDto,
                             BindingResult bindingResult,
                             Model model) {
    System.out.println(">>>> reg userForm= " + userDto + " bindingResult= " + bindingResult);

    this.userValidator.validate(userDto, bindingResult);
    System.out.println(
        ">>>> reg validate ok userForm = " + userDto + " bindingResult= " + bindingResult);
    if (bindingResult.hasErrors()) {
      return "registration";
    }
    System.out.println(">>>> reg no error  ");
    this.userService.createUser(userDto);
    System.out.println(">>>> reg createUser ok  ");
    this.securityService.autoLogin(userDto.getLogin(), userDto.getConfirmPassword());
    System.out.println(">>>> reg autoLogin ok  ");
    return "redirect:/contacts";
  }
}