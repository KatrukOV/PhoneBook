package com.katruk.dao.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.katruk.dao.AddressDao;
import com.katruk.domain.entity.Address;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Repository
@Profile(value = "file")
public class AddressDaoFile extends BaseDaoFile implements AddressDao {

  public AddressDaoFile(ObjectMapper objectMapper) {
    super(objectMapper);
  }

  @Override
  protected File getJsonFile() {
    File jsonFile = null;
    try {
      jsonFile = super.createJsonFile("src/main/resources/json/address.json");
    } catch (IOException e) {
      //todo log and throw
      e.printStackTrace();
    }
    return jsonFile;
  }

  // TODO: this method is redundant (probably)
  @Override
  public Optional<Address> getAddressById(Long addressId) {
    return super.findOne(addressId);
  }

  // TODO: this method is redundant (the same issue in other dao file classes)
  @Override
  public Address saveAndFlush(Address address) {
    return super.save(address);
  }

  @Override
  public void delete(Long addressId) {
    super.<Address>delete(addressId);
  }

}
