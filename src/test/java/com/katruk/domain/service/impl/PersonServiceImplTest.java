package com.katruk.domain.service.impl;

import static org.junit.Assert.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.katruk.dao.PersonDao;
import com.katruk.domain.entity.Person;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Objects;
import java.util.Optional;

public class PersonServiceImplTest {

  @InjectMocks
  private PersonServiceImpl personService;

  @Mock
  private PersonDao personDao;

  private Person person;
  private Long personId;

  @Before
  public void setUp() throws Exception {
//    personDao = mock(PersonDao.class);
    MockitoAnnotations.initMocks(this);

    personService = new PersonServiceImpl(personDao);

    person = new Person();
    person.setLastName("LastName");
    person.setName("Name");
    person.setPatronymic("Patronymic");
  }

  @Test
  public void save() throws Exception {

    personId = 1L;
    when(personDao.saveAndFlush(any(Person.class))).thenAnswer(new Answer<Person>() {
      @Override
      public Person answer(InvocationOnMock invocationOnMock) throws Throwable {
        Person person = invocationOnMock.getArgumentAt(0, Person.class);
        person.setId(personId);
        return person;
      }
    });

    assertNull(person.getId());

    person = personService.save(person);

    assertNotNull(person.getId());
    assertEquals(person.getId(), personId);
  }

  @Test
  public void getPersonById() throws Exception {
    personId = 2L;

    when(personDao.getPersonById(personId)).thenReturn(Optional.of(new Person()));

    Person person = personService.getPersonById(personId);

    assertNotNull(person);
  }

}