package com.katruk.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.katruk.domain.DefaultEntity;
import com.katruk.domain.entity.Contact;
import com.katruk.domain.entity.dto.ContactDto;
import com.katruk.domain.service.ContactService;
import com.katruk.domain.service.MessageService;
import com.katruk.domain.validator.ContactValidator;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DirectFieldBindingResult;

import java.util.HashSet;

@RunWith(JUnit4.class)
public class ContactControllerTest {

  @Rule
  public MockitoRule rule = MockitoJUnit.rule();
  @Mock
  private ContactService contactService;
  @Mock
  private ContactValidator contactValidator;
  @Mock
  private MessageService messageService;

  @Spy
  private ContactDto contactDto;
  private Model model;
  private ContactController contactController;

  @Before
  public void setUp() throws Exception {
    this.contactController = new ContactController(contactService, contactValidator, messageService);
    this.contactDto = new DefaultEntity().contactDto();
    this.model = new ExtendedModelMap();
  }

  @Test
  public void contact_model_has_attribute() throws Exception {
    //given
    String attribute = "contacts";
    model.addAttribute(attribute, new HashSet<ContactDto>());

    //when
    String result = this.contactController.goContact(model);

    //then
    verify(this.contactService, times(0)).getAllContact();
    assertEquals("contacts", result);
  }

  @Test
  public void contact_model_is_empty() throws Exception {
    //given
    String attribute = "contacts";

    //when
    when(this.contactService.getAllContact()).thenReturn(new HashSet<>());
    String result = this.contactController.goContact(model);

    //then
    verify(this.contactService, times(1)).getAllContact();
    assertTrue(model.containsAttribute(attribute));
    assertEquals("contacts", result);
  }

  @Test
  public void goAdd() throws Exception {
    //when
    String result = this.contactController.goAdd(contactDto);

    //then
    assertEquals("add", result);
  }

  @Test
  public void doAddContact_success() throws Exception {
    //given
    Contact contact = new DefaultEntity().contact();
    BindingResult error = new DirectFieldBindingResult(contactDto, "ContactJson");

    //when
    whenDefaultScript(contact);
    String result = this.contactController.doAddContact(contactDto, error);

    //then
    assertEquals("redirect:/contacts/add", result);
  }

  @Test
  public void doAddContact_fail() throws Exception {
    //given
    Contact contact = new DefaultEntity().contact();
    BindingResult error = new DirectFieldBindingResult(contactDto, "ContactJson");
    error.reject("error");

    //when
    whenDefaultScript(contact);
    String result = this.contactController.doAddContact(contactDto, error);

    //then
    verify(this.contactValidator, times(1))
        .validate(any(ContactDto.class), any(BindingResult.class));
    verify(this.messageService, times(0)).addInfo(anyString());
    verify(this.messageService, times(1)).addError(anyString());
    verify(this.contactService, times(0)).addContact(any(ContactDto.class));
    assertEquals("add", result);
  }

  @Test
  public void goEdit() throws Exception {
    //given
    BindingResult error = new DirectFieldBindingResult(contactDto, "ContactJson");

    //when
    when(this.contactService.getContactById(anyLong())).thenReturn(contactDto);
    String result = this.contactController.goEdit(anyLong(), contactDto, error, model);

    //then
    assertTrue(model.containsAttribute("contact"));
    assertEquals("edit", result);
  }

  @Test
  public void doEditContact_success() throws Exception {
    //given
    Contact contact = new DefaultEntity().contact();
    BindingResult error = new DirectFieldBindingResult(contactDto, "ContactJson");

    //when
    whenDefaultScript(contact);
    String result = this.contactController.doEditContact(contactDto, error, model);

    //then
    assertEquals("redirect:/contacts", result);
  }

  @Test
  public void doEditContact_fail() throws Exception {
    //given
    Contact contact = new DefaultEntity().contact();
    BindingResult error = new DirectFieldBindingResult(contactDto, "ContactJson");
    error.reject("error");

    //when
    whenDefaultScript(contact);
    String result = this.contactController.doEditContact(contactDto, error, model);

    //then
    verify(this.contactValidator, times(1))
        .validate(any(ContactDto.class), any(BindingResult.class));
    verify(this.messageService, times(0)).addInfo(anyString());
    verify(this.messageService, times(1)).addError(anyString());
    verify(this.contactService, times(0)).addContact(any(ContactDto.class));
    assertEquals("edit", result);
  }

  @Test
  public void doDelete() throws Exception {
    //when
    when(this.contactService.getContactById(anyLong())).thenReturn(contactDto);
    String result = this.contactController.doDelete(anyLong());

    //then
    verify(this.messageService, times(1)).addInfo(anyString());
    assertEquals("redirect:/contacts", result);
  }

  @Test
  public void showAllContact() throws Exception {
    //when
    when(this.contactService.getAllContact()).thenReturn(new HashSet<>());
    String result = this.contactController.showAllContact(contactDto, model);

    //then
    assertEquals("contacts", result);
    assertTrue(model.containsAttribute("contacts"));
  }

  @Test
  public void showContactByLastName() throws Exception {
    //given
    String lastName = "LastName";

    //when
    when(this.contactService.getContactByLastName(anyString())).thenReturn(new HashSet<>());
    String result = this.contactController.showContactByLastName(lastName, contactDto, model);

    //then
    assertEquals("contacts", result);
    assertTrue(model.containsAttribute("contacts"));
  }

  @Test
  public void showContactByName() throws Exception {
    //given
    String name = "Name";

    //when
    when(this.contactService.getContactByName(anyString())).thenReturn(new HashSet<>());
    String result = this.contactController.showContactByName(name, contactDto, model);

    //then
    assertEquals("contacts", result);
    assertTrue(model.containsAttribute("contacts"));
  }

  @Test
  public void showContactByPhone() throws Exception {
    //given
    String phone = "+380(67)4445566";

    //when
    when(this.contactService.getContactByPhone(anyString())).thenReturn(new HashSet<>());
    String result = this.contactController.showContactByPhone(phone, contactDto, model);

    //then
    assertEquals("contacts", result);
    assertTrue(model.containsAttribute("contacts"));
  }

  private void whenDefaultScript(Contact contact) {
    doNothing().when(this.contactValidator)
        .validate(any(ContactDto.class), any(BindingResult.class));
    doNothing().when(this.messageService).addError(anyString());
    doNothing().when(this.messageService).addInfo(anyString());
    when(this.contactService.addContact(any(ContactDto.class))).thenReturn(contact);
  }
}