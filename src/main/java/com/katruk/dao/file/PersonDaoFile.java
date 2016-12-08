package com.katruk.dao.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.katruk.dao.PersonDao;
import com.katruk.domain.entity.Person;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Repository
@Profile(value = "file")
public class PersonDaoFile extends BaseDaoFile implements PersonDao {

  public PersonDaoFile(ObjectMapper objectMapper) {
    super(objectMapper);
  }

  @Override
  protected File getJsonFile() {
    File jsonFile = null;
    try {
      jsonFile = super.createJsonFile("src/main/resources/json/person.json");
    } catch (IOException e) {
      //todo log and throw
      e.printStackTrace();
    }
    return jsonFile;
  }

  @Override
  public Optional<Person> getPersonById(Long personId) {
    return super.findOne(personId);
  }

  @Override
  public Person saveAndFlush(Person person) {
    return super.save(person);
  }
}
