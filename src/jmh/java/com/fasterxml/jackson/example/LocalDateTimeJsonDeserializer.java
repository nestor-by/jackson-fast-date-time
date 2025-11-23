package com.fasterxml.jackson.example;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;

public class LocalDateTimeJsonDeserializer extends JsonDeserializer<LocalDateTime> {
  @Override
  public LocalDateTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    return dateFromString(jp.getValueAsString());
  }

  private LocalDateTime dateFromString(String str) {
    if (str == null) {
      return null;
    }
    return fastDateFromString(str);
  }

  protected LocalDateTime fastDateFromString(String str) {
    // fast yyyy-MM-dd'T'HH:mm:ss'Z'
    //          |  |  |    |  |
    //          4  7  10   13 16
    if (str.charAt(4) == '-' && str.charAt(7) == '-' && str.charAt(10) == 'T' && str.charAt(13) == ':' && str.charAt(16) == ':') {
      int nanos = retrieveNanos(str);
      return LocalDateTime.of(
        Integer.parseInt(str.substring(0, 4)),
        Integer.parseInt(str.substring(5, 7)),
        Integer.parseInt(str.substring(8, 10)),
        Integer.parseInt(str.substring(11, 13)),
        Integer.parseInt(str.substring(14, 16)),
        Integer.parseInt(str.substring(17, 19)),
        nanos
      );
    }
    throw new IllegalArgumentException("Invalid date format: " + str);
  }

  private int retrieveNanos(String str) {
    int nanos = 0;
    if (str.length() > 19 && str.length() < 30 && str.charAt(19) == '.') {
      String numbers = getNumbers(str.substring(20));
      if (numbers.isEmpty()) {
        return nanos;
      }
      nanos = Integer.parseInt(numbers);
      nanos *= (int) Math.pow(10, 9.0 - numbers.length());
    }
    return nanos;
  }

  private String getNumbers(String text) {
    StringBuilder sb = new StringBuilder();
    char current;
    for (int i = 0; i <= text.length() - 1; i++) {
      current = text.charAt(i);
      if (!Character.isDigit(current)) {
        return sb.toString();
      }
      sb.append(current);
    }
    return sb.toString();
  }
}
