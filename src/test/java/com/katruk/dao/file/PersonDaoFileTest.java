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
import com.katruk.domain.entity.Person;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonDaoFileTest {

  @Rule
  public MockitoRule rule = MockitoJUnit.rule();
  @Mock
  private ObjectMapper objectMapper;
  @Spy
  private Person person;
  private List<Person> persons;
  private PersonDaoFile personDaoFile;

  @Before
  public void setUp() throws Exception {
    this.personDaoFile = new PersonDaoFile(objectMapper);
    this.person = new DefaultEntity().person();
    this.persons = new ArrayList<>();
    this.persons.add(this.person);
  }

  @Test
  public void getJsonFile() throws Exception {
    //when
    File result = this.personDaoFile.getJsonFile();

    //then
    assertNotNull(result);
  }

  @Test
  public void getPersonById_exist() throws Exception {
    //given
    Long personId = 1L;

    //when
    when(this.objectMapper.readValue(any(File.class), any(TypeReference.class)))
        .thenReturn(this.persons);
    Optional<Person> result = this.personDaoFile.getPersonById(personId);

    //then
    verify(this.objectMapper, times(1)).readValue(any(File.class), any(TypeReference.class));
    assertTrue(result.isPresent());
  }

  @Test
  public void getPersonById_empty() throws Exception {
    //given
    Long personId = 7L;

    //when
    when(this.objectMapper.readValue(any(File.class), any(TypeReference.class)))
        .thenReturn(this.persons);
    Optional<Person> result = this.personDaoFile.getPersonById(personId);

    //then
    verify(this.objectMapper, times(1)).readValue(any(File.class), any(TypeReference.class));
    assertFalse(result.isPresent());
  }

  @Test
  public void saveAndFlush() throws Exception {
    //when
    when(this.objectMapper.readValue(any(File.class), any(TypeReference.class))).thenReturn(this.persons);
    doNothing().when(this.objectMapper).writeValue(any(File.class), any(TypeReference.class));
    Person result = this.personDaoFile.saveAndFlush(this.person);

    //then
    verify(this.objectMapper, times(3)).readValue(any(File.class), any(TypeReference.class));
    verify(this.objectMapper, times(2)).writeValue(any(File.class), any(TypeReference.class));
    assertNotNull(result);
  }
}