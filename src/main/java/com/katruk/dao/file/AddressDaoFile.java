package com.katruk.dao.file;

import com.katruk.dao.AddressDao;
import com.katruk.domain.entity.Address;

import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.Optional;

@Repository(value = "AddressDaoFile")
public class AddressDaoFile extends BaseDaoFile implements AddressDao {

  @Override
  protected File getJsonFile(String path) {
    return new File("src/main/resources/json/address.json");
  }
  // TODO: this method is redundant (probably)
  @Override
  public Optional<Address> getAddressById(Long addressId) {
    return super.<Address>findOne(addressId);
  }

  // TODO: this method is redundant (the same issue in other dao file classes)
  @Override
  public Address saveAndFlush(Address address) {
    return super.save(address);
  }


}
