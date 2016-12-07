package com.katruk.dao.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.katruk.dao.UserDao;
import com.katruk.domain.entity.User;
import org.springframework.stereotype.Repository;
import java.io.File;
import java.util.List;
import java.util.Optional;

@Repository(value = "UserDaoFile")
public class UserDaoFile extends BaseDaoFile implements UserDao {

  public UserDaoFile(ObjectMapper objectMapper) {
    super(objectMapper);
  }

  @Override
  protected File getJsonFile() {
//  todo  String fileName = "${user.json}";
    return new File("src/main/resources/json/user.json");
  }

  @Override
  public Optional<User> getUserByLogin(String login) {
    List<User> users = super.getAll();
    System.out.println(">>> getUserByLogin users="+users+ "  login="+login);

    for (User element : users) {
      if (element.getLogin().equals(login)) {
        System.out.println(">>> equals!!!!!!!!! element="+element);
        return Optional.of(element);
      }
    }
    System.out.println("return empty");
    return Optional.empty();
  }

  @Override
  public User saveAndFlush(User user) {
    return super.save(user);
  }
}
