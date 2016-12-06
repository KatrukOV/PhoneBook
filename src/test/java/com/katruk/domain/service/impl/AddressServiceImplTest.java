package com.katruk.domain.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.katruk.dao.AddressDao;
import com.katruk.domain.DefaultEntity;
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
  private Address address;
  private AddressService addressService;

  @Before
  public void setUp() throws Exception {
    this.addressService = new AddressServiceImpl(addressDao);
    this.address = new DefaultEntity().address();
  }

  @Test
  public void save_any_address() throws Exception {
    //when
    when(this.addressDao.saveAndFlush(any(Address.class))).thenAnswer(returnsFirstArg());
    Address address = this.addressService.save(this.address);

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
    Address address = this.addressService.save(this.address);

    //then
    assertEquals(addressId, address.getId());
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
}