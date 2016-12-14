package com.katruk.dao.file;

import static java.util.Objects.nonNull;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.katruk.dao.UserDao;
import com.katruk.domain.entity.Contact;
import com.katruk.domain.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.annotation.Lazy;
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
public class UserDaoFile implements UserDao, Injectable {

  //  @Autowired
  private ObjectMapper objectMapper;
  //  @Autowired
  private ContactDaoFile contactDaoFile;
  private File jsonFile;

  @Override
  public void inject(BeansManager beansManager) {
    this.objectMapper = beansManager.getObjectMapper();
    this.contactDaoFile = beansManager.getContactDaoFile();
  }

  protected File getJsonFile() {

    try {
      jsonFile = new File("src/main/resources/json/user.json");
      if (!jsonFile.exists()) {
        jsonFile.createNewFile();
      }
    } catch (IOException e) {
      //todo log and throw
      e.printStackTrace();
    }
    return jsonFile;
  }

  @Override
  public Optional<User> getUserById(Long userId) {
    List<UserJson> users = getAll();

    for (UserJson element : users) {
      if (element.getUserId().equals(userId)) {
        User user = createUser(element);
//        Set<Contact> userContacts = this.contactDaoFile.getContactByUserId(user.getId());
//        user.setContacts(userContacts);
        return Optional.of(user);
      }
    }
    System.out.println("return empty");
    return Optional.empty();
  }

  @Override
  public Optional<User> getUserByLogin(String login) {
    List<UserJson> users = getAll();
    System.out.println(">>> getUserByLogin users=" + users + "  login=" + login);

    for (UserJson element : users) {
      if (element.getLogin().equals(login)) {
        User user = createUser(element);
//        Set<Contact> userContacts = this.contactDaoFile.getContactByUserId(user.getId());
//        user.setContacts(userContacts);
        return Optional.of(user);
      }
    }

    System.out.println(">>> return empty");
    return Optional.empty();
  }


  @Override
  public User saveAndFlush(User user) {

    List<UserJson> list = getAll();
    UserJson userJson = createUserJson(user);
    boolean isUnique = true;
    for (UserJson element : list) {
      if (element.getUserId().equals(user.getId())) {
        isUnique = false;
      }
    }
    if (isUnique) {
      userJson.setUserId(UUID.randomUUID().getLeastSignificantBits());
    } else {
      delete(userJson.getUserId());
    }
    try {
      list.add(userJson);
      objectMapper.writeValue(getJsonFile(), list);
    } catch (IOException e) {
      //// TODO: log + exc
      e.printStackTrace();
    }
    return user;
  }

  private UserJson createUserJson(User user) {
    UserJson userJson = new UserJson();
    userJson.setUserId(user.getId());
    userJson.setLastName(user.getLastName());
    userJson.setName(user.getName());
    userJson.setPatronymic(user.getPatronymic());
    userJson.setLogin(user.getLogin());
    userJson.setPassword(user.getPassword());
    return userJson;
  }

  private void delete(Long userId) {
    List<UserJson> list = getAll();
    User
        user =
        getUserById(userId).orElseThrow(() -> new NoSuchElementException("Element not found"));

    UserJson userJson = createUserJson(user);

    list.remove(list.indexOf(userJson));
    try {
      objectMapper.writeValue(getJsonFile(), list);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private User createUser(UserJson userJson) {
    User user = new User();
    user.setId(userJson.getUserId());
    user.setLastName(userJson.getLastName());
    user.setName(userJson.getName());
    user.setPatronymic(userJson.getPatronymic());
    user.setLogin(userJson.getLogin());
    user.setPassword(userJson.getPassword());
    return user;
  }

  private List<UserJson> getAll() {
    System.out.println(">>> getAll UserJson start");
    List<UserJson> list = new ArrayList<>();

    File jsonFile = getJsonFile();
    if (jsonFile.exists() && !jsonFile.isDirectory()) {
      try {
        list = objectMapper.readValue(jsonFile, new TypeReference<List<UserJson>>() {
        });
      } catch (IOException e) {
        // TODO:  log
        e.printStackTrace();
      }
    }
    for (UserJson us : list) {
      System.out.println(">>> getAll user=   " + us);
    }
    return list;
  }
}
