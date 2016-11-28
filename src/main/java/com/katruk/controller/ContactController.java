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
  private final ContactValidator contactValidator;

  @Autowired
  public ContactController(ContactService contactService, ContactValidator contactValidator) {
    this.contactService = contactService;
    this.contactValidator = contactValidator;
  }

  @RequestMapping(value = {"/", "contacts"}, method = RequestMethod.GET)
  public String contact(ContactDto contactDto, Model model) {
    System.out.println(">>> contact");
    if (!model.containsAttribute("contacts")) {
      System.out.println(">>>> if Add contacts");
      Set<ContactDto> contactDtoSet = this.contactService.getAllContact();
      model.addAttribute("contacts", contactDtoSet);
    }
    return "contacts";
  }

  @RequestMapping(value = "/contacts/add", method = RequestMethod.GET)
  public String add(ContactDto contactDto, Model model) {
    return "add";
  }

  @RequestMapping(value = "/contacts/addContact", method = RequestMethod.POST)
  public String addContact(ContactDto contactDto, BindingResult bindingResult, Model model) {
    this.contactValidator.validate(contactDto, bindingResult);
    if (bindingResult.hasErrors()) {
      return "add";
    }
    this.contactService.addContact(contactDto);
    return "redirect:/contacts/add";
  }

  @RequestMapping(value = "/contacts/edit", method = RequestMethod.GET)
  public String edit(@RequestParam Long contactId, ContactDto contactDto,
                     BindingResult bindingResult, Model model) {
    contactDto = this.contactService.getById(contactId);
    model.addAttribute("contact", contactDto);
    return "edit";
  }

  @RequestMapping(value = "/contacts/editContact", method = RequestMethod.POST)
  public String editContact(ContactDto contactDto, BindingResult bindingResult, Model model) {
    this.contactValidator.validate(contactDto, bindingResult);

    if (bindingResult.hasErrors()) {
      model.addAttribute("contact", contactDto);
      return "edit";
    }
    this.contactService.editContact(contactDto);

    return "redirect:/contacts";
  }

  @RequestMapping(value = "/contacts/delete", method = RequestMethod.POST)
  public String delete(@RequestParam(value = "contactId") Long contactId,
                       ContactDto contactDto, Model model) {
    this.contactService.deleteContact(contactId);
//    redirectAttributes.addFlashAttribute("message", "Contact was deleted");
    return "redirect:/contacts";
  }

  @RequestMapping(value = "/contacts/allContact", method = RequestMethod.POST)
  public String showAllContact(ContactDto contactDto, Model model) {

    Set<ContactDto> contactDtoSet = this.contactService.getAllContact();
    model.addAttribute("contacts", contactDtoSet);
    return "contacts";
  }

  @RequestMapping(value = "/contacts/findByLastName", method = RequestMethod.POST)
  public String showContactByLastName(@RequestParam(value = "lastName") String lastName,
                                      ContactDto contactDto, Model model) {
    Set<ContactDto> contactDtoSet = this.contactService.getContactByLastName(lastName);
    model.addAttribute("contacts", contactDtoSet);
    return "contacts";
  }

  @RequestMapping(value = "/contacts/findByName", method = RequestMethod.POST)
  public String showContactByName(@RequestParam(value = "name") String name,
                                  ContactDto contactDto, Model model) {
    Set<ContactDto> contactDtoSet = this.contactService.getContactByName(name);
    model.addAttribute("contacts", contactDtoSet);
    return "contacts";
  }

  @RequestMapping(value = "/contacts/findByPhone", method = RequestMethod.POST)
  public String showContactByMobilePhone(@RequestParam(value = "phone") String phone,
                                         ContactDto contactDto, Model model) {
    Set<ContactDto> contactDtoSet = this.contactService.getContactByPhone(phone);
    model.addAttribute("contacts", contactDtoSet);
    return "contacts";
  }
}