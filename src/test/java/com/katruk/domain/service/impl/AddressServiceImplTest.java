package com.katruk.domain.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.katruk.dao.AddressDao;
import com.katruk.domain.DefaultEntity;
import com.katruk.domain.dto.ContactDto;
import com.katruk.domain.entity.Address;
import com.katruk.domain.service.AddressService;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;

import java.util.NoSuchElementException;
import java.util.Optional;

@RunWith(JUnit4.class)
public class AddressServiceImplTest {

  @Rule
  public MockitoRule rule = MockitoJUnit.rule();
  @Mock(name = "${AddressDao.class}")
  private AddressDao addressDao;
  @Spy
  private ContactDto contactDto;
  private AddressService addressService;

  @Before
  public void setUp() throws Exception {
    this.addressService = new AddressServiceImpl(addressDao);
    this.contactDto = new DefaultEntity().contactDto();
  }

  @Test
  public void save_any_address() throws Exception {
    //when
    when(this.addressDao.saveAndFlush(any(Address.class))).thenAnswer(returnsFirstArg());
    Address address = this.addressService.create(this.contactDto);

    //then
    assertNotNull(address);
  }

  @Test
  public void save_and_check_by_id() throws Exception {
    //given
    Long addressId = 2L;

    //when
    when(this.addressDao.saveAndFlush(any(Address.class)))
        .thenAnswer(new Answer<Address>() {
          @Override
          public Address answer(InvocationOnMock invocation) throws Throwable {
            Address address = invocation.getArgumentAt(0, Address.class);
            address.setId(addressId);
            return address;
          }
        });
    Address address = this.addressService.create(this.contactDto);

    //then
    assertEquals(addressId, address.getId());
  }

  @Test
  public void save_empty_address() throws Exception {
    //given
    ContactDto contactDto = this.contactDto;
    contactDto.setCity(null);
    contactDto.setStreet(null);
    contactDto.setBuilding(null);
    contactDto.setApartment(0);

    //when
    when(this.addressDao.saveAndFlush(any(Address.class))).thenReturn(mock(Address.class));
    Address address = this.addressService.create(this.contactDto);

    //then
    verify(this.addressDao, times(0)).saveAndFlush(any(Address.class));
    assertNull(address);
  }

  @Test
  public void getAddressById() throws Exception {
    //given
    Long addressId = 3L;

    //when
    when(this.addressDao.getAddressById(addressId)).thenReturn(Optional.of(new Address()));
    Address address = this.addressService.getAddressById(addressId);

    //then
    assertNotNull(address);
  }

  @Test(expected = NoSuchElementException.class)
  public void getAddressById_then_return_optional_empty() throws Exception {
    //when
    when(this.addressDao.getAddressById(anyLong())).thenReturn(Optional.empty());
    this.addressService.getAddressById(anyLong());
  }

  @Test
  public void updateAddress_was_empty_id() throws Exception {
    //given
    Long addressId = -1L;

    //when
    whenUpdateAddressScript();
    Address result = this.addressService.updateAddress(addressId, this.contactDto);

    //then
    verify(this.addressDao, times(0)).getAddressById(anyLong());
    verify(this.addressDao, times(0)).delete(anyLong());
    assertNotNull(result);
  }

  @Test
  public void updateAddress_will_empty_address() throws Exception {
    //given
    ContactDto contactDto = this.contactDto;
    contactDto.setCity(null);
    contactDto.setStreet(null);
    contactDto.setBuilding(null);
    contactDto.setApartment(0);

    //when
    whenUpdateAddressScript();
    Address result = this.addressService.updateAddress(anyLong(), contactDto);

    //then
    verify(this.addressDao, times(0)).getAddressById(anyLong());
    verify(this.addressDao, times(1)).delete(anyLong());
    assertNull(result);
  }

  @Test
  public void updateAddress_address_update() throws Exception {
    //given
    Long addressId = 1L;
    ContactDto contactDto = this.contactDto;
    contactDto.setCity("NewCity");
    contactDto.setStreet("NewStreet");
    contactDto.setBuilding("999");
    contactDto.setApartment(100);

    //when
    whenUpdateAddressScript();
    Address result = this.addressService.updateAddress(addressId, contactDto);

    //then
    verify(this.addressDao, times(1)).getAddressById(anyLong());
    verify(this.addressDao, times(0)).delete(anyLong());
    assertNotNull(result);
  }
  @Test
  public void updateAddress_address_not_update() throws Exception {
    //given
    Long addressId = 1L;

    //when
    whenUpdateAddressScript();
    Address result = this.addressService.updateAddress(addressId, this.contactDto);

    //then
    verify(this.addressDao, times(1)).getAddressById(anyLong());
    verify(this.addressDao, times(0)).delete(anyLong());
    assertNotNull(result);
  }

  @Test
  public void delete() throws Exception {
    //when
    doNothing().when(this.addressDao).delete(anyLong());
    this.addressService.delete(anyLong());

    //then
    verify(this.addressDao, times(1)).delete(anyLong());
  }

  private void whenUpdateAddressScript() {
    when(this.addressDao.saveAndFlush(any(Address.class))).thenAnswer(returnsFirstArg());
    when(this.addressDao.getAddressById(anyLong())).thenReturn(
        Optional.ofNullable(new DefaultEntity().address()));
    doNothing().when(this.addressDao).delete(anyLong());
  }
}