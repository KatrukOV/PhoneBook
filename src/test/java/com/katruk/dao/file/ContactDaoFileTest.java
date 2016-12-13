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
import com.katruk.domain.entity.Contact;

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
public class ContactDaoFileTest {

  @Rule
  public MockitoRule rule = MockitoJUnit.rule();
  @Mock
  private ObjectMapper objectMapper;
//  @Mock
//  private UserDaoFile userDaoFile;

  @Spy
  private Contact contact;
  private List<Contact> contacts;
  private ContactDaoFile contactDaoFile;

  @Before
  public void setUp() throws Exception {
    this.contactDaoFile = new ContactDaoFile();
    this.contact = new DefaultEntity().contact();
    this.contacts = new ArrayList<>();
    this.contacts.add(this.contact);
  }

  @Test
  public void getJsonFile() throws Exception {
    //when
    File result = this.contactDaoFile.getJsonFile();

    //then
    assertNotNull(result);
  }

  @Test
  public void getContactById_exist() throws Exception {
    //given
    Long contactId = 1L;

    //when
    when(this.objectMapper.readValue(any(File.class), any(TypeReference.class)))
        .thenReturn(this.contacts);
    Optional<Contact> result = this.contactDaoFile.getContactById(contactId);

    //then
    verify(this.objectMapper, times(1)).readValue(any(File.class), any(TypeReference.class));
    assertTrue(result.isPresent());
  }

  @Test
  public void getContactById_empty() throws Exception {
    //given
    Long contactId = 7L;

    //when
    when(this.objectMapper.readValue(any(File.class), any(TypeReference.class)))
        .thenReturn(this.contacts);
    Optional<Contact> result = this.contactDaoFile.getContactById(contactId);

    //then
    verify(this.objectMapper, times(1)).readValue(any(File.class), any(TypeReference.class));
    assertFalse(result.isPresent());
  }

  @Test
  public void saveAndFlush() throws Exception {
    //when
    when(this.objectMapper.readValue(any(File.class), any(TypeReference.class)))
        .thenReturn(this.contacts);
    doNothing().when(this.objectMapper).writeValue(any(File.class), any(TypeReference.class));
    Contact result = this.contactDaoFile.saveAndFlush(this.contact);

    //then
    verify(this.objectMapper, times(3)).readValue(any(File.class), any(TypeReference.class));
    verify(this.objectMapper, times(2)).writeValue(any(File.class), any(TypeReference.class));
    assertNotNull(result);
  }

  @Test
  public void delete() throws Exception {
    //given
    Long contactId = 1L;

    //when
    when(this.objectMapper.readValue(any(File.class), any(TypeReference.class)))
        .thenReturn(this.contacts);
    doNothing().when(this.objectMapper).writeValue(any(File.class), any(TypeReference.class));
    this.contactDaoFile.delete(contactId);

    //then
    verify(this.objectMapper, times(2)).readValue(any(File.class), any(TypeReference.class));
    verify(this.objectMapper, times(1)).writeValue(any(File.class), any(TypeReference.class));
  }
}