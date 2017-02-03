package com.katruk.domain.validator;

import com.katruk.domain.entity.dto.ContactDto;

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
      "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");

  @Override
  public boolean supports(Class<?> clazz) {
    return ContactDto.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    ContactDto contactDto = (ContactDto) target;
    requiredField(errors);
    validateNames(errors, contactDto);
    validatePhones(errors, contactDto);
    validateEmail(errors, contactDto);
  }

  private void requiredField(Errors errors) {
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "required");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "patronymic", "required");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mobilePhone", "required");
  }

  private void validateNames(Errors errors, ContactDto contactDto) {
    if (contactDto.getLastName().length() < 4 || contactDto.getLastName().length() > 30) {
      errors.rejectValue("lastName", "lastName.size");
    }
    if (contactDto.getName().length() < 4 || contactDto.getName().length() > 30) {
      errors.rejectValue("name", "name.size");
    }
    if (contactDto.getPatronymic().length() < 4 || contactDto.getPatronymic().length() > 30) {
      errors.rejectValue("patronymic", "patronymic.size");
    }
  }

  private void validatePhones(Errors errors, ContactDto contactDto) {
    if (!PHONE_PATTERN.matcher(contactDto.getMobilePhone()).matches()) {
      errors.rejectValue("mobilePhone", "mobile.phone.incorrect");
    }
    if (!PHONE_PATTERN.matcher(contactDto.getHomePhone()).matches()) {
      errors.rejectValue("homePhone", "home.phone.incorrect");
    }
  }

  private void validateEmail(Errors errors, ContactDto contactDto) {
    if (!EMAIL_PATTERN.matcher(contactDto.getEmail()).matches()) {
      errors.rejectValue("email", "email.incorrect");
    }
  }
}