package com.katruk.domain.service.impl;

import com.katruk.dao.PersonDao;
import com.katruk.domain.dto.ContactDto;
import com.katruk.domain.entity.Person;
import com.katruk.domain.service.PersonService;

import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import javax.annotation.Resource;

@Service
public class PersonServiceImpl implements PersonService {

  @Resource(name = "${PersonDao.class}")
  private final PersonDao personDao;

  public PersonServiceImpl(PersonDao personDao) {
    this.personDao = personDao;
  }

  @Override
  public Person create(ContactDto contactDto) {
    Person person = new Person();
    person.setLastName(contactDto.getLastName().trim());
    person.setName(contactDto.getName().trim());
    person.setPatronymic(contactDto.getPatronymic().trim());
    return personDao.saveAndFlush(person);
  }

  @Override
  public Person getPersonById(Long personId) {
    return this.personDao.getPersonById(personId).orElseThrow(() -> new NoSuchElementException(
        String.format("No person present with id: %s", personId)));
  }

  @Override
  public Person updatePerson(Long personId, ContactDto contactDto) {
    Person person = this.personDao.getPersonById(personId)
        .orElseThrow(() -> new NoSuchElementException(
            String.format("No person present with id: %s", personId)));

    if (!(contactDto.getLastName().trim()).equals(person.getLastName())) {
      person.setLastName(contactDto.getLastName().trim());
    }
    if (!(contactDto.getName().trim()).equals(person.getName())) {
      person.setName(contactDto.getName().trim());
    }
    if (!(contactDto.getPatronymic().trim()).equals(person.getPatronymic())) {
      person.setPatronymic(contactDto.getPatronymic().trim());
    }
    return this.personDao.saveAndFlush(person);
  }

}
