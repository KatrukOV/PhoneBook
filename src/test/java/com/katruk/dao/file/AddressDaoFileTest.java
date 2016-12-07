package com.katruk.dao.file;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.katruk.domain.DefaultEntity;
import com.katruk.domain.entity.Address;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(JUnit4.class)
public class AddressDaoFileTest {

  @Rule
  public MockitoRule rule = MockitoJUnit.rule();
  @Mock
  private ObjectMapper objectMapper;
  @Spy
  private Address address;
  private List<Address> addresses;
  private AddressDaoFile addressDaoFile;

  @Before
  public void setUp() throws Exception {
    this.addressDaoFile = new AddressDaoFile(objectMapper);
    this.address = new DefaultEntity().address();
    this.addresses = new ArrayList<>();
    this.addresses.add(this.address);
  }

  @Test
  public void getJsonFile() throws Exception {
    //when
    File result = this.addressDaoFile.getJsonFile();

    //then
    assertNotNull(result);
  }

  @Test
  public void getAddressById_exist() throws Exception {
    //given
    Long addressId = 1L;

    //when
    when(this.objectMapper.readValue(any(File.class), any(TypeReference.class)))
        .thenReturn(this.addresses);
    Optional<Address> result = this.addressDaoFile.getAddressById(addressId);

    //then
    verify(this.objectMapper, times(1)).readValue(any(File.class), any(TypeReference.class));
    assertTrue(result.isPresent());
  }

  @Test
  public void getAddressById_empty() throws Exception {
    //given
    Long addressId = 7L;

    //when
    when(this.objectMapper.readValue(any(File.class), any(TypeReference.class)))
        .thenReturn(this.addresses);
    Optional<Address> result = this.addressDaoFile.getAddressById(addressId);

    //then
    verify(this.objectMapper, times(1)).readValue(any(File.class), any(TypeReference.class));
    assertFalse(result.isPresent());
  }

  @Test
  public void saveAndFlush() throws Exception {
    //when
    when(this.objectMapper.readValue(any(File.class), any(TypeReference.class))).thenReturn(this.addresses);
    doNothing().when(this.objectMapper).writeValue(any(File.class), any(TypeReference.class));
    Address result = this.addressDaoFile.saveAndFlush(this.address);

    //then
    verify(this.objectMapper, times(3)).readValue(any(File.class), any(TypeReference.class));
    verify(this.objectMapper, times(2)).writeValue(any(File.class), any(TypeReference.class));
    assertNotNull(result);
  }

  @Test
  public void delete() throws Exception {
    //given
    Long addressId = 1L;

    //when
    when(this.objectMapper.readValue(any(File.class), any(TypeReference.class))).thenReturn(this.addresses);
    doNothing().when(this.objectMapper).writeValue(any(File.class), any(TypeReference.class));
    this.addressDaoFile.delete(addressId);

    //then
    verify(this.objectMapper, times(2)).readValue(any(File.class), any(TypeReference.class));
    verify(this.objectMapper, times(1)).writeValue(any(File.class), any(TypeReference.class));
  }

}