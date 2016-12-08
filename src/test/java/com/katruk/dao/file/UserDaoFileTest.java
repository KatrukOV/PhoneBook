package com.katruk.dao.file;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.katruk.domain.DefaultEntity;
import com.katruk.domain.entity.User;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(JUnit4.class)
public class UserDaoFileTest {

  @Rule
  public MockitoRule rule = MockitoJUnit.rule();
  @Mock
  private ObjectMapper objectMapper;
  @Spy
  private User user;
  private List<User> users;
  private UserDaoFile userDaoFile;

  @Before
  public void setUp() throws Exception {
    this.userDaoFile = new UserDaoFile(objectMapper);
    this.user = new DefaultEntity().user();
    this.users = new ArrayList<>();
    this.users.add(this.user);
  }

  @Test
  public void getJsonFile() throws Exception {
    //when
    File result = this.userDaoFile.getJsonFile();

    //then
    assertNotNull(result);
  }

  @Test
  public void getUserByLogin_exist() throws Exception {
    //given
    String login = "Login";

    //when
    when(this.objectMapper.readValue(any(File.class), any(TypeReference.class)))
        .thenReturn(this.users);
    Optional<User> result = this.userDaoFile.getUserByLogin(login);

    //then
    verify(this.objectMapper, times(1)).readValue(any(File.class), any(TypeReference.class));
    assertTrue(result.isPresent());
  }

  @Test
  public void getUserByLogin_empty() throws Exception {
    //given
    String login = "AnotherLogin";

    //when
    when(this.objectMapper.readValue(any(File.class), any(TypeReference.class)))
        .thenReturn(this.users);
    Optional<User> result = this.userDaoFile.getUserByLogin(login);

    //then
    verify(this.objectMapper, times(1)).readValue(any(File.class), any(TypeReference.class));
    assertFalse(result.isPresent());
  }

  @Test
  public void saveAndFlush() throws Exception {
    //when
    when(this.objectMapper.readValue(any(File.class), any(TypeReference.class))).thenReturn(this.users);
    doNothing().when(this.objectMapper).writeValue(any(File.class), any(TypeReference.class));
    User result = this.userDaoFile.saveAndFlush(this.user);

    //then
    verify(this.objectMapper, times(3)).readValue(any(File.class), any(TypeReference.class));
    verify(this.objectMapper, times(2)).writeValue(any(File.class), any(TypeReference.class));
    assertNotNull(result);
  }

}