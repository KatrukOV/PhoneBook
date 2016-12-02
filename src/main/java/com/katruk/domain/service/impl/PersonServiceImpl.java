package com.katruk.domain.service.impl;

import com.katruk.dao.PersonDao;
import com.katruk.domain.entity.Person;
import com.katruk.domain.service.PersonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class PersonServiceImpl implements PersonService {

  //  @Qualifier("PersonDaoMySql")
  private final PersonDao personDao;

  @Autowired()
  public PersonServiceImpl(PersonDao personDao) {
    this.personDao = personDao;
  }

  @Override
  public Person save(Person person) {
    return personDao.saveAndFlush(person);
  }

  @Override
  public Person getPersonById(Long personId) {
    return this.personDao.getPersonById(personId).orElseThrow(() -> new NoSuchElementException(
        String.format("No person present with id: %s", personId)));
  }
}
