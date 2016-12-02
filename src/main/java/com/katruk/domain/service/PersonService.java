package com.katruk.domain.service;

import com.katruk.domain.entity.Person;

public interface PersonService {

  Person save(Person person);

  Person getPersonById(Long personId);

}
