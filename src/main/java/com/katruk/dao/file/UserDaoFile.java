package com.katruk.dao.file;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.katruk.dao.UserDao;
import com.katruk.domain.entity.User;
import com.katruk.domain.entity.json.UserJson;
import com.katruk.domain.util.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile(value = "file")
public class UserDaoFile implements UserDao {

  @Autowired
  private ObjectMapper objectMapper;

  private File jsonFile;


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

    for (UserJson userJson : users) {
      if (userJson.getUserId().equals(userId)) {
        User user = new Converter().makeUserFromJson(userJson);
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

    for (UserJson userJson : users) {
      if (userJson.getLogin().equals(login)) {
        User user = new Converter().makeUserFromJson(userJson);
        return Optional.of(user);
      }
    }

    System.out.println(">>> return empty");
    return Optional.empty();
  }


  @Override
  public User saveAndFlush(User user) {

    List<UserJson> list = getAll();
    UserJson userJson = new Converter().makeJsonFromUser(user);
    boolean isUnique = true;
    for (UserJson element : list) {
      if (element.getUserId().equals(user.getId())) {
        isUnique = false;
      }
    }
    if (isUnique) {
      userJson.setUserId(UUID.randomUUID().getLeastSignificantBits());
    } else {
      list = remove(userJson.getUserId());
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

  private List<UserJson> remove(Long userId) {
    List<UserJson> list = getAll();
    User user = getUserById(userId)
        .orElseThrow(() -> new NoSuchElementException("Element not found"));

    UserJson userJson = new Converter().makeJsonFromUser(user);

    list.remove(list.indexOf(userJson));
    try {
      objectMapper.writeValue(getJsonFile(), list);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return list;
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
