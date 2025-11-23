## Benchmark results (JMH)

Three implementations were tested:

1. **default** — standard Jackson `LocalDateTimeDeserializer`
2. **custom** — earlier custom implementation using Strings (`substring`, `Integer.parseInt`, etc.)
3. **zeroAlloc** — the new `LocalDateTimeZeroAllocDeserializer` using direct char[] parsing

### Raw results

```
Benchmark                                                                    Mode  Cnt   Score     Error   Units
DateDeserializationBenchmark.test2_zeroAlloc_LocalDateTime                     avgt   10   0.161 ±   0.075   ms/op
DateDeserializationBenchmark.test2_zeroAlloc_LocalDateTime:gc.alloc.rate       avgt   10 572.557 ± 190.794  MB/sec
DateDeserializationBenchmark.test2_zeroAlloc_LocalDateTime:gc.alloc.rate.norm  avgt   10 90803.207 ±  34.693 B/op
DateDeserializationBenchmark.test2_zeroAlloc_LocalDateTime:gc.count            avgt   10    77.000          counts
DateDeserializationBenchmark.test2_zeroAlloc_LocalDateTime:gc.time             avgt   10    44.000          ms

DateDeserializationBenchmark.test2_custom_LocalDateTime                      avgt   10   0.244 ±   0.063   ms/op
DateDeserializationBenchmark.test2_custom_LocalDateTime:gc.alloc.rate        avgt   10 1476.749 ± 292.338 MB/sec
DateDeserializationBenchmark.test2_custom_LocalDateTime:gc.alloc.rate.norm   avgt   10 369652.338 ± 28.247 B/op

DateDeserializationBenchmark.test2_default_LocalDateTime                     avgt   10   0.508 ±   0.037   ms/op
DateDeserializationBenchmark.test2_default_LocalDateTime:gc.alloc.rate       avgt   10 2432.938 ± 157.016 MB/sec
DateDeserializationBenchmark.test2_default_LocalDateTime:gc.alloc.rate.norm  avgt   10 1293266.575 ± 19.157 B/op
```

### Summary

| Variant       | Time (ms/op) | Allocations (B/op) | Speed vs Default | Allocations vs Default |
|---------------|--------------|--------------------|------------------|-------------------------|
| default       | 0.508        | 1,293,266          | 1.0×             | 1.0×                    |
| custom        | 0.244        |   369,652          | ~2.1× faster     | ~3.5× less              |
| zeroAlloc     | 0.161        |    90,803          | ~3.1× faster     | ~14.2× less             |
