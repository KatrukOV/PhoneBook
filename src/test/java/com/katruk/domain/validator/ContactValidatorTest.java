package com.katruk.domain.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.katruk.domain.DefaultEntity;
import com.katruk.domain.dto.ContactDto;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DirectFieldBindingResult;

@RunWith(JUnit4.class)
public class ContactValidatorTest {

  @Rule
  public MockitoRule rule = MockitoJUnit.rule();
  @Spy
  private ContactDto contactDto;
  private BindingResult errors;
  private ContactValidator contactValidator;

  @Before
  public void setUp() throws Exception {
    this.contactValidator = new ContactValidator();
    this.contactDto = new DefaultEntity().contactDto();
    this.errors = new DirectFieldBindingResult(contactDto, "contactDto");
  }

  @Test
  public void supports_true() throws Exception {
    //when
    boolean result = this.contactValidator.supports(contactDto.getClass());

    //then
    assertTrue(result);
  }
  @Test
  public void supports_false() throws Exception {
    //when
    boolean result = this.contactValidator.supports(errors.getClass());

    //then
    assertFalse(result);
  }

  @Test
  public void validate_good_contact_without_error() throws Exception {
    //when
    this.contactValidator.validate(contactDto, errors);

    //then
    assertFalse(errors.hasErrors());
  }

  @Test
  public void validate_contact_with_incorrect_last_name_small_size() throws Exception {
    //given
    this.contactDto.setLastName("L");

    //when
    this.contactValidator.validate(contactDto, errors);

    //then
    assertNotNull(errors.getFieldError("lastName"));
  }

  @Test
  public void validate_contact_with_incorrect_last_name_large_size() throws Exception {
    //given
    this.contactDto.setLastName("InThisContactDtoLastNameHaveVeryLargeSize");

    //when
    this.contactValidator.validate(contactDto, errors);

    //then
    assertNotNull(errors.getFieldError("lastName"));
  }

  @Test
  public void validate_contact_with_incorrect_name_small_size() throws Exception {
    //given
    this.contactDto.setName("N");

    //when
    this.contactValidator.validate(contactDto, errors);

    //then
    assertNotNull(errors.getFieldError("name"));
  }

  @Test
  public void validate_contact_with_incorrect_name_large_size() throws Exception {
    //given
    this.contactDto.setName("InThisContactDtoNameHaveVeryLargeSize");

    //when
    this.contactValidator.validate(contactDto, errors);

    //then
    assertNotNull(errors.getFieldError("name"));
  }


  @Test
  public void validate_contact_with_incorrect_patronymic_small_size() throws Exception {
    //given
    this.contactDto.setPatronymic("P");

    //when
    this.contactValidator.validate(contactDto, errors);

    //then
    assertNotNull(errors.getFieldError("patronymic"));
  }

  @Test
  public void validate_contact_with_incorrect_patronymic_large_size() throws Exception {
    //given
    this.contactDto.setPatronymic("InThisContactDtoPatronymicHaveVeryLargeSize");

    //when
    this.contactValidator.validate(contactDto, errors);

    //then
    assertNotNull(errors.getFieldError("patronymic"));
  }

  @Test
  public void validate_contact_with_incorrect_mobilePhone() throws Exception {
    //given
    this.contactDto.setMobilePhone("(38077)456-88-55");

    //when
    this.contactValidator.validate(contactDto, errors);

    //then
    assertNotNull(errors.getFieldError("mobilePhone"));
  }

@Test
  public void validate_contact_with_incorrect_homePhone() throws Exception {
    //given
    this.contactDto.setHomePhone("656-88-55");

    //when
    this.contactValidator.validate(contactDto, errors);

    //then
    assertNotNull(errors.getFieldError("homePhone"));
  }

@Test
  public void validate_contact_with_incorrect_email() throws Exception {
    //given
    this.contactDto.setEmail("bed#emale");

    //when
    this.contactValidator.validate(contactDto, errors);

    //then
    assertNotNull(errors.getFieldError("email"));
  }
}