# LocalDateTime Deserialization Benchmark (Short Version)

## Summary Comparison

| Variant      | Time (ms/op) | Bytes per Operation | GC Count | GC Time (ms) |
|--------------|--------------|----------------------|----------|--------------|
| default      | ~2.765       | ~1,294,166 B         | 54       | 260          |
| custom       | ~1.699       | ~370,050 B           | 24       | 333          |
| zeroAlloc    | ~0.623       | ~90,936 B            | 29       | 101          |

**zeroAlloc** is the fastest and allocates the least memory  
(≈4.5× faster than default, ≈3× faster than custom, ≈14× fewer allocations than default).

---

## Raw Benchmark Results

### default

```
LocalDateDeserializationBenchmark.test2_default_LocalDateTime                       avgt   10        2.765 ± 2.251   ms/op
LocalDateDeserializationBenchmark.test2_default_LocalDateTime:gc.alloc.rate         avgt   10      625.679 ± 598.009 MB/sec
LocalDateDeserializationBenchmark.test2_default_LocalDateTime:gc.alloc.rate.norm    avgt   10  1294166.117 ± 670.974 B/op
LocalDateDeserializationBenchmark.test2_default_LocalDateTime:gc.count              avgt   10       54.000 counts
LocalDateDeserializationBenchmark.test2_default_LocalDateTime:gc.time               avgt   10      260.000 ms
```

### custom

```
LocalDateDeserializationBenchmark.test2_custom_LocalDateTime                        avgt   10        1.699 ± 1.138   ms/op
LocalDateDeserializationBenchmark.test2_custom_LocalDateTime:gc.alloc.rate          avgt   10      227.850 ± 80.600  MB/sec
LocalDateDeserializationBenchmark.test2_custom_LocalDateTime:gc.alloc.rate.norm     avgt   10      370049.595 ± 125.909 B/op
LocalDateDeserializationBenchmark.test2_custom_LocalDateTime:gc.count               avgt   10       24.000 counts
LocalDateDeserializationBenchmark.test2_custom_LocalDateTime:gc.time                avgt   10      333.000 ms
```

### zeroAlloc

```
LocalDateDeserializationBenchmark.test2_zeroAlloc_LocalDateTime                     avgt   10        0.623 ± 0.721   ms/op
LocalDateDeserializationBenchmark.test2_zeroAlloc_LocalDateTime:gc.alloc.rate       avgt   10      214.594 ± 207.325 MB/sec
LocalDateDeserializationBenchmark.test2_zeroAlloc_LocalDateTime:gc.alloc.rate.norm  avgt   10       90935.626 ± 120.387 B/op
LocalDateDeserializationBenchmark.test2_zeroAlloc_LocalDateTime:gc.count            avgt   10       29.000 counts
LocalDateDeserializationBenchmark.test2_zeroAlloc_LocalDateTime:gc.time             avgt   10      101.000 ms
```

---

## Run the Benchmark

```bash
./gradlew jmh
```
