package com.katruk.domain.entity.dto;

import static org.junit.Assert.assertNotNull;

import com.katruk.domain.DefaultEntity;

import org.junit.Test;

public class ContactDtoTest {

  @Test
  public void toString_test() throws Exception {
    //given
    ContactDto contactDto = new DefaultEntity().contactDto();

    //when
    String result = contactDto.toString();

    //then
    assertNotNull(result);
  }

  @Test
  public void default_constructor_test() throws Exception {
    //when
    ContactDto result = new ContactDto();

    //then
    assertNotNull(result);
  }
}