package com.katruk.dao.mysql;


import com.katruk.dao.UserDao;
import com.katruk.domain.entity.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository(value = "UserDaoMySql")
@Transactional
public interface UserDaoMySql extends CrudRepository<User, Integer>, UserDao {

}
