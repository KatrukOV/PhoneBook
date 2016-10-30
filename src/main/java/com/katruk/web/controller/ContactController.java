package com.katruk.web.controller;

import com.katruk.domain.dto.ContactDto;
import com.katruk.domain.service.ContactService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Set;

import javax.servlet.http.HttpSession;

@Controller
public class ContactController {

  @Autowired
  private ContactService contactService;

  @RequestMapping(value = "/contact")
  public String contact(HttpSession session, Model model) {
    String login = (String) session.getAttribute("login");
    Set<ContactDto> contactDtoSet = contactService.getByUserLogin(login);
    model.addAttribute("contacts", contactDtoSet);
    return "contact";
  }

  @RequestMapping(value = "/contact/add")
  public String add() {
    return "add";
  }

  @RequestMapping(value = "/contact/addNew", method = RequestMethod.POST)
  public String addNew(
      @RequestParam String lastName,
      @RequestParam String name,
      @RequestParam String patronymic,
      @RequestParam String mobilePhone,
      @RequestParam String homePhone,
      @RequestParam String email,
      @RequestParam String city,
      @RequestParam String street,
      @RequestParam String building,
      @RequestParam String apartment,
      HttpSession session,
      RedirectAttributes redirectAttributes) {

    System.out.println("addNew method -------------");
    String login = (String) session.getAttribute("login");
    ContactDto contactDto = new ContactDto.Builder()
        .login(login)
        .lastName(lastName)
        .name(name)
        .patronymic(patronymic)
        .mobilePhone(mobilePhone)
        .homePhone(homePhone)
        .email(email)
        .city(city)
        .street(street)
        .building(building)
        .apartment(Integer.parseInt(apartment))
        .build();
    ContactService.ContactStatus status = contactService.addContact(contactDto);

    System.out.println("status = " + status);

    switch (status) {
      case INCORRECT_LAST_NAME:
        redirectAttributes
            .addFlashAttribute("message", "Last Name must contain at least 4 symbols");
        break;
      case INCORRECT_NAME:
        redirectAttributes
            .addFlashAttribute("message", "First Name must contain at least 4 symbols");
        break;
      case INCORRECT_PATRONYMIC:
        redirectAttributes
            .addFlashAttribute("message", "Patronymic must contain at least 4 symbols");
        break;
      case INCORRECT_MOBILE_PHONE:
        redirectAttributes
            .addFlashAttribute("message",
                               "Mobile phone must have format: +380(XX)XXX-XX-XX or +380(XX)XXXXXXX");
        break;
      case INCORRECT_HOME_PHONE:
        redirectAttributes
            .addFlashAttribute("message",
                               "Home phone must have format: +380(XX)XXX-XX-XX or +380(XX)XXXXXXX");
        break;
      case INCORRECT_EMAIL:
        redirectAttributes
            .addFlashAttribute("message", "E-mail must have format: some@example.com");
        break;
      case SUCCESS:
        redirectAttributes.addFlashAttribute("message", "New contact added to your Contacts");
        break;
    }
    return "redirect:/contact/add";
  }

  @RequestMapping(value = "/contact/edit/{contactId}")
  public String edit(@PathVariable Integer contactId, Model model) {
    ContactDto contactDto = contactService.getById(contactId);
    model.addAttribute("contact", contactDto);
    return "edit";
  }
  @RequestMapping(value = "/contact/edit1/")
  public String edit1(@RequestParam Integer contactId, Model model) {
    ContactDto contactDto = contactService.getById(contactId);
    model.addAttribute("contact", contactDto);
    return "edit";
  }


  @RequestMapping(value = "/contact/editContact", method = RequestMethod.POST)
  public String editContact(
      @RequestParam Integer contactId,
      @RequestParam String lastName,
      @RequestParam String name,
      @RequestParam String patronymic,
      @RequestParam String mobilePhone,
      @RequestParam String homePhone,
      @RequestParam String email,
      @RequestParam String city,
      @RequestParam String street,
      @RequestParam String building,
      @RequestParam String apartment,
      HttpSession session,
      RedirectAttributes redirectAttributes) {

    String login = (String) session.getAttribute("login");
    ContactDto contactDto = new ContactDto.Builder()
        .id(contactId)
        .login(login)
        .lastName(lastName)
        .name(name)
        .patronymic(patronymic)
        .mobilePhone(mobilePhone)
        .homePhone(homePhone)
        .email(email)
        .city(city)
        .street(street)
        .building(building)
        .apartment(Integer.parseInt(apartment))
        .build();

    ContactService.ContactStatus status = contactService.editContact(contactDto);
    switch (status) {
      case INCORRECT_LAST_NAME:
        redirectAttributes
            .addFlashAttribute("message", "Last Name must contain at least 4 symbols");
        break;
      case INCORRECT_NAME:
        redirectAttributes
            .addFlashAttribute("message", "First Name must contain at least 4 symbols");
        break;
      case INCORRECT_PATRONYMIC:
        redirectAttributes
            .addFlashAttribute("message", "Patronymic must contain at least 4 symbols");
        break;
      case INCORRECT_MOBILE_PHONE:
        redirectAttributes.addFlashAttribute("message",
                                             "Mobile phone must have format: +380(XX)XXX-XX-XX or +380(XX)XXXXXXX");
        break;
      case INCORRECT_HOME_PHONE:
        redirectAttributes.addFlashAttribute("message",
                                             "Home phone must have format: +380(XX)XXX-XX-XX or +380(XX)XXXXXXX");
        break;
      case INCORRECT_EMAIL:
        redirectAttributes
            .addFlashAttribute("message", "E-mail must have format: some@example.com");
        break;
      case SUCCESS:
        redirectAttributes.addFlashAttribute("message", "Contact changed");
        break;
    }
    return "redirect:/contact/edit/" + contactId;
  }

  @RequestMapping(value = "/contact/delete", method = RequestMethod.POST)
  @ResponseBody
  public String delete(
      @RequestParam String contactId,
//      HttpSession session,
//      Model model,
      RedirectAttributes redirectAttributes
  ) {
    System.out.println(">>> to del contactId"+contactId);
    contactService.deleteContact(Integer.parseInt(contactId));
    redirectAttributes.addFlashAttribute("message", "Contact was deleted");
    return "redirect:/contact";
  }

  @RequestMapping(value = "/logOut")
  public String logOut(HttpSession session) {
    session.invalidate();
    return "redirect:/";
  }
}
