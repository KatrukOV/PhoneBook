package com.katruk.dao.mysql;


import com.katruk.dao.UserDao;
import com.katruk.domain.entity.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserDaoMySql extends CrudRepository<User, Integer>, UserDao {

//  @Override
//  @Query(value = "SELECT u.last_name, u.name, u.patronymic,  u.login, u.password, FROM User u WHERE u.login = :login", nativeQuery = true)
//  User findUserByLogin(@Param("login") String login);

//  @Override
//  public User getUserByLogin(String login);

}
