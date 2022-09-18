package fr.univ_lyon1.info.m1.mes.utils;

import java.util.UUID;

public interface UIDGenerator {
  static String generate() {
    UUID uuid = UUID.randomUUID();
    return uuid.toString();
  }
}
