package com.katruk.dao.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.katruk.dao.ContactDao;
import com.katruk.domain.entity.Contact;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Repository
@Profile(value = "file")
public class ContactDaoFile extends BaseDaoFile implements ContactDao {

  public ContactDaoFile(ObjectMapper objectMapper) {
    super(objectMapper);
  }

  @Override
  protected File getJsonFile() {
    File jsonFile = null;
    try {
      jsonFile = super.createJsonFile("src/main/resources/json/contact.json");
    } catch (IOException e) {
      //todo log and throw
      e.printStackTrace();
    }
    return jsonFile;
  }

  @Override
  public Optional<Contact> getContactById(Long contactId) {
    return super.findOne(contactId);
  }

  @Override
  public Contact saveAndFlush(Contact contact) {
    return super.save(contact);
  }

  @Override
  public void delete(Long contactId) {
    super.<Contact>delete(contactId);
  }
}
