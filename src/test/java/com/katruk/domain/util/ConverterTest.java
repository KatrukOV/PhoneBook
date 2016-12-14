package com.katruk.domain.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import com.katruk.domain.DefaultEntity;
import com.katruk.domain.entity.dto.ContactDto;
import com.katruk.domain.entity.dto.UserDto;
import com.katruk.domain.entity.Contact;
import com.katruk.domain.entity.User;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConverterTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConverterTest.class);

  @Spy
  private UserDto userDto;
  @Spy
  private Contact contact;

  private Converter converter;

  @Before
  public void setUp() throws Exception {
    this.converter = new Converter();
    this.userDto = new DefaultEntity().userDto();
    this.contact = new DefaultEntity().contact();
  }

  @Test
  public void makeUserFromDto() throws Exception {
    //when
    User user = this.converter.makeUserFromDto(this.userDto);

    //then
    assertNotNull(user);
    assertEquals(this.userDto.getLastName(), user.getLastName());
    assertEquals(this.userDto.getName(), user.getName());
    assertEquals(this.userDto.getPatronymic(), user.getPatronymic());
    assertEquals(this.userDto.getLogin(), user.getLogin());
    assertNotEquals(this.userDto.getConfirmPassword(), user.getPassword());
    // TODO:  assertEquals Password
//    assertEquals(new BCryptPasswordEncoder().encode(userDto.getPassword()), user.getPassword());
//    assertTrue(new BCryptPasswordEncoder().matches(userDto.getPassword(), user.getPassword()));
//    assertFalse(new BCryptPasswordEncoder().matches(userDto.getPassword(), user.getPassword()));
  }

  @Test
  public void makeDtoFromContact() throws Exception {
    //when
    ContactDto contactDto = this.converter.makeDtoFromContact(this.contact);

    //then
    assertNotNull(contactDto);
    assertEquals(this.contact.getPerson().getLastName(), contactDto.getLastName());
    assertEquals(this.contact.getPerson().getName(), contactDto.getName());
    assertEquals(this.contact.getPerson().getPatronymic(), contactDto.getPatronymic());
    assertEquals(this.contact.getMobilePhone(), contactDto.getMobilePhone());
    assertEquals(this.contact.getHomePhone(), contactDto.getHomePhone());
    assertEquals(this.contact.getEmail(), contactDto.getEmail());
    assertEquals(this.contact.getAddress().getCity(), contactDto.getCity());
    assertEquals(this.contact.getAddress().getStreet(), contactDto.getStreet());
    assertEquals(this.contact.getAddress().getBuilding(), contactDto.getBuilding());
    assertEquals(this.contact.getAddress().getApartment(), contactDto.getApartment());
  }
}