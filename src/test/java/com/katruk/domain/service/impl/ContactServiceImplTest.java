package com.katruk.domain.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.katruk.dao.ContactDao;
import com.katruk.domain.DefaultEntity;
import com.katruk.domain.dto.ContactDto;
import com.katruk.domain.entity.Address;
import com.katruk.domain.entity.Contact;
import com.katruk.domain.entity.Person;
import com.katruk.domain.entity.User;
import com.katruk.domain.service.AddressService;
import com.katruk.domain.service.PersonService;
import com.katruk.domain.service.SecurityService;
import com.katruk.domain.service.UserService;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RunWith(JUnit4.class)
public class ContactServiceImplTest {

  @Rule
  public MockitoRule rule = MockitoJUnit.rule();
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
  @Spy
  private ContactDto contactDto;
  @Spy
  private Contact contact;
  @Spy
  private User user;
  private ContactServiceImpl contactService;

  @Before
  public void setUp() throws Exception {
    this.contactService = new ContactServiceImpl(securityService, contactDao, personService,
                                                 addressService, userService);
    this.contactDto = new DefaultEntity().contactDto();
    this.contact = new DefaultEntity().contact();
    this.user = new DefaultEntity().user();
  }

  @Test
  public void getAllContact() throws Exception {
    //when
    whenGetUserByLogin();
    Set<ContactDto> allContact = this.contactService.getAllContact();

    //then
    assertNotNull(allContact);
  }

  @Test
  public void getContactByLastName() throws Exception {
    //given
    String lastName = "LastName";

    //when
    whenGetUserByLogin();
    Set<ContactDto> contactByLastName = this.contactService.getContactByLastName(lastName);
    String result = contactByLastName.iterator().next().getLastName();

    //then
    assertNotNull(contactByLastName);
    assertEquals(lastName, result);
  }

  @Test
  public void getContactByName() throws Exception {
    //given
    String name = "Name";

    //when
    whenGetUserByLogin();
    Set<ContactDto> contactByName = this.contactService.getContactByName(name);
    String result = contactByName.iterator().next().getName();

    //then
    assertNotNull(contactByName);
    assertEquals(name, result);
  }

  @Test
  public void getContactByPhone_mobile() throws Exception {
    //given
    String mPhone = "+380(67)4445566";
//    String hPhone = "+380(44)444-55-66";

    //when
    whenGetUserByLogin();
    Set<ContactDto> contactByPhone = this.contactService.getContactByPhone(mPhone);
    Set<String> result = new HashSet<>();
    String mobilePhone = contactByPhone.iterator().next().getMobilePhone();
    String homePhone = contactByPhone.iterator().next().getHomePhone();
    result.add(mobilePhone);
    result.add(homePhone);

    //then
    assertNotNull(contactByPhone);
    assertTrue(result.contains(mPhone));
  }

//  @Test
//  public void getContactByPhone_home() throws Exception {
//    //given
//    String phone = "+380(44)444-55-66";
//
//    //when
//    whenGetUserByLogin();
//    Set<ContactDto> contactByPhone = this.contactService.getContactByPhone(phone);
//    Set<String> result = new HashSet<>();
//    String mobilePhone = contactByPhone.iterator().next().getMobilePhone();
//    String homePhone = contactByPhone.iterator().next().getHomePhone();
//    result.add(mobilePhone);
//    result.add(homePhone);
//
//    //then
//    assertNotNull(contactByPhone);
//    assertTrue(result.contains(phone));
//  }

  @Test
  public void getById() throws Exception {
    //given
    Long contactId = 2L;

    //when
    when(this.contactDao.getContactById(contactId)).thenReturn(Optional.of(new Contact()));
    ContactDto contactDto = this.contactService.getById(contactId);

    //then
    assertNotNull(contactDto);
  }

  @Test
  public void addContact() throws Exception {
    //when
    when(this.contactDao.saveAndFlush(any(Contact.class))).thenAnswer(returnsFirstArg());
    Contact contact = this.contactService.addContact(this.contactDto);

    //then
    assertNotNull(contact);
  }

  @Test
  public void editContact() throws Exception {
    //when
    when(this.contactDao.getContactById(anyLong())).thenReturn(Optional.of(this.contact));
    when(this.contactDao.saveAndFlush(any(Contact.class))).thenAnswer(returnsFirstArg());
    when(this.personService.getPersonById(anyLong())).thenReturn(this.contact.getPerson());
    when(this.personService.save(any(Person.class))).thenAnswer(returnsFirstArg());
    when(this.addressService.getAddressById(anyLong())).thenReturn(this.contact.getAddress());
    when(this.addressService.save(any(Address.class))).thenAnswer(returnsFirstArg());
    when(this.contactDao.saveAndFlush(any(Contact.class))).thenAnswer(returnsFirstArg());
    Contact contact = this.contactService.editContact(this.contactDto);

    //then
    assertNotNull(contact);
  }

  @Test
  public void deleteContact() throws Exception {
    //when
    doNothing().when(contactDao).delete(anyLong());
    this.contactService.deleteContact(anyLong());

    //then
    verify(contactDao, times(1)).delete(anyLong());
  }

  private void whenGetUserByLogin() {
    String login = "Login";
    when(this.securityService.getLogin()).thenReturn(login);
    when(this.userService.getUserByLogin(login)).thenReturn(this.user);
  }
}