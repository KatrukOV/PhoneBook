package com.katruk;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.fasterxml.jackson.core.type.TypeReference;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PhoneBookApplicationTests {

//  @Mock
//  SpringApplication springApplication;

  @Test
  public void contextLoads() {

    /*
    //given
    PhoneBookApplication phoneBookApplication = mock(PhoneBookApplication.class);

    //when
    doNothing().when(springApplication).run(any(Class.class), (String[]) any(Object.class));
    phoneBookApplication.main(Object.class, null);

    //then
    verify(springApplication).run(any(Class.class), (String[]) any(Object.class));
  */

  }
}
