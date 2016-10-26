package com.katruk.web.controller;

import com.katruk.domain.entity.Address;
import com.katruk.domain.entity.Contact;
import com.katruk.domain.entity.Person;
import com.katruk.domain.entity.User;
import com.katruk.domain.service.ContactService;
import com.katruk.domain.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

  @Autowired
  private UserService userService;
  @Autowired
  private ContactService contactService;

  @RequestMapping(value = "/user")
  public String phoneBook(HttpSession session, Model model) {
    if (null != session.getAttribute("login")) {
      User user = userService.getUserByLogin(session.getAttribute("login").toString());
      model.addAttribute("login", user.getLogin());

//      model.addAttribute("contacts", user.getContacts());
      return "user";
    } else {
      return "redirect:/";
    }
  }

  @RequestMapping(value = "/user/add")
  public String add(HttpSession session, Model model) {
    if (null != session.getAttribute("login")) {
      User user = userService.getUserByLogin(session.getAttribute("login").toString());
      model.addAttribute("login", user.getLogin());
      return "adds";
    } else {
      return "redirect:/";
    }
  }


  @RequestMapping(value = "/user/addNew", method = RequestMethod.POST)
  public String addNew(@RequestParam String personLastName,
                       @RequestParam String personName,
                       @RequestParam String personPatronymic,
                       @RequestParam String mobilePhone,
                       @RequestParam String homePhone,
                       @RequestParam String email,
                       @RequestParam String addressCity,
                       @RequestParam String addressStreet,
                       @RequestParam String addressBuilding,
                       @RequestParam String addressApartment,
                       HttpSession session,
                       RedirectAttributes redirectAttributes) {
    User user = userService.getUserByLogin(session.getAttribute("login").toString());

    Person person = new Person();
    person.setLastName(personLastName);
    person.setName(personName);
    person.setPatronymic(personPatronymic);

    Address address = new Address();
    address.setCity(addressCity);
    address.setStreet(addressStreet);
    address.setBuilding(addressBuilding);
    address.setApartment(Integer.valueOf(addressApartment));

    Contact contact = new Contact();
    contact.setPerson(person);
    contact.setMobilePhone(mobilePhone);
    contact.setHomePhone(homePhone);
    contact.setEmail(email);
    contact.setAddress(address);

    ContactService.ContactStatus status = contactService.addContact(contact);
    switch (status) {
      case INCORRECT_LAST_NAME:
        redirectAttributes
            .addFlashAttribute("message", "Last Name must contain at least 4 symbols");
        return "redirect:/user/add";
      case INCORRECT_NAME:
        redirectAttributes
            .addFlashAttribute("message", "First Name must contain at least 4 symbols");
        return "redirect:/user/add";
      case INCORRECT_PATRONYMIC:
        redirectAttributes
            .addFlashAttribute("message", "Patronymic must contain at least 4 symbols");
        return "redirect:/user/add";
      case INCORRECT_MOBILE_PHONE:
        redirectAttributes
            .addFlashAttribute("message", "Mobile phone must have format: +XX(XXX)XXX-XX-XX");
        return "redirect:/user/add";
      case INCORRECT_HOME_PHONE:
        redirectAttributes
            .addFlashAttribute("message", "Home phone must have format: +XX(XXX)XXX-XX-XX");
        return "redirect:/user/add";
      case INCORRECT_EMAIL:
        redirectAttributes
            .addFlashAttribute("message", "E-mail must have format: some@example.com");
        return "redirect:/user/add";
      case SUCCESS:
        redirectAttributes.addFlashAttribute("message", "New Contact Added To Your PhoneBook");
        return "redirect:/user/add";
    }
    return "redirect:/user/add";
  }


  @RequestMapping(value = "/user/edit/{contactId}")
  public String edit(@PathVariable String contactId, HttpSession session, Model model) {
    if (session.getAttribute("login") != null) {
      model.addAttribute("login", session.getAttribute("login").toString());
      model.addAttribute("contactId", contactId);
      return "edits";
    }
    return "redirect:/";
  }


  @RequestMapping(value = "/user/editContact", method = RequestMethod.POST)
  public String editPhone(@RequestParam String contactId,
                          @RequestParam String personLastName,
                          @RequestParam String personName,
                          @RequestParam String personPatronymic,
                          @RequestParam String mobilePhone,
                          @RequestParam String homePhone,
                          @RequestParam String email,
                          @RequestParam String addressCity,
                          @RequestParam String addressStreet,
                          @RequestParam String addressBuilding,
                          @RequestParam String addressApartment,
                          HttpSession session,
                          RedirectAttributes redirectAttributes) {

    User user = userService.getUserByLogin(session.getAttribute("login").toString());

    Person person = new Person();
    person.setLastName(personLastName);
    person.setName(personName);
    person.setPatronymic(personPatronymic);

    Address address = new Address();
    address.setCity(addressCity);
    address.setStreet(addressStreet);
    address.setBuilding(addressBuilding);
    address.setApartment(Integer.valueOf(addressApartment));

    Contact contact = new Contact();
    contact.setId(Integer.valueOf(contactId));
    contact.setPerson(person);
    contact.setMobilePhone(mobilePhone);
    contact.setHomePhone(homePhone);
    contact.setEmail(email);
    contact.setAddress(address);


    ContactService.ContactStatus status = contactService.editContact(contact);
    switch (status) {
      case INCORRECT_LAST_NAME:
        redirectAttributes.addFlashAttribute("message", "Last Name must contain at least 4 symbols");
        return "redirect:/user/edit/" + contactId;
      case INCORRECT_NAME:
        redirectAttributes
            .addFlashAttribute("message", "First Name must contain at least 4 symbols");
        return "redirect:/user/edit/" + contactId;
      case INCORRECT_PATRONYMIC:
        redirectAttributes
            .addFlashAttribute("message", "Patronymic must contain at least 4 symbols");
        return "redirect:/user/edit/" + contactId;
      case INCORRECT_MOBILE_PHONE:
        redirectAttributes
            .addFlashAttribute("message", "Mobile phone must have format: +XX(XXX)XXX-XX-XX");
        return "redirect:/user/edit/" + contactId;
      case INCORRECT_HOME_PHONE:
        redirectAttributes
            .addFlashAttribute("message", "Home phone must have format: +XX(XXX)XXX-XX-XX");
        return "redirect:/user/edit/" + contactId;
      case INCORRECT_EMAIL:
        redirectAttributes.addFlashAttribute("message", "E-mail must have format: some@example.com");
        return "redirect:/user/edit/" + contactId;
      case SUCCESS:
        redirectAttributes.addFlashAttribute("message", "Contact Changed");
        return "redirect:/user/edit/" + contactId;
    }
    return "redirect:/user/edit/" + contactId;
  }


  @RequestMapping(value = "/user/delete", method = RequestMethod.POST)
  public
  @ResponseBody
  String delete(@RequestParam String contactId, HttpSession session, Model model) {
    contactService.deleteContact(Integer.valueOf(contactId));
    return "redirect:/user/delete";
  }

  @RequestMapping(value = "/logOut")
  public String logOut(HttpSession session) {
    session.invalidate();
    return "redirect:/";
  }
}
