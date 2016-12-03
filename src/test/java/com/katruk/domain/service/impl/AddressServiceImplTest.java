package com.katruk.domain.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.katruk.dao.AddressDao;
import com.katruk.domain.DefaultEntity;
import com.katruk.domain.entity.Address;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.NoSuchElementException;
import java.util.Optional;


public class AddressServiceImplTest {

  @InjectMocks
  private AddressServiceImpl addressService;

  @Mock
  private AddressDao addressDao;

  private Address address;

  @Before
  public void setUp() throws Exception {

    MockitoAnnotations.initMocks(this);

    this.addressService = new AddressServiceImpl(addressDao);

    this.address = new DefaultEntity().address();

  }

  @Test
  public void saveEasy() throws Exception {

    when(this.addressDao.saveAndFlush(any(Address.class))).thenAnswer(returnsFirstArg());

    Address address = this.addressService.save(this.address);

    assertNotNull(address);
  }

  @Test
  public void save_and_check_by_id() throws Exception {

    when(this.addressDao.saveAndFlush(any(Address.class)))
        .thenAnswer(new Answer<Address>() {
          @Override
          public Address answer(InvocationOnMock invocation) throws Throwable {
            Address address = invocation.getArgumentAt(0, Address.class);
            address.setId(2L);
            return address;
          }
        });

    assertTrue(this.address.getId() == 1L);

    Address address = this.addressService.save(this.address);

    assertTrue(address.getId() == 2L);
  }

  @Test
  public void getAddressById() throws Exception {
    Long addressId = 2L;

    when(this.addressDao.getAddressById(addressId)).thenReturn(Optional.of(new Address()));

    Address address = this.addressService.getAddressById(addressId);

    assertNotNull(address);

  }

  @Test(expected = NoSuchElementException.class)
  public void getAddressById_get_null() throws Exception {
    Long addressId = 2L;

    when(this.addressDao.getAddressById(addressId)).thenThrow(new NoSuchElementException());

    Address address = this.addressService.getAddressById(addressId);

    verify(this.addressService.getAddressById(addressId));
    assertNotNull(address);
    // TODO: ??
    fail();
  }
}