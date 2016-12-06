package com.katruk.dao.file;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.katruk.domain.entity.Address;
import com.katruk.domain.entity.Model;
import com.katruk.domain.entity.Person;

import java.io.IOException;

//public class InstanceDeserializer extends JsonDeserializer<Model> {
//
//  @Override
//  public Model deserialize(JsonParser jp, DeserializationContext ctxt)
//      throws IOException, JsonProcessingException {
//    ObjectMapper mapper = (ObjectMapper) jp.getCodec();
//    ObjectNode root = (ObjectNode) mapper.readTree(jp);
//    Class<? extends Model> instanceClass = null;
//    if (checkConditionsForUser()) {
//      instanceClass = Person.class;
//    } else {
//      instanceClass = Address.class;
//    }
//    if (instanceClass == null) {
//      return null;
//    }
//    return mapper.readValue(root, instanceClass);
//  }
//
//}
