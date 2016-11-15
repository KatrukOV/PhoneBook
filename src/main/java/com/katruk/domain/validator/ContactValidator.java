package com.katruk.domain.validator;

import com.katruk.domain.dto.ContactDto;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class ContactValidator implements Validator {

  private final Pattern PHONE_PATTERN = Pattern.compile(
      "^\\+380\\(\\d{2}\\)\\d{3}-{0,1}\\d{2}-{0,1}\\d{2}$");
  private final Pattern EMAIL_PATTERN = Pattern.compile(
      "^([A-Za-z0-9_-]+\\.)*[A-Za-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$");

  @Override
  public boolean supports(Class<?> clazz) {
    return ContactDto.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    ContactDto contactDto = (ContactDto) target;
    System.out.println(">>> validate contactDto= " + contactDto + " errors= " + errors);

    requiredField(errors);
    System.out.println("1= "+ errors);
    validateNames(errors, contactDto);
    System.out.println("2= "+ errors);
    validatePhones(errors, contactDto);
    System.out.println("3= "+ errors);
    validateEmail(errors, contactDto);
    System.out.println("4= "+ errors);

  }

  private void requiredField(Errors errors) {
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "REQUIRED");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "REQUIRED");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "patronymic", "REQUIRED");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mobilePhone", "REQUIRED");
  }

  private void validateNames(Errors errors, ContactDto contactDto) {
    if (contactDto.getLastName().length() < 4 || contactDto.getLastName().length() > 30) {
      errors.rejectValue("lastName", "INCORRECT_SIZE_LAST_NAME");
    }
    if (contactDto.getName().length() < 4 || contactDto.getName().length() > 30) {
      errors.rejectValue("name", "INCORRECT_SIZE_NAME");
    }
    if (contactDto.getPatronymic().length() < 4 || contactDto.getPatronymic().length() > 30) {
      errors.rejectValue("patronymic", "INCORRECT_SIZE_PATRONYMIC");
    }
  }

  private void validatePhones(Errors errors, ContactDto contactDto) {
    if (!PHONE_PATTERN.matcher(contactDto.getMobilePhone()).matches()) {
      errors.rejectValue("MobilePhone", "INCORRECT_MOBILE_PHONE");
    }
    if (!PHONE_PATTERN.matcher(contactDto.getHomePhone()).matches()) {
      errors.rejectValue("HomePhone", "INCORRECT_HOME_PHONE");
    }
  }

  private void validateEmail(Errors errors, ContactDto contactDto) {
    if (!EMAIL_PATTERN.matcher(contactDto.getEmail()).matches()) {
      errors.rejectValue("email", "INCORRECT_EMAIL");
    }
  }
}
