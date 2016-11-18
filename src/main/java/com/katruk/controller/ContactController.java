package com.katruk.controller;

import com.katruk.domain.dto.ContactDto;
import com.katruk.domain.service.ContactService;
import com.katruk.domain.service.SecurityService;
import com.katruk.domain.validator.ContactValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.Set;

@Controller
public class ContactController {

  private final ContactService contactService;
//  private final SecurityService securityService;
  private final ContactValidator contactValidator;

  @Autowired
  public ContactController(ContactService contactService,
//                           SecurityService securityService,
                           ContactValidator contactValidator) {
    this.contactService = contactService;
//    this.securityService = securityService;
    this.contactValidator = contactValidator;
  }

  @RequestMapping(value = {"/", "contacts"}, method = RequestMethod.GET)
  public String contact(ContactDto contactDto, Model model) {

//    String login = this.securityService.getLogin();
    if (!model.containsAttribute("contacts")) {
      System.out.println(">>>> if Add contacts");
//    Set<ContactDto> contactDtoSet = this.contactService.getAllContact(login);
      Set<ContactDto> contactDtoSet = this.contactService.getAllContact();
      model.addAttribute("contacts", contactDtoSet);
//      String login = this.securityService.getLogin();
//      model.addAttribute("login", login);

    }

    return "contacts";
  }

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
    return "redirect:/contacts/add";
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
  public String edit1(@RequestParam Long contactId, ContactDto contactDto,
                      BindingResult bindingResult, Model model) {
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

      model.addAttribute("contact", contactDto);
      return "edit";
    }
    System.out.println(">>>> editContact no error  ");
    this.contactService.editContact(contactDto);
    System.out.println(">>>> editContact ok  ");
    return "redirect:/contacts";
  }

  @RequestMapping(value = "/contacts/delete", method = RequestMethod.POST)
  public String delete(@RequestParam(value = "contactId") Long contactId, ContactDto contactDto,
//                       RedirectAttributes redirectAttributes,
                       Model model
  ) {

    System.out.println(">>> to del contactId=" + contactId);
    this.contactService.deleteContact(contactId);
//    redirectAttributes.addFlashAttribute("message", "Contact was deleted");
    return "redirect:/contacts";
  }

  @RequestMapping(value = "contacts/allContact", method = RequestMethod.POST)
  public String showAllContact(ContactDto contactDto, Model model) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String login = authentication.getName();
    Set<ContactDto> contactDtoSet = this.contactService.getAllContact();
//        = getAllContactDto();
    model.addAttribute("contacts", contactDtoSet);
    return "contacts";
  }

  @RequestMapping(value = "contacts/findByLastName", method = RequestMethod.POST)
  public String showContactByLastName(@RequestParam(value = "lastName") String lastName,
                                      ContactDto contactDto, Model model) {
    System.out.println(">>> contacts/findByLastName LastName=" + lastName);

    Set<ContactDto> contactDtoSet = this.contactService.getContactByLastName(lastName);

    System.out.println(">>> contacts/findByLastName contacts=" + contactDtoSet);
    model.addAttribute("contacts", contactDtoSet);
    return "contacts";
  }

  @RequestMapping(value = "contacts/findByName", method = RequestMethod.POST)
  public String showContactByName(@RequestParam(value = "name") String name,
                                  ContactDto contactDto, Model model) {
    System.out.println(">>> contacts/findByName name=" + name);

    Set<ContactDto> contactDtoSet = this.contactService.getContactByName(name);

    System.out.println(">>> contacts/findByName contacts=" + contactDtoSet);
    model.addAttribute("contacts", contactDtoSet);
    return "contacts";
  }

  @RequestMapping(value = "contacts/findByPhone", method = RequestMethod.POST)
  public String showContactByMobilePhone(@RequestParam(value = "phone") String phone,
                                         ContactDto contactDto, Model model) {
    System.out.println(">>> contacts/findByPhone Phone=" + phone);

    Set<ContactDto> contactDtoSet = this.contactService.getContactByPhone(phone);

    System.out.println(">>> contacts/findByPhone contacts=" + contactDtoSet);
    model.addAttribute("contacts", contactDtoSet);
    return "contacts";
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


 */