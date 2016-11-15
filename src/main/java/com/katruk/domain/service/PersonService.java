package com.katruk.domain.service;

import com.katruk.domain.entity.Person;

import java.util.Optional;

public interface PersonService {

  Person save(Person person);

  Person getPersonById(Long personId);

}
