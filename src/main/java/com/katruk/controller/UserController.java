package com.katruk.controller;

import static java.util.Objects.nonNull;
import com.katruk.domain.dto.UserDto;
import com.katruk.domain.service.MessageService;
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
  private final MessageService messageService;

  @Autowired
  public UserController(UserService userService, UserValidator userValidator,
                        SecurityService securityService, MessageService messageService) {
    this.userService = userService;
    this.userValidator = userValidator;
    this.securityService = securityService;
    this.messageService = messageService;
  }

  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public String login(Model model, String error, String logout) {
    if (nonNull(error)) {
      model.addAttribute("error", "error.login.or.password");
    }
    if (nonNull(logout)) {
      model.addAttribute("logout", "logout.success");
    }
    return "login";
  }

  @RequestMapping(value = "/registration", method = RequestMethod.GET)
  public String registration(UserDto userDto) {
    return "registration";
  }

  @RequestMapping(value = "/registration", method = RequestMethod.POST)
  public String doRegistration(UserDto userDto, BindingResult bindingResult) {
    this.userValidator.validate(userDto, bindingResult);
    if (bindingResult.hasErrors()) {
      this.messageService.addError("Please fill the form correctly!");
      return "registration";
    }
    this.userService.createUser(userDto);
    this.securityService.autoLogin(userDto.getLogin(), userDto.getConfirmPassword());
    this.messageService.addInfo("Registration successful");
    return "redirect:/contacts";
  }
}