package com.katruk.domain.service;

import com.katruk.domain.dto.ContactDto;
import com.katruk.domain.entity.Address;

public interface AddressService {

  Address create(ContactDto contactDto);

  Address getAddressById(Long addressId);

  Address updateAddress(Long addressId, ContactDto contactDto);

  void delete(Long addressId);

}
