package com.katruk.domain.service.impl;

import com.katruk.dao.AddressDao;
import com.katruk.domain.entity.Address;
import com.katruk.domain.service.AddressService;

import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import javax.annotation.Resource;

@Service
public class AddressServiceImpl implements AddressService {

  @Resource(name = "${AddressDao.class}")
  private final AddressDao addressDao;

  public AddressServiceImpl(AddressDao addressDao) {
    this.addressDao = addressDao;
  }

//  @Autowired
//  public AddressServiceImpl(@Qualifier("AddressDaoMySql") AddressDao addressDao) {
//    this.addressDao = addressDao;
//  }

  @Override
  public Address save(Address address) {
    return this.addressDao.saveAndFlush(address);
  }

  @Override
  public Address getAddressById(Long addressId) {
    return this.addressDao.getAddressById(addressId).orElseThrow(() -> new NoSuchElementException(
        String.format("No address present with id: %s", addressId)));
  }
}
