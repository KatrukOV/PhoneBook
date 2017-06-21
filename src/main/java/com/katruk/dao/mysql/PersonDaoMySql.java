package com.katruk.dao.mysql;

import com.katruk.dao.PersonDao;
import com.katruk.domain.entity.Person;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile(value = "mysql")
public interface PersonDaoMySql extends JpaRepository<Person, Long>, PersonDao {

}
