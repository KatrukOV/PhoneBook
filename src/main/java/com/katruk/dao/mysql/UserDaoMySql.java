package com.katruk.dao.mysql;

import com.katruk.dao.UserDao;
import com.katruk.domain.entity.User;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Profile(value = "mysql")
@Transactional
public interface UserDaoMySql extends JpaRepository<User, Long>, UserDao {

}
