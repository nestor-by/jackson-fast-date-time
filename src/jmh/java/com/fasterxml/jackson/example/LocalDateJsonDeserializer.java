package com.fasterxml.jackson.example;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class LocalDateJsonDeserializer extends JsonDeserializer<LocalDate> {

  private final LoadingCache<String, LocalDate> cache = CacheBuilder
    .newBuilder()
    .expireAfterAccess(1, TimeUnit.HOURS)
    .build(CacheLoader.from(str -> LocalDate.parse(str, DateTimeFormatter.ISO_LOCAL_DATE)));

  @Override
  public LocalDate deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    return dateFromString(jp.getValueAsString());
  }

  private LocalDate dateFromString(String str) {
    if (str == null) {
      return null;
    }
    return cache.getUnchecked(str);
  }
}
