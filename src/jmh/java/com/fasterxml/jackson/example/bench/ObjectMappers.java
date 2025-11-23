package com.fasterxml.jackson.example.bench;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.example.LocalDateJsonDeserializer;
import com.fasterxml.jackson.example.LocalDateTimeJsonDeserializer;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ObjectMappers {

  public static ObjectMapper defaultMapper() {
    JavaTimeModule module = new JavaTimeModule();

    return JsonMapper.builder()
      .addModule(module)
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultTimeZone(java.util.TimeZone.getTimeZone("UTC"))
      .build();
  }

  public static ObjectMapper customMapper() {
    JavaTimeModule module = new JavaTimeModule();
    module.addDeserializer(LocalDate.class, new LocalDateJsonDeserializer());
    module.addDeserializer(LocalDateTime.class, new LocalDateTimeJsonDeserializer());

    return JsonMapper.builder()
      .addModule(module)
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
      .defaultTimeZone(java.util.TimeZone.getTimeZone("UTC"))
      .build();
  }
}
