package com.katruk.dao.mysql;

import com.katruk.dao.AddressDao;
import com.katruk.domain.entity.Address;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile(value = "mysql")
public interface AddressDaoMySql extends JpaRepository<Address, Long>, AddressDao {

}
