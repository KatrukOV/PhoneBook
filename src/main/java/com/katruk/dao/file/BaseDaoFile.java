package com.katruk.dao.file;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.katruk.domain.entity.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Repository
public abstract class BaseDaoFile {

//  @Value("${json.path}")
//  private String path;

  @Autowired
  private ObjectMapper objectMapper;

  protected File jsonFile; //= new File("src/main/resources/json/base.json");

  @Autowired
  protected abstract File getJsonFile(String path);

//    return new File(path);

  protected <S extends Model> Optional<S> findOne(Long id) {
    List<S> lists = getAll();
    for (S element : lists) {
      if (element.getId().equals(id)) {
        return Optional.of(element);
      }
    }
    return Optional.empty();
  }

  protected <S extends Model> S save(S s) {
    List<S> list = getAll();
    boolean isUnique = true;
    for (S element : list) {
      if (element.getId().equals(s.getId())) {
        isUnique = false;
      }
    }
    if (isUnique) {
      s.setId((Long) UUID.randomUUID().getLeastSignificantBits());
    } else {
      delete(s.getId());
    }
    try {
      list.add(s);
      objectMapper.writeValue(jsonFile, list);
    } catch (IOException e) {
      //// TODO: log + exc
      e.printStackTrace();
    }
    return s;
  }

  <S extends Model> void delete(Long id) {
    List<S> list = getAll();
    S s = (S) findOne(id).orElseThrow(() -> new NoSuchElementException("Element not found"));
    list.remove(list.indexOf(s));
    try {
      objectMapper.writeValue(jsonFile, list);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  <S extends Model> List<S> getAll() {
    List<S> list = null;
    if (jsonFile.exists() && !jsonFile.isDirectory()) {
      try {
        list = objectMapper.readValue(jsonFile, new TypeReference<List<S>>() {
        });
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      list = new ArrayList<>();
    }
    return list;
  }

  protected void checkNull(Object o) {
    if (o == null) {
      throw new NullPointerException("Object is NULL");
    }
  }

}
