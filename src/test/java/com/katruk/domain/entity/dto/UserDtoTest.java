package com.katruk.domain.entity.dto;

import static org.junit.Assert.assertNotNull;

import com.katruk.domain.DefaultEntity;

import org.junit.Test;


public class UserDtoTest {

  @Test
  public void toString_test() throws Exception {
    //given
    UserDto userDto = new DefaultEntity().userDto();

    //when
    String result = userDto.toString();

    //then
    assertNotNull(result);
  }
}