package com.katruk.domain.dto;

import static org.junit.Assert.*;

import com.katruk.domain.DefaultEntity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

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