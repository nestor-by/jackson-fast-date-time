package com.fasterxml.jackson.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Unit tests for the `LocalDateJsonDeserializer` class.
 * This class is responsible for deserializing JSON strings into `LocalDate` instances.
 */
public class LocalDateJsonDeserializerTest {

  @Test
  void testDeserializeValidDate() throws IOException {
    String json = "\"2025-11-23\"";
    ObjectMapper mapper = createObjectMapper();

    LocalDate result = mapper.readValue(json, LocalDate.class);

    Assertions.assertEquals(LocalDate.of(2025, 11, 23), result);
  }

  @Test
  void testDeserializeNullValue() throws IOException {
    String json = "null";
    ObjectMapper mapper = createObjectMapper();

    LocalDate result = mapper.readValue(json, LocalDate.class);

    Assertions.assertNull(result);
  }

  @Test
  void testDeserializeInvalidDateFormat() {
    String json = "\"23-11-2025\"";
    ObjectMapper mapper = createObjectMapper();

    Assertions.assertThrows(RuntimeException.class, () -> {
      mapper.readValue(json, LocalDate.class);
    });
  }

  @Test
  void testDeserializeEmptyString() {
    String json = "\"\"";
    ObjectMapper mapper = createObjectMapper();

    Assertions.assertThrows(RuntimeException.class, () -> {
      mapper.readValue(json, LocalDate.class);
    });
  }

  /**
   * Helper method to create an ObjectMapper configured with the LocalDateJsonDeserializer.
   *
   * @return an ObjectMapper instance
   */
  private ObjectMapper createObjectMapper() {
    SimpleModule module = new SimpleModule();
    module.addDeserializer(LocalDate.class, new LocalDateJsonDeserializer());
    return JsonMapper.builder().addModule(module).build();
  }
}
