package com.katruk.dao.file;


import com.katruk.dao.PersonDao;
import com.katruk.domain.entity.Person;

import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.Optional;


@Repository(value = "PersonDaoFile")
public class PersonDaoFile extends BaseDaoFile implements PersonDao {

  @Override
  protected File getJsonFile(String path) {
    return new File("src/main/resources/json/person.json");
  }

  @Override
  public Optional<Person> getPersonById(Long personId) {
    return super.<Person>findOne(personId);
  }

  @Override
  public Person saveAndFlush(Person person) {
    return super.save(person);
  }
}
