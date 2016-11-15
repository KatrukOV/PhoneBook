package com.katruk.dao.mysql;

import com.katruk.dao.PersonDao;
import com.katruk.domain.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository(value = "PersonDaoMySql")
@Transactional
public interface PersonDaoMySql extends JpaRepository<Person, Long>, PersonDao {

}
