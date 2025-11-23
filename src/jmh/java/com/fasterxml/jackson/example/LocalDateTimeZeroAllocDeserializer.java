package com.fasterxml.jackson.example;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;

public class LocalDateTimeZeroAllocDeserializer extends JsonDeserializer<LocalDateTime> {

  @Override
  public LocalDateTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    char[] buf = jp.getTextCharacters();
    int offset = jp.getTextOffset();
    int len = jp.getTextLength();

    if (buf == null || len == 0) {
      return null;
    }

    return fastDateFromChars(buf, offset, len);
  }

  private static LocalDateTime fastDateFromChars(char[] buf, int off, int len) {
    if (len < 19) {
      throw new IllegalArgumentException("Invalid date format");
    }

    if (buf[off + 4] != '-' ||
      buf[off + 7] != '-' ||
      buf[off + 10] != 'T' ||
      buf[off + 13] != ':' ||
      buf[off + 16] != ':') {
      throw new IllegalArgumentException("Invalid date format");
    }

    int year = parse4(buf, off);
    int month = parse2(buf, off + 5);
    int day = parse2(buf, off + 8);
    int hour = parse2(buf, off + 11);
    int minute = parse2(buf, off + 14);
    int second = parse2(buf, off + 17);
    int nanos = retrieveNanos(buf, off, len);

    return LocalDateTime.of(year, month, day, hour, minute, second, nanos);
  }

  private static int parse2(char[] buf, int off) {
    int d1 = buf[off] - '0';
    int d2 = buf[off + 1] - '0';
    return d1 * 10 + d2;
  }

  private static int parse4(char[] buf, int off) {
    int d1 = buf[off] - '0';
    int d2 = buf[off + 1] - '0';
    int d3 = buf[off + 2] - '0';
    int d4 = buf[off + 3] - '0';
    return d1 * 1000 + d2 * 100 + d3 * 10 + d4;
  }

  private static int retrieveNanos(char[] buf, int off, int len) {
    int dotPos = off + 19;
    int end = off + len;

    if (dotPos >= end || buf[dotPos] != '.') {
      return 0;
    }

    int i = dotPos + 1;
    int value = 0;
    int digits = 0;

    while (i < end && digits < 9) {
      char c = buf[i];
      if (c < '0' || c > '9') {
        break;
      }
      value = value * 10 + (c - '0');
      i++;
      digits++;
    }

    while (digits < 9) {
      value *= 10;
      digits++;
    }

    return value;
  }
}
