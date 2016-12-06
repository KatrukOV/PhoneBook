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

//  private static File jsonFile; //= new File("src/main/resources/json/base.json");

  protected abstract File getJsonFile();


  <S extends Model> Optional<S> findOne(Long id) {

    System.out.println(">>> base findOne id="+id);

    List<S> lists = getAll();
    for (S element : lists) {
      if (element.getId().equals(id)) {
        return Optional.of(element);
      }
    }
    return Optional.empty();
  }

  <S extends Model> S save(S s) {

    System.out.println(">>> base save S="+s);

    List<S> list = getAll();
    boolean isUnique = true;
    for (S element : list) {
      if (element.getId().equals(s.getId())) {
        isUnique = false;
      }
    }
    if (isUnique) {
      s.setId(UUID.randomUUID().getLeastSignificantBits());
    } else {
      delete(s.getId());
    }
    try {
      list.add(s);
      objectMapper.writeValue(getJsonFile(), list);
    } catch (IOException e) {
      //// TODO: log + exc
      e.printStackTrace();
    }
    return s;
  }

  <S extends Model> void delete(Long id) {

    System.out.println(">>> base delete id="+id);

    List<S> list = getAll();
    S s = (S) findOne(id).orElseThrow(() -> new NoSuchElementException("Element not found"));
    list.remove(list.indexOf(s));
    try {
      objectMapper.writeValue(getJsonFile(), list);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  <S extends Model> List<S> getAll() {
    List<S> list = null;

//    System.out.println(">>> before jsonFile=" + jsonFile);

    File jsonFile = getJsonFile();

    System.out.println(">>> jsonFile=" + jsonFile);
    System.out.println(">>> jsonFile.exists()=" + jsonFile.exists());
    System.out.println(">>> !jsonFile.isDirectory()=" + !jsonFile.isDirectory());

    if (jsonFile.exists() && !jsonFile.isDirectory()) {
      try {
        System.out.println(">>> try");
        list = objectMapper.readValue(jsonFile, new TypeReference<List<S>>() {
        });

        System.out.println(">>> list="+list);
      } catch (IOException e) {
        e.printStackTrace();

      }
    } else {
      System.out.println(">>> else");
      list = new ArrayList<>();
    }
    System.out.println(">>> base getAll return="+list);
    return list;
  }

  protected void checkNull(Object o) {
    if (o == null) {
      throw new NullPointerException("Object is NULL");
    }
  }
}
