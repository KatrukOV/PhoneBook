package com.katruk.web.controller;

import com.katruk.domain.entity.User;
import com.katruk.domain.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class SingController {

  @Autowired
  private UserService userService;

  @RequestMapping(value = "/signIn", method = RequestMethod.POST)
  public String singIn(@RequestParam String login, @RequestParam String password,
                       HttpSession session, RedirectAttributes redirectAttributes) {
    User user = userService.getUserByLogin(login);
    String encodedPassword = DigestUtils.sha1Hex(password);
    if (user == null || !user.getPassword().equals(encodedPassword)) {
      redirectAttributes.addFlashAttribute("message", "Wrong Login or Password!");
      return "redirect:/";
    } else {
      session.setMaxInactiveInterval(900);
      session.setAttribute("login", login);
      return "redirect:/user";
    }
  }

  @RequestMapping(value = "/registration")
  public String signUp() {
    return "/registration";
  }

  @RequestMapping(value = "/doRegistration", method = RequestMethod.POST)
  public String reg(@RequestParam String lastName,
                    @RequestParam String name,
                    @RequestParam String patronymic,
                    @RequestParam String login,
                    @RequestParam String password,
                    HttpSession session,
                    RedirectAttributes redirectAttributes) {

    System.out.println(">>>>name= "+ name);

    UserService.UserStatus
        status =
        userService.regUser(lastName, name, patronymic, login, password);
    switch (status) {
      case SUCCESS:
        session.setMaxInactiveInterval(900);
        session.setAttribute("login", login);
        return "redirect:/user";
      case EXISTS:
        redirectAttributes.addFlashAttribute("message", "This Login Already Exists!");
        return "redirect:/registration";
      case INCORRECT_LAST_NAME:
        redirectAttributes
            .addFlashAttribute("message", "Last Name must contain at least 4 symbols");
        return "redirect:/registration";
      case INCORRECT_NAME:
        redirectAttributes
            .addFlashAttribute("message", "Name must contain at least 4 symbols");
        return "redirect:/registration";
      case INCORRECT_PATRONYMIC:
        redirectAttributes
            .addFlashAttribute("message", "Patronymic must contain at least 4 symbols");
        return "redirect:/registration";
      case INCORRECT_LOGIN:
        redirectAttributes
            .addFlashAttribute("message", "Login must contain only English symbols (at least 3)");
        return "redirect:/registration";
      case INCORRECT_PASSWORD:
        redirectAttributes.addFlashAttribute("message",
                                             "Password must contain only numbers and English symbols (at least 5)");
        return "redirect:/registration";
    }
    return "redirect:/registration";
  }
}
