package com.katruk.dao;

import com.katruk.domain.entity.Person;

import java.util.Optional;

public interface PersonDao {

  Optional<Person> getPersonById(Long personId);

  Person saveAndFlush(Person person);

}

