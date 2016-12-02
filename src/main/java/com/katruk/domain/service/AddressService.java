package com.katruk.domain.service;

import com.katruk.domain.entity.Address;

public interface AddressService {

  Address save(Address address);

  Address getAddressById(Long addressId);

}
