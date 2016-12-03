package com.katruk.domain.dto;

import static org.junit.Assert.*;
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