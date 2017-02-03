package com.katruk.dao.file;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.katruk.dao.ContactDao;
import com.katruk.domain.entity.Contact;
import com.katruk.domain.entity.User;
import com.katruk.domain.entity.json.ContactJson;
import com.katruk.domain.util.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
@Profile(value = "file")
public class ContactDaoFile implements ContactDao {

  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private UserDaoFile userDaoFile;

  private File jsonFile;

  protected File getJsonFile() {
    try {
      this.jsonFile = new File("src/main/resources/json/contact.json");
      if (!jsonFile.exists()) {
        this.jsonFile.createNewFile();
      }
    } catch (IOException e) {
      //todo log and throw
      e.printStackTrace();
    }
    return jsonFile;
  }

  @Override
  public Optional<Contact> getContactById(Long contactId) {

    List<ContactJson> lists = getAll();
    for (ContactJson contactJson : lists) {
      if (contactJson.getContactId().equals(contactId)) {
        User user = this.userDaoFile.getUserById(contactJson.getUserId())
            .orElseThrow(() -> new NoSuchElementException("Element not found"));
        //todo @Autowired  Converter
        Contact contact = new Converter().makeContactFromJson(contactJson, user);
        return Optional.of(contact);
      }
    }
    return Optional.empty();
  }

  public Set<Contact> getContactByUserId(Long userId) {
    List<ContactJson> list = getAll();
    Set<Contact> result = new HashSet<>();
    for (ContactJson contactJson : list) {
      if (contactJson.getUserId().equals(userId)) {
        User user = this.userDaoFile.getUserById(contactJson.getUserId())
            .orElseThrow(() -> new NoSuchElementException("Element not found"));
        Contact contact = new Converter().makeContactFromJson(contactJson, user);
        result.add(contact);
      }
    }
    return result;
  }

  @Override
  public Contact saveAndFlush(Contact contact) {
    List<ContactJson> list = getAll();
    ContactJson contactJson = new Converter().makeJsonFromContact(contact);
    boolean isUnique = true;
    for (ContactJson element : list) {
      if (element.getContactId().equals(contact.getId())) {
        isUnique = false;
      }
    }
    if (isUnique) {
      contactJson.setContactId(UUID.randomUUID().getLeastSignificantBits());
    } else {
      list = remove(contactJson.getContactId());
    }
    try {
      list.add(contactJson);
      objectMapper.writeValue(getJsonFile(), list);
    } catch (IOException e) {
      //// TODO: log + exc
      e.printStackTrace();
    }
    return contact;
  }


  @Override
  public void delete(Long contactId) {
    remove(contactId);
  }

  public List<ContactJson> getAll() {
    List<ContactJson> list = new ArrayList<>();
    File jsonFile = getJsonFile();
    if (jsonFile.exists() && !jsonFile.isDirectory()) {
      try {
        list = objectMapper.readValue(jsonFile, new TypeReference<List<ContactJson>>() {
        });
      } catch (IOException e) {
        // TODO:  log
        e.printStackTrace();
      }
    }
    return list;
  }

  private List<ContactJson> remove(Long contactId) {
    List<ContactJson> list = getAll();
    Contact contact = getContactById(contactId)
        .orElseThrow(() -> new NoSuchElementException("Element not found"));
    ContactJson contactJson = new Converter().makeJsonFromContact(contact);
    list = removeFromList(list, contactJson);
    try {
      objectMapper.writeValue(getJsonFile(), list);
    } catch (IOException e) {
      e.printStackTrace();
    }
    List<ContactJson> list1 = getAll();
    return list;
  }

  private List<ContactJson> removeFromList(List<ContactJson> list, ContactJson contactJson) {
    List<ContactJson> result = new ArrayList<>();
    for (ContactJson element : list) {
      if (!element.getContactId().equals(contactJson.getContactId())) {
        result.add(element);
      }
    }
    return result;
  }
}