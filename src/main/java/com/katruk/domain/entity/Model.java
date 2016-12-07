package com.katruk.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

//@JsonDeserialize(using = InstanceDeserializer.class)
//@JsonIgnoreProperties(ignoreUnknown = true)

//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "id")
//@JsonSubTypes({
//    @JsonSubTypes.Type(value = Address.class, name = "Address"),
//    @JsonSubTypes.Type(value = Person.class, name = "Person"),
//    @JsonSubTypes.Type(value = Contact.class, name = "Contact"),
//    @JsonSubTypes.Type(value = User.class, name = "User")
//})
@MappedSuperclass
public abstract class Model implements Serializable {

//  @JsonProperty("id")
  protected Long id;

//  @JsonCreator
  Model() {
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Model model = (Model) o;
    return id != null ? id.equals(model.id) : model.id == null;
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "Model{" +
           "id=" + id +
           '}';
  }
}
