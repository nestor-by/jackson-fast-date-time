package com.fasterxml.jackson.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;

class LocalDateTimeZeroAllocDeserializerTest {

  @Test
  void testDeserializeValidDateTime() throws IOException {
    String json = "\"2024-05-15T10:22:33\"";
    ObjectMapper mapper = createObjectMapper();

    LocalDateTime result = mapper.readValue(json, LocalDateTime.class);

    Assertions.assertEquals(LocalDateTime.of(2024, 5, 15, 10, 22, 33), result);
  }

  @Test
  void testDeserializeValidDateTimeWithMilliseconds() throws IOException {
    String json = "\"2024-05-15T10:22:33.123\"";
    ObjectMapper mapper = createObjectMapper();

    LocalDateTime result = mapper.readValue(json, LocalDateTime.class);

    Assertions.assertEquals(LocalDateTime.of(2024, 5, 15, 10, 22, 33, 123_000_000), result);
  }

  @Test
  void testDeserializeValidDateTimeWithNanoseconds() throws IOException {
    String json = "\"2024-05-15T10:22:33.123456789\"";
    ObjectMapper mapper = createObjectMapper();

    LocalDateTime result = mapper.readValue(json, LocalDateTime.class);

    Assertions.assertEquals(LocalDateTime.of(2024, 5, 15, 10, 22, 33, 123_456_789), result);
  }

  @Test
  void testDeserializeValidDateTimeWithZSuffix() throws IOException {
    String json = "\"2024-05-15T10:22:33Z\"";
    ObjectMapper mapper = createObjectMapper();

    LocalDateTime result = mapper.readValue(json, LocalDateTime.class);

    Assertions.assertEquals(LocalDateTime.of(2024, 5, 15, 10, 22, 33), result);
  }

  @Test
  void testDeserializeNullValue() throws IOException {
    String json = "null";
    ObjectMapper mapper = createObjectMapper();

    LocalDateTime result = mapper.readValue(json, LocalDateTime.class);

    Assertions.assertNull(result);
  }

  @Test
  void testDeserializeInvalidDateFormat() {
    String json = "\"15-05-2024T10:22:33\"";
    ObjectMapper mapper = createObjectMapper();

    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      mapper.readValue(json, LocalDateTime.class);
    });
  }

  @Test
  void testDeserializeEmptyString() {
    String json = "\"\"";
    ObjectMapper mapper = createObjectMapper();

    Assertions.assertThrows(RuntimeException.class, () -> {
      mapper.readValue(json, LocalDateTime.class);
    });
  }

  /**
   * Helper method to create an ObjectMapper configured with the LocalDateTimeZeroAllocDeserializer.
   *
   * @return an ObjectMapper instance
   */
  private ObjectMapper createObjectMapper() {
    SimpleModule module = new SimpleModule();
    module.addDeserializer(LocalDateTime.class, new LocalDateTimeZeroAllocDeserializer());
    return JsonMapper.builder().addModule(module).build();
  }
}
