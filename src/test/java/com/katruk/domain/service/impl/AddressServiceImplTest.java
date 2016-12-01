package com.katruk.domain.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.katruk.dao.AddressDao;
import com.katruk.domain.entity.Address;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Optional;


public class AddressServiceImplTest {

  @InjectMocks
  private AddressServiceImpl addressService;

  @Mock
  private AddressDao addressDao;

  private Address address;

  @Before
  public void setUp() throws Exception {
    addressDao = mock(AddressDao.class);
//    MockitoAnnotations.initMocks(this);

    addressService = new AddressServiceImpl(addressDao);

    address = new Address();
    address.setCity("City");
    address.setStreet("Street");
    address.setBuilding("12/4");
    address.setApartment(15);
  }

  @Test
  public void save() throws Exception {

//    when(addressDao.saveAndFlush(any(Address.class))).thenAnswer(returnsFirstArg());

    when(addressDao.saveAndFlush(any(Address.class)))
        .thenAnswer(new Answer<Address>() {
          @Override
          public Address answer(InvocationOnMock invocation) throws Throwable {
            Address address = invocation.getArgumentAt(0, Address.class);
            address.setId(1L);
            return address;
          }
        });

    assertNull(address.getId());

    address = addressService.save(address);

    assertNotNull(address.getId());
    assertTrue(address.getId() == 1L);
  }

  @Test
  public void getAddressById() throws Exception {
    Long addressId = 2L;

//    when(addressDao.getAddressById(anyLong()))
//        .thenAnswer(new Answer<Address>() {
//          @Override
//          public Address answer(InvocationOnMock invocation) throws Throwable {
//            System.out.println(">> in answer getAddressById invocation=" + invocation);
//
////            Address address = (Address) invocation.callRealMethod();
//            Address address = invocation.getArgumentAt(0, Address.class);
//
//            address.setId(2L);
//            System.out.println(">> in answer getAddressById address=" + address);
//            return address;
//          }
//        });


    when(addressDao.getAddressById(addressId)).thenReturn(Optional.of(new Address()));

//    when(addressDao.getAddressById(anyLong())).thenAnswer(new Answer<Address>() {
//      @Override
//      public Address answer(InvocationOnMock invocation) {
//        Object[] args = invocation.getArguments();
//        Object mock = invocation.getMock();
//
//        return document(fakeIndex((int)(Integer)args[0]));
//
////        return document(fakeIndex((int)(Integer)args[0]));
//
//      }
//    });



//    System.out.println(">>> address.getId()="+address.getId());
//    address = null;

//    assertNull(address);

//    System.out.println(">>> address=" + address);
//    System.out.println(">>> addressService="+addressService);

//    address.setId(addressId);
    Address address = addressService.getAddressById(addressId);

    System.out.println(">>> address 11=" + address);

    assertNotNull(address);

//    assertTrue(address.getId() > 0);
  }

}