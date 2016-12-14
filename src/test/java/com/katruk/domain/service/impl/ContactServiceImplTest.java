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
import com.katruk.domain.entity.Contact;
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
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@RunWith(JUnit4.class)
public class ContactServiceImplTest {

  @Rule
  public MockitoRule rule = MockitoJUnit.rule();
  @Mock
  private ContactDao contactDao;
  @Mock
  private SecurityService securityService;
  @Mock
  private UserService userService;
  @Spy
  private ContactDto contactDto;
  @Spy
  private Contact contact;
  private ContactServiceImpl contactService;

  @Before
  public void setUp() throws Exception {
    this.contactService = new ContactServiceImpl(contactDao, securityService, userService);
    this.contactDto = new DefaultEntity().contactDto();
    this.contact = new DefaultEntity().contact();
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
  public void getContactByLastName_lower_case() throws Exception {
    //given
    String inputLastName = "lastname";
    String outputLastName = "LastName";

    //when
    whenGetUserByLogin();
    Set<ContactDto> contactByLastName = this.contactService.getContactByLastName(inputLastName);
    String result = contactByLastName.iterator().next().getLastName();

    //then
    assertNotNull(contactByLastName);
    assertEquals(outputLastName, result);
  }

  @Test
  public void getContactByName_lower_case() throws Exception {
    //given
    String inputName = "name";
    String outputName = "Name";

    //when
    whenGetUserByLogin();
    Set<ContactDto> contactByName = this.contactService.getContactByName(inputName);
    String result = contactByName.iterator().next().getName();

    //then
    assertNotNull(contactByName);
    assertEquals(outputName, result);
  }

  @Test
  public void getContactByPhone() throws Exception {
    //given
    String phone = "+380(67)4445566";

    //when
    whenGetUserByLogin();
    Set<ContactDto> contactByPhone = this.contactService.getContactByPhone(phone);

    //then
    assertNotNull(contactByPhone);
  }

  @Test
  public void getContactByPhone_mobile() throws Exception {
    //given
    String mPhone = "+380(67)4445566";

    //when
    whenGetUserByLogin();
    Set<String> result = getPhones(mPhone);

    //then
    assertTrue(result.contains(mPhone));
  }

  @Test
  public void getContactByPhone_home() throws Exception {
    //given
    String hPhone = "+380(44)4445566";

    //when
    whenGetUserByLogin();
    Set<String> result = getPhones(hPhone);

    //then
    assertTrue(result.contains(hPhone));
  }

  @Test
  public void getContactByPhone_not_exist() throws Exception {
    //given
    String notExistPhone = "+380(77)7777777";

    //when
    whenGetUserByLogin();
    Set<ContactDto> contactByPhone = this.contactService.getContactByPhone(notExistPhone);

    //then
    assertTrue(contactByPhone.isEmpty());
  }

  @Test
  public void getContactByPhone_with_space() throws Exception {
    //given
    String phoneWithSpace = "         +380(67)4445566           ";
    String phoneWithoutSpace = "+380(67)4445566";

    //when
    whenGetUserByLogin();
    Set<String> result = getPhones(phoneWithSpace);

    //then
    assertTrue(result.contains(phoneWithoutSpace));
  }

  @Test
  public void getContactByPhone_with_hyphen() throws Exception {
    //given
    String phoneWithHyphen = "+380(67)444-55-66";
    String phoneWithoutHyphen = "+380(67)4445566";

    //when
    whenGetUserByLogin();
    Set<String> result = getPhones(phoneWithHyphen);

    //then
    assertTrue(result.contains(phoneWithoutHyphen));
  }

  @Test
  public void getContactById() throws Exception {
    //given
    Long contactId = 2L;

    //when
    when(this.contactDao.getContactById(contactId)).thenReturn(Optional.of(new Contact()));
    ContactDto contactDto = this.contactService.getContactById(contactId);

    //then
    assertNotNull(contactDto);
  }

  @Test(expected = NoSuchElementException.class)
  public void getContactById_then_return_optional_empty() throws Exception {
    //when
    when(this.contactDao.getContactById(anyLong())).thenReturn(Optional.empty());
    this.contactService.getContactById(anyLong());
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
    whenEditContactScript();
    Contact contact = this.contactService.editContact(this.contactDto);

    //then
    assertNotNull(contact);
  }

  @Test
  public void editContact_update_was_empty_fields() throws Exception {
    //given
    Contact contactWithEmptyFields = this.contact;
    contactWithEmptyFields.setHomePhone(null);
    contactWithEmptyFields.setEmail(null);

    //when
    whenEditContactScript();
    when(this.contactDao.getContactById(anyLong())).thenReturn(Optional.of(contactWithEmptyFields));
    Contact contact = this.contactService.editContact(this.contactDto);

    //then
    assertNotNull(contact);
  }

  @Test
  public void editContact_update_will_empty_fields() throws Exception {
    //given
    ContactDto contactWithEmptyFields = this.contactDto;
    contactWithEmptyFields.setHomePhone(null);
    contactWithEmptyFields.setEmail(null);

    //when
    whenEditContactScript();
    Contact contact = this.contactService.editContact(contactWithEmptyFields);

    //then
    assertNotNull(contact);
  }

  @Test
  public void editContact_update_will_new_mobile_phone() throws Exception {
    //given
    ContactDto contactWithNewMobilePhone = this.contactDto;
    contactWithNewMobilePhone.setMobilePhone("+380(50)777-77-77");

    //when
    whenEditContactScript();
    Contact contact = this.contactService.editContact(contactWithNewMobilePhone);

    //then
    assertNotNull(contact);
  }

  @Test(expected = NoSuchElementException.class)
  public void editContact_then_return_optional_empty() throws Exception {
    //when
    when(this.contactDao.getContactById(anyLong())).thenReturn(Optional.empty());
    this.contactService.editContact(contactDto);
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
    when(this.userService.getUserByLogin(login)).thenReturn(new DefaultEntity().user());
  }

  private Set<String> getPhones(String phone) {
    Set<ContactDto> contactByPhone = this.contactService.getContactByPhone(phone);
    Set<String> result = new HashSet<>();
    String mobilePhone = contactByPhone.iterator().next().getMobilePhone();
    String homePhone = contactByPhone.iterator().next().getHomePhone();
    result.add(mobilePhone);
    result.add(homePhone);
    return result;
  }

  private void whenEditContactScript() {
    when(this.contactDao.getContactById(anyLong())).thenReturn(Optional.of(this.contact));
    when(this.contactDao.saveAndFlush(any(Contact.class))).thenAnswer(returnsFirstArg());
  }
}