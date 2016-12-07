package com.katruk.dao.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.katruk.dao.ContactDao;
import com.katruk.domain.entity.Contact;
import org.springframework.stereotype.Repository;
import java.io.File;
import java.util.Optional;

@Repository(value = "ContactDaoFile")
public class ContactDaoFile extends BaseDaoFile implements ContactDao {

  public ContactDaoFile(ObjectMapper objectMapper) {
    super(objectMapper);
  }

  @Override
  protected File getJsonFile() {
    return new File("src/main/resources/json/contact.json");
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
