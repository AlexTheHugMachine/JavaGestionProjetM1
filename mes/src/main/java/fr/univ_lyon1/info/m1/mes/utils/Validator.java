package fr.univ_lyon1.info.m1.mes.utils;

public interface Validator {
  static Boolean isLetter(String input) {
    return input.matches("^[a-zA-Z]+$");
  }

  static Boolean isNumber(String input) {
    return input.matches("^[0-9]+$");
  }

  static Boolean isNumberOfLength(String input, int lenghtExpected) {
    return input.matches("^[0-9]{" + lenghtExpected + "}$");
  }

  static Boolean isValidUUID(String uUID) {
    final String uUIDregex =
    "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
    return uUID.matches(uUIDregex);
  }

  static Boolean startWithNumbersAndEndWithLetters(String input) {
    return input.matches("^[0-9]+[A-Za-z]+$");
  }

  static Boolean onlyLettersAndSpaces(String input) {
    return input.matches("^[A-Za-z\\s]*$");
  }
}
