package com.katruk.controller;

import com.katruk.domain.dto.ContactDto;
import com.katruk.domain.service.ContactService;
import com.katruk.domain.validator.ContactValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Set;

import javax.servlet.http.HttpSession;

@Controller
public class ContactController {

  private final ContactService contactService;
  private final ContactValidator contactValidator;

  @Autowired
  public ContactController(ContactService contactService, ContactValidator contactValidator) {
    this.contactService = contactService;
    this.contactValidator = contactValidator;
  }

  @RequestMapping(value = {"/", "contacts"}, method = RequestMethod.GET)
  public String contact(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String login = authentication.getName();
    System.out.println("!!! contact login= " + login);

    Set<ContactDto> contactDtoSet = this.contactService.getContactDtoByUserLogin(login);
    model.addAttribute("contacts", contactDtoSet);
    return "contacts";
  }

//  @RequestMapping(value = "/logOut", method = RequestMethod.POST)
//  public String logOut(HttpSession session) {
//    session.invalidate();
//    return "redirect:/login";
//  }

  @RequestMapping(value = "/contacts/add", method = RequestMethod.GET)
  public String add(ContactDto contactDto, Model model) {
//    System.out.println(">>> add contactDto= " + contactDto);
    return "add";
  }

  @RequestMapping(value = "/contacts/addContact", method = RequestMethod.POST)
  public String createContact(ContactDto contactDto, BindingResult bindingResult, Model model) {

    System.out.println(
        ">>>> addContact contactDto= " + contactDto + " bindingResult= " + bindingResult);

    this.contactValidator.validate(contactDto, bindingResult);
    System.out.println(
        ">>>> addContact validate ok contactDto = " + contactDto + " bindingResult= "
        + bindingResult);
    if (bindingResult.hasErrors()) {
      return "add";
    }
    System.out.println(">>>> addContact no error  ");
    this.contactService.addContact(contactDto);
    System.out.println(">>>> addContact ok  ");
    return "redirect:/add";
  }

//  @RequestMapping(value = "/contacts/edit/{contactId}")
//  public String edit(@PathVariable Long contactId, Model model) {
//    System.out.println(">>> edit{} contactId= " + contactId);
//
//    ContactDto contactDto = this.contactService.getById(contactId);
//    System.out.println(">>> edit{} contactDto= " + contactDto);
//    model.addAttribute("contact", contactDto);
//    return "edit";
//  }

  @RequestMapping(value = "/contacts/edit", method = RequestMethod.GET)
  public String edit1(@RequestParam Long contactId, ContactDto contactDto, Model model) {
    System.out.println(">>> contacts/edit contactId= " + contactId);
    contactDto = this.contactService.getById(contactId);
    System.out.println(">>> contacts/edit contactDto= " + contactDto);
    model.addAttribute("contact", contactDto);
    return "edit";
  }

  @RequestMapping(value = "/contacts/editContact", method = RequestMethod.POST)
  public String editContact(ContactDto contactDto, BindingResult bindingResult, Model model) {
    System.out.println(">>> /contacts/editContact contactDto= " + contactDto);

    this.contactValidator.validate(contactDto, bindingResult);
    System.out.println(
        ">>>> editContact validate ok contactDto = " + contactDto + " bindingResult= "
        + bindingResult);
    if (bindingResult.hasErrors()) {
      return "edit";
    }
    System.out.println(">>>> editContact no error  ");
    this.contactService.editContact(contactDto);
    System.out.println(">>>> editContact ok  ");
    return "redirect:/contacts";
  }

  @RequestMapping(value = "/contacts/delete", method = RequestMethod.POST)
  public String delete(@RequestParam Long contactId, ContactDto contactDto,
//                       RedirectAttributes redirectAttributes,
                       Model model
  ) {
    System.out.println(">>> to del contactId" + contactId);
    this.contactService.deleteContact(contactId);
//    redirectAttributes.addFlashAttribute("message", "Contact was deleted");
    return "redirect:/contacts";
  }
}

/*
 @RequestMapping(value = "/contact")
  public String contact(HttpSession session, Model model) {

//    String login = (String) session.getAttribute("login");

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String login = authentication.getName();

    Set<ContactDto> contactDtoSet = contactService.getContactDtoByUserLogin(login);
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
//        .login(login)
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
  public String edit(@PathVariable Long contactId, Model model) {
    ContactDto contactDto = contactService.getById(contactId);
    model.addAttribute("contact", contactDto);
    return "edit";
  }

  @RequestMapping(value = "/contact/edit1/")
  public String edit1(@RequestParam Long contactId, Model model) {
    ContactDto contactDto = this.contactService.getById(contactId);
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
//        .id(contactId)
//        .login(login)
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

  @RequestMapping(value = "/contact/deleteInBatch", method = RequestMethod.POST)
  public String deleteInBatch(
      @RequestParam String contactId,
//      HttpSession session,
//      Model model,
      RedirectAttributes redirectAttributes
  ) {
    System.out.println(">>> to del contactId" + contactId);
    contactService.deleteContact(Long.parseLong(contactId));
    redirectAttributes.addFlashAttribute("message", "Contact was deleted");
    return "redirect:/contact";
  }

  @RequestMapping(value = "/logOut")
  public String logOut(HttpSession session) {
    session.invalidate();
    return "redirect:/";
  }

 */