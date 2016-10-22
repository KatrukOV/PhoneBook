package com.katruk.domain.entity;

import java.io.Serializable;

public abstract class Model implements Serializable {

  protected int id;

  public Model() {

  }

  public Model(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

}
