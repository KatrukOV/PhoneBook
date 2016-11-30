package com.katruk.domain.util;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.katruk.domain.dto.ContactDto;
import com.katruk.domain.dto.UserDto;
import com.katruk.domain.entity.Contact;
import com.katruk.domain.entity.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConverterTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConverterTest.class);

  @InjectMocks
  private Converter converter;

  @Mock
  private UserDto userDto;

  @Mock
  private Contact contact;


  @Before
  public void setUp() throws Exception {
//    User user = new User();
//    UserDto userDto = new UserDto();
  }

  @After
  public void tearDown() throws Exception {

  }

  @Test
  public void makeUserFromDto() throws Exception {

    when(converter.makeUserFromDto(userDto)).thenReturn(mock(User.class));

    verify(converter, times(1)).makeUserFromDto(userDto);
  }

  @Test
  public void makeDtoFromContact() throws Exception {

    when(converter.makeDtoFromContact(contact)).thenReturn(mock(ContactDto.class));

    verify(converter, times(1)).makeDtoFromContact(contact);
  }

}