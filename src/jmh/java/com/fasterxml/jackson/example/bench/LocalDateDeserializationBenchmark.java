package com.fasterxml.jackson.example.bench;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.fasterxml.jackson.example.bench.BenchmarkItems.DateTimeItem;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 3, time = 2)
@Measurement(iterations = 5, time = 3)
@Fork(value = 1)
@State(Scope.Benchmark)
public class LocalDateDeserializationBenchmark {

  private ObjectMapper defaultMapper;
  private ObjectMapper customMapper;
  private ObjectMapper zeroAllocMapper;
  private String json;

  @Setup(Level.Trial)
  public void setup() throws Exception {
    json = Resources.toString(Resources.getResource("example.json"), StandardCharsets.UTF_8);
    defaultMapper = ObjectMappers.defaultMapper();
    customMapper = ObjectMappers.customMapper();
    zeroAllocMapper = ObjectMappers.zeroAllocMapper();
  }

  @Benchmark
  public List<DateTimeItem> test2_default_LocalDateTime() throws IOException {
    return defaultMapper.readValue(json, new TypeReference<>() {
    });
  }

  @Benchmark
  public List<DateTimeItem> test2_custom_LocalDateTime() throws IOException {
    return customMapper.readValue(json, new TypeReference<>() {
    });
  }

  @Benchmark
  public List<DateTimeItem> test2_zeroAlloc_LocalDateTime() throws IOException {
    return zeroAllocMapper.readValue(json, new TypeReference<>() {
    });
  }
}
