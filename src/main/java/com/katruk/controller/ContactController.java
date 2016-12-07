package com.katruk.controller;

import com.katruk.domain.dto.ContactDto;
import com.katruk.domain.service.ContactService;
import com.katruk.domain.service.MessageService;
import com.katruk.domain.validator.ContactValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Set;

@Controller
public class ContactController {

  private final ContactService contactService;
  private final ContactValidator contactValidator;
  private final MessageService messageService;

  @Autowired
  public ContactController(ContactService contactService, ContactValidator contactValidator,
                           MessageService messageService) {
    this.contactService = contactService;
    this.contactValidator = contactValidator;
    this.messageService = messageService;
  }

  @RequestMapping(value = {"/", "contacts"}, method = RequestMethod.GET)
  public String goContact(Model model) {
    if (!model.containsAttribute("contacts")) {
      Set<ContactDto> contactDtoSet = this.contactService.getAllContact();
      model.addAttribute("contacts", contactDtoSet);
    }
    return "contacts";
  }

  @RequestMapping(value = "/contacts/add", method = RequestMethod.GET)
  public String goAdd(ContactDto contactDto) {
    return "add";
  }

  @RequestMapping(value = "/contacts/addContact", method = RequestMethod.POST)
  public String doAddContact(ContactDto contactDto, BindingResult bindingResult) {
    this.contactValidator.validate(contactDto, bindingResult);
    if (bindingResult.hasErrors()) {
      this.messageService.addError("Please fill the contact correctly!");
      return "add";
    }
    this.contactService.addContact(contactDto);
    this.messageService.addInfo("Add contact successful");
    return "redirect:/contacts/add";
  }

  @RequestMapping(value = "/contacts/edit", method = RequestMethod.GET)
  public String goEdit(@RequestParam Long contactId, ContactDto contactDto,
                       BindingResult bindingResult, Model model) {
    contactDto = this.contactService.getContactById(contactId);
    model.addAttribute("contact", contactDto);
    return "edit";
  }

  @RequestMapping(value = "/contacts/editContact", method = RequestMethod.POST)
  public String doEditContact(ContactDto contactDto, BindingResult bindingResult, Model model) {
    this.contactValidator.validate(contactDto, bindingResult);

    if (bindingResult.hasErrors()) {
      model.addAttribute("contact", contactDto);
      this.messageService.addError("Please fill the contact correctly!");
      return "edit";
    }
    this.contactService.editContact(contactDto);
    this.messageService.addInfo("Contact was update");
    return "redirect:/contacts";
  }

  @RequestMapping(value = "/contacts/delete", method = RequestMethod.POST)
  public String doDelete(@RequestParam(value = "contactId") Long contactId) {
    this.contactService.deleteContact(contactId);
    this.messageService.addInfo("Contact was deleted");
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
                                      ContactDto contactDto,
                                      Model model) {
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
  public String showContactByPhone(@RequestParam(value = "phone") String phone,
                                   ContactDto contactDto, Model model) {
    Set<ContactDto> contactDtoSet = this.contactService.getContactByPhone(phone);
    model.addAttribute("contacts", contactDtoSet);
    return "contacts";
  }
}