package com.katruk.domain.service.impl;

import static java.util.Objects.nonNull;

import com.katruk.dao.AddressDao;
import com.katruk.domain.dto.ContactDto;
import com.katruk.domain.entity.Address;
import com.katruk.domain.service.AddressService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class AddressServiceImpl implements AddressService {

  private final AddressDao addressDao;

  @Autowired
  public AddressServiceImpl(AddressDao addressDao) {
    this.addressDao = addressDao;
  }

  @Override
  public Address create(ContactDto contactDto) {
    if (!addressNotEmptyField(contactDto)) {
      return null;
    }
    Address address = new Address();
    address.setCity(contactDto.getCity().trim());
    address.setStreet(contactDto.getStreet().trim());
    address.setBuilding(contactDto.getBuilding().trim());
    address.setApartment(contactDto.getApartment());
    return this.addressDao.saveAndFlush(address);
  }

  @Override
  public Address getAddressById(Long addressId) {
    return this.addressDao.getAddressById(addressId).orElseThrow(() -> new NoSuchElementException(
        String.format("No address present with id: %s", addressId)));
  }

  //todo refactor
  @Override
  public Address updateAddress(Long addressId, ContactDto contactDto) {

    if (!addressNotEmptyField(contactDto)) {
      this.addressDao.delete(addressId);
      return null;
    }

    Address address;
    if (addressId <= 0) {
      address = new Address();
    } else {
      address = getAddressById(addressId);
    }

    if (nonNull(contactDto.getCity()) && !contactDto.getCity().equals(address.getCity())) {
      address.setCity(contactDto.getCity().trim());
    }
    if (contactDto.getStreet() != null
        && !contactDto.getStreet().equals(address.getStreet())) {
      address.setStreet(contactDto.getStreet().trim());
    }
    if (contactDto.getBuilding() != null
        && !contactDto.getBuilding().equals(address.getBuilding())) {
      address.setBuilding(contactDto.getBuilding().trim());
    }
    if (contactDto.getApartment() <= 0
        && Objects.equals(contactDto.getApartment(), address.getApartment())) {
      address.setApartment(contactDto.getApartment());
    }
    return this.addressDao.saveAndFlush(address);
  }

  @Override
  public void delete(Long addressId) {
    this.addressDao.delete(addressId);
  }

  private boolean addressNotEmptyField(ContactDto contactDto) {
    return nonNull(contactDto.getCity()) || nonNull(contactDto.getStreet())
           || nonNull(contactDto.getBuilding()) || contactDto.getApartment() > 0;
  }
}
