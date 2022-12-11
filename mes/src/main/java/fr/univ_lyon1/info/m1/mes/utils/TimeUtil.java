package fr.univ_lyon1.info.m1.mes.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public interface TimeUtil {
  static String getDate(String pattern) {
    if (pattern.isEmpty()) {
      throw new IllegalArgumentException();
    }
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
    LocalDateTime now = LocalDateTime.now();
    return dtf.format(now);
  }
}
