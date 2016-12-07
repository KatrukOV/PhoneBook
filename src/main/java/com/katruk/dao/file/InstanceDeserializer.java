package com.katruk.dao.file;

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
