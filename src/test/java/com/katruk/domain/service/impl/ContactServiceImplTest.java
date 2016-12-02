package com.katruk.domain.service.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import com.katruk.dao.AddressDao;
import com.katruk.dao.ContactDao;
import com.katruk.dao.PersonDao;
import com.katruk.domain.dto.ContactDto;
import com.katruk.domain.entity.Contact;
import com.katruk.domain.entity.User;
import com.katruk.domain.service.AddressService;
import com.katruk.domain.service.PersonService;
import com.katruk.domain.service.SecurityService;
import com.katruk.domain.service.UserService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ContactServiceImplTest {

  @InjectMocks
  private ContactServiceImpl contactService;

  @Mock
  private SecurityService securityService;
  @Mock
  private ContactDao contactDao;
  @Mock
  private PersonService personService;
  @Mock
  private AddressService addressService;
  @Mock
  private UserService userService;

  private ContactDto contactDto;
  private Contact contact;
  private Long contactId;
  private User user;
  private String login;
  private String lastName;
  private String name;
  private String phone;


  @Before
  public void setUp() throws Exception {

    MockitoAnnotations.initMocks(this);

    contactService = new ContactServiceImpl(securityService, contactDao, personService,
                                            addressService, userService);
    login = "Login";
    lastName = "LastName";
    name = "Name";
    phone = "+380(67)4445566";

    contactDto = new ContactDto.Builder()
        .lastName("LastName")
        .name("Name")
        .patronymic("Patronymic")
        .mobilePhone("+380(67)4445566")
        .homePhone("+380(44)4445566")
        .email("email@same.com")
        .city("City")
        .street("Street")
        .building("15/7")
        .apartment(16)
        .build();

    user = new User();
    user.setLastName("LastName");
    user.setName("Name");
    user.setPatronymic("Patronymic");
    user.setLogin("Login");
    user.setPassword("Password");
    Set<Contact> contactSet = new HashSet<Contact>();
    contactSet.add(new Contact());
    contactSet.add(new Contact());
    user.setContacts(contactSet);

  }

  @Test
  public void getAllContact() throws Exception {

    when(securityService.getLogin()).thenReturn(login);
    when(userService.getUserByLogin(login)).thenReturn(user);

    Set<ContactDto> allContact = contactService.getAllContact();

    assertNotNull(allContact);
  }

//  @Test
//  public void getContactByLastName() throws Exception {
//
//    when(securityService.getLogin()).thenReturn(login);
//    when(userService.getUserByLogin(login)).thenReturn(user);
//
//    Set<ContactDto> contactByLastName = contactService.getContactByLastName(lastName);
//
//    assertNotNull(contactByLastName);
//
//    String result = contactByLastName.iterator().next().getLastName();
//    assertEquals(lastName, result);
//
//  }

//  @Test
//  public void getContactByName() throws Exception {
//    when(securityService.getLogin()).thenReturn(login);
//    when(userService.getUserByLogin(login)).thenReturn(user);
//
//    Set<ContactDto> contactByName = contactService.getContactByName(name);
//
//    assertNotNull(contactByName);
//
//    String result = contactByName.iterator().next().getName();
//    assertEquals(name, result);
//  }

//  @Test
//  public void getContactByPhone() throws Exception {
//    when(securityService.getLogin()).thenReturn(login);
//    when(userService.getUserByLogin(login)).thenReturn(user);
//
//    Set<ContactDto> contactByPhone = contactService.getContactByPhone(phone);
//
//    Set<String> result = new HashSet<>();
//    String mobilePhone = contactByPhone.iterator().next().getMobilePhone();
//    String homePhone = contactByPhone.iterator().next().getHomePhone();
//    result.add(mobilePhone);
//    result.add(homePhone);
//
//    assertNotNull(contactByPhone);
//    assertTrue(result.contains(phone));
//  }

  @Test
  public void getById() throws Exception {
    contactId = 2L;

    when(contactDao.getContactById(contactId)).thenReturn(Optional.of(new Contact()));

    ContactDto contactDto = contactService.getById(contactId);

    assertNotNull(contactDto);
  }

  @Test
  public void addContact() throws Exception {

  }

  @Test
  public void editContact() throws Exception {

  }

  @Test
  public void deleteContact() throws Exception {

  }

}