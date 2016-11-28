package com.katruk.dao.file;


import com.katruk.dao.ContactDao;
import com.katruk.domain.entity.Contact;

import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.Optional;

//@Repository(value = "ContactDaoFile")
public class ContactDaoFile extends BaseDaoFile implements ContactDao {

  @Override
  protected File getJsonFile(String path) {
    return new File("{$Contact.json}");
  }

  @Override
  public Optional<Contact> getContactById(Long contactId) {
    return super.<Contact>findOne(contactId);
  }

  @Override
  public Contact saveAndFlush(Contact contact) {
    return super.save(contact);
  }

  @Override
//  public void delete(Contact contact) {
//    super.<Contact>delete(contact.getId());
//  }
  public void delete(Long contactId) {
    super.<Contact>delete(contactId);
  }

//  @Override
//  public List<Contact> findByLastName(String lastName) {
//    checkNull(lastName);
//    List<Contact> contacts = getAll();
//    List<Contact> result = new ArrayList<>();
//    if (contacts.isEmpty()) {
//      return result;
//    }
//    for (Contact element : contacts) {
//      if (lastName.equals(element.getPerson().getLastName())) {
//        result.add(element);
//      }
//    }
//    return result;
//  }
//
//  @Override
//  public List<Contact> findByName(String name) {
//    checkNull(name);
//    List<Contact> contacts = getAll();
//    List<Contact> result = new ArrayList<>();
//    if (contacts.isEmpty()) {
//      return result;
//    }
//    for (Contact element : contacts) {
//      if (name.equals(element.getPerson().getName())) {
//        result.add(element);
//      }
//    }
//    return result;
//  }
//
//  @Override
//  public List<Contact> findByMobilePhoneLike(String mobilePhone) {
//    checkNull(mobilePhone);
//    List<Contact> contacts = getAll();
//    List<Contact> result = new ArrayList<>();
//    if (contacts.isEmpty()) {
//      return result;
//    }
//    for (Contact element : contacts) {
//      if (element.getPerson().getName().contains(mobilePhone)) {
//        result.add(element);
//      }
//    }
//    return result;
//  }
}
