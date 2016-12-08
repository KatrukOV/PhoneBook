package com.katruk.domain.util;

import com.katruk.domain.dto.ContactDto;
import com.katruk.domain.dto.UserDto;
import com.katruk.domain.entity.Contact;
import com.katruk.domain.entity.User;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Converter {

  public User makeUserFromDto(final UserDto userDto) {
    User newUser = new User();
    newUser.setLastName(userDto.getLastName());
    newUser.setName(userDto.getName());
    newUser.setPatronymic(userDto.getPatronymic());
    newUser.setLogin(userDto.getLogin());
    newUser.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
    return newUser;
  }

  public ContactDto makeDtoFromContact(final Contact contact) {
    return new ContactDto.Builder()
        .contactId(contact.getId())
        .lastName(contact.getPerson().getLastName())
        .name(contact.getPerson().getName())
        .patronymic(contact.getPerson().getPatronymic())
        .mobilePhone(contact.getMobilePhone())
        .homePhone(contact.getHomePhone())
        .email(contact.getEmail())
        .city(contact.getAddress().getCity())
        .street(contact.getAddress().getStreet())
        .building(contact.getAddress().getBuilding())
        .apartment(contact.getAddress().getApartment())
        .build();
  }
}
