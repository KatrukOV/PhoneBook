package com.katruk.dao.file;

import com.katruk.dao.UserDao;
import com.katruk.domain.entity.User;

import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Repository(value = "UserDaoFile")
public class UserDaoFile extends BaseDaoFile implements UserDao {

  @Override
  protected File getJsonFile(String path) {
    return new File("src/main/resources/json/user.json");
  }

  @Override
  public Optional<User> getUserByLogin(String login) {
    List<User> users = getAll();
    for (User element : users) {
      if (element.getLogin().equals(login)) {
        return Optional.of(element);
      }
    }
    return Optional.empty();
  }

  @Override
  public User saveAndFlush(User user) {
    return super.save(user);
  }
}
