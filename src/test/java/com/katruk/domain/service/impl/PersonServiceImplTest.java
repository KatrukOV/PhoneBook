package com.katruk.domain.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;
import com.katruk.dao.PersonDao;
import com.katruk.domain.DefaultEntity;
import com.katruk.domain.entity.Person;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;

import java.util.NoSuchElementException;
import java.util.Optional;

@RunWith(JUnit4.class)
public class PersonServiceImplTest {

  @Rule
  public MockitoRule rule = MockitoJUnit.rule();
  @Mock
  private PersonDao personDao;
  @Spy
  private Person person;
  private PersonServiceImpl personService;

  @Before
  public void setUp() throws Exception {
    this.personService = new PersonServiceImpl(this.personDao);
    this.person = new DefaultEntity().person();
  }

  @Test
  public void save() throws Exception {
    //given
    Long personId = 3L;

    //when
    when(this.personDao.saveAndFlush(any(Person.class))).thenAnswer(new Answer<Person>() {
      @Override
      public Person answer(InvocationOnMock invocationOnMock) throws Throwable {
        Person person = invocationOnMock.getArgumentAt(0, Person.class);
        person.setId(personId);
        return person;
      }
    });
    Person person = this.personService.save(this.person);

    //then
    assertNotNull(person);
    assertEquals(person.getId(), personId);
  }

  @Test
  public void getPersonById() throws Exception {
    //when
    when(this.personDao.getPersonById(anyLong())).thenReturn(Optional.of(new Person()));
    Person person = this.personService.getPersonById(anyLong());

    System.out.println("person=" + person);
    //then
    assertNotNull(person);
  }

  @Test(expected = NoSuchElementException.class)
  public void getPersonById_throw_exception() throws Exception {

    //when
    when(this.personDao.getPersonById(anyLong())).thenThrow(new NoSuchElementException());
    this.personService.getPersonById(anyLong());
  }

  @Test
  public void getPersonById_throw_exception_with_message() throws Exception {
    //given
    String error = "error";
    String result = "";

    //when
    when(this.personDao.getPersonById(anyLong())).thenThrow(new NoSuchElementException(error));
    try {
      this.personService.getPersonById(anyLong());
    } catch (NoSuchElementException e) {
      result = e.getMessage();
    }

    //then
    assertEquals(error, result);
  }

  @Test(expected = NoSuchElementException.class)
  public void getPersonById_then_return_optional_empty() throws Exception {
    //when
    when(this.personDao.getPersonById(anyLong())).thenReturn(Optional.empty());
    this.personService.getPersonById(anyLong());
  }
}