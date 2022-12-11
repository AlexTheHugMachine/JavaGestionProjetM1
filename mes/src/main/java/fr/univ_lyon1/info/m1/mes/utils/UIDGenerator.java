package fr.univ_lyon1.info.m1.mes.utils;

import java.util.UUID;

public interface UIDGenerator {
  static String generate() {
    UUID uuid = UUID.randomUUID();
    String result = uuid.toString();
    if (Validator.isValidUUID(result)) {
      return result;
    }
    throw new InternalError("UUID generator does not match expected format");
  }
}
