package com.katruk.domain.util;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.katruk.domain.DefaultEntity;
import com.katruk.domain.dto.ContactDto;
import com.katruk.domain.dto.UserDto;
import com.katruk.domain.entity.Contact;
import com.katruk.domain.entity.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConverterTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConverterTest.class);

  private Converter converter;
  private UserDto userDto;
  private Contact contact;

  @Before
  public void setUp() throws Exception {

    converter = new Converter();

    userDto = new DefaultEntity().userDto();

//    userDto = new UserDto();
//    userDto.setLastName("LastName");
//    userDto.setName("Name");
//    userDto.setPatronymic("Patronymic");
//    userDto.setLogin("Login");
//    userDto.setPassword("Password");
//    userDto.setConfirmPassword("Password");

    contact = new DefaultEntity().contact();

//    contact.
//        .lastName("LastName")
//        .name("Name")
//        .patronymic("Patronymic")
//        .mobilePhone("+380(67)4445566")
//        .homePhone("+380(44)4445566")
//        .email("email@same.com")
//        .city("City")
//        .street("Street")
//        .building("15/7")
//        .apartment(16)

  }

  @Test
  public void makeUserFromDto() throws Exception {

//    when(converter.makeUserFromDto(userDto)).thenReturn(new User());

    User user = converter.makeUserFromDto(userDto);

    assertNotNull(user);
//    verify(converter, times(1)).makeUserFromDto(userDto);
  }

  @Test
  public void makeDtoFromContact() throws Exception {

//    when(converter.makeDtoFromContact(contact)).thenReturn(mock(ContactDto.class));

    ContactDto contactDto = converter.makeDtoFromContact(contact);

    assertNotNull(contactDto);

//    verify(converter, times(1)).makeDtoFromContact(contact);
  }
}