package com.katruk.domain.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import com.katruk.dao.ContactDao;
import com.katruk.domain.DefaultEntity;
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
  private User user;

  @Before
  public void setUp() throws Exception {

    MockitoAnnotations.initMocks(this);

    this.contactService = new ContactServiceImpl(securityService, contactDao, personService,
                                                 addressService, userService);
    this.contactDto = new DefaultEntity().contactDto();
    this.user = new DefaultEntity().user();
  }

  @Test
  public void getAllContact() throws Exception {

    whenGetUserWithMock();

    Set<ContactDto> allContact = this.contactService.getAllContact();

    assertNotNull(allContact);
  }

  @Test
  public void getContactByLastName() throws Exception {
    String lastName = "LastName";
    whenGetUserWithMock();

    Set<ContactDto> contactByLastName = this.contactService.getContactByLastName(lastName);
    String result = contactByLastName.iterator().next().getLastName();

    assertNotNull(contactByLastName);
    assertEquals(lastName, result);
  }

  @Test
  public void getContactByName() throws Exception {
    String name = "Name";
    whenGetUserWithMock();

    Set<ContactDto> contactByName = this.contactService.getContactByName(name);
    String result = contactByName.iterator().next().getName();

    assertNotNull(contactByName);
    assertEquals(name, result);
  }

  @Test
  public void getContactByPhone() throws Exception {
    String phone = "+380(67)4445566";
    whenGetUserWithMock();

    Set<ContactDto> contactByPhone = this.contactService.getContactByPhone(phone);

    Set<String> result = new HashSet<>();
    String mobilePhone = contactByPhone.iterator().next().getMobilePhone();
    String homePhone = contactByPhone.iterator().next().getHomePhone();
    result.add(mobilePhone);
    result.add(homePhone);

    assertNotNull(contactByPhone);
    assertTrue(result.contains(phone));
  }

  @Test
  public void getById() throws Exception {
    Long contactId = 2L;
    when(this.contactDao.getContactById(contactId)).thenReturn(Optional.of(new Contact()));

    ContactDto contactDto = this.contactService.getById(contactId);

    assertNotNull(contactDto);
  }

  @Test
  public void addContact() throws Exception {
    when(this.contactDao.saveAndFlush(any(Contact.class))).thenAnswer(returnsFirstArg());

    Contact contact = this.contactService.addContact(this.contactDto);

    assertNotNull(contact);
  }

  @Test
  public void editContact() throws Exception {
    when(this.contactDao.getContactById(this.contactDto.getContactId()))
        .thenReturn(Optional.ofNullable(this.contact));
    when(this.contactDao.saveAndFlush(any(Contact.class))).thenAnswer(returnsFirstArg());

    Contact contact = this.contactService.addContact(this.contactDto);

    assertNotNull(contact);
  }

  @Test
  public void deleteContact() throws Exception {

  }

  private void whenGetUserWithMock() {
    String login = "Login";
    when(this.securityService.getLogin()).thenReturn(login);
    when(this.userService.getUserByLogin(login)).thenReturn(this.user);
  }
}