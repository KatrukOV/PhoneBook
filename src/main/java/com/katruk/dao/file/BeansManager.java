package com.katruk.dao.file;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

/**
 * For circular reference problem
 */
@Component
@Profile(value = "file")
public class BeansManager {

  @Autowired
  private ContactDaoFile contactDaoFile;
  @Autowired
  private UserDaoFile userDaoFile;
  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private Set<Injectable> injectables = new HashSet();

  @PostConstruct
  private void inject() {
    for (Injectable injectableItem : injectables) {
      injectableItem.inject(this);
    }
  }

  public ContactDaoFile getContactDaoFile() {
    return contactDaoFile;
  }

  public void setContactDaoFile(ContactDaoFile contactDaoFile) {
    this.contactDaoFile = contactDaoFile;
  }

  public UserDaoFile getUserDaoFile() {
    return userDaoFile;
  }

  public void setUserDaoFile(UserDaoFile userDaoFile) {
    this.userDaoFile = userDaoFile;
  }

  public ObjectMapper getObjectMapper() {
    return objectMapper;
  }

  public void setObjectMapper(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }
}
