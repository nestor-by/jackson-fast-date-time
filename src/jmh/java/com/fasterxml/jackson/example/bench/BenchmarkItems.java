package com.fasterxml.jackson.example.bench;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BenchmarkItems {

  public static class Item {
    public LocalDate date;
    public LocalDateTime dateTime;
  }

  public static class DateItem {
    public LocalDate date;
  }

  public static class DateTimeItem {
    public LocalDateTime dateTime;
  }
}
