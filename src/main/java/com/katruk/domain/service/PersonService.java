package com.katruk.domain.service;

import com.katruk.domain.dto.ContactDto;
import com.katruk.domain.entity.Person;

public interface PersonService {

  Person create(ContactDto contactDto);

  Person getPersonById(Long personId);

  Person updatePerson(Long personId, ContactDto contactDto);

}
