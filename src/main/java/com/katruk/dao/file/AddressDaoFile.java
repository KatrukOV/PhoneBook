package com.katruk.dao.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.katruk.dao.AddressDao;
import com.katruk.domain.entity.Address;
import org.springframework.stereotype.Repository;
import java.io.File;
import java.util.Optional;

@Repository(value = "AddressDaoFile")
public class AddressDaoFile extends BaseDaoFile implements AddressDao {

  public AddressDaoFile(ObjectMapper objectMapper) {
    super(objectMapper);
  }

  @Override
  protected File getJsonFile() {
    return new File("src/main/resources/json/address.json");
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
