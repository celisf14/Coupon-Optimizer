package com.meli.couponoptimizer.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;


@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class ObjectMapperUtil {

  private static final ObjectMapper objectMapper = new ObjectMapper();


  public static <T> T readValue(String content, Class<T> valueType) throws JsonProcessingException {
    return objectMapper.readValue(content, valueType);
  }

  public static <T> T readValue(String content, TypeReference<T> valueTypeRef) throws JsonProcessingException {
    return objectMapper.readValue(content, valueTypeRef);
  }

  public static <T> T readValue(byte[] content, Class<T> valueType) throws IOException {
    return objectMapper.readValue(content, valueType);
  }

  public static String writeValueAsString(Object value) throws JsonProcessingException {
    return objectMapper.writeValueAsString(value);
  }

  public static <T> T convertValue(Object fromValue, TypeReference<T> toValueTypeRef) throws IllegalArgumentException {
    return objectMapper.convertValue(fromValue, toValueTypeRef);
  }

}
