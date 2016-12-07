package com.katruk.dao;

import com.katruk.domain.entity.Address;

import java.util.Optional;

public interface AddressDao {

  Optional<Address> getAddressById(Long addressId);

  Address saveAndFlush(Address address);

  void delete(Long addressId);

}

