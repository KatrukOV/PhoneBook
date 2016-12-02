package com.katruk.domain.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import com.katruk.dao.PersonDao;
import com.katruk.domain.DefaultEntity;
import com.katruk.domain.entity.Person;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Optional;

public class PersonServiceImplTest {

  @InjectMocks
  private PersonServiceImpl personService;

  @Mock
  private PersonDao personDao;

  private Person person;

  @Before
  public void setUp() throws Exception {

    MockitoAnnotations.initMocks(this);

    this.personService = new PersonServiceImpl(this.personDao);

    this.person = new DefaultEntity().person();

  }

  @Test
  public void save() throws Exception {

    Long personId = 3L;

    when(this.personDao.saveAndFlush(any(Person.class))).thenAnswer(new Answer<Person>() {
      @Override
      public Person answer(InvocationOnMock invocationOnMock) throws Throwable {
        Person person = invocationOnMock.getArgumentAt(0, Person.class);
        person.setId(personId);
        return person;
      }
    });

    Person person = this.personService.save(this.person);

    assertNotNull(person);
    assertEquals(person.getId(), personId);
  }

  @Test
  public void getPersonById() throws Exception {
    Long personId = 2L;

    when(this.personDao.getPersonById(personId)).thenReturn(Optional.of(new Person()));

    Person person = this.personService.getPersonById(personId);

    assertNotNull(person);
  }

}