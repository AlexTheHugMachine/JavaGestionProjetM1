package fr.univ_lyon1.info.m1.mes.utils;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ValidatorTest {

  @Test
  void isLetterReturningFalseWhenGivingBadFormattedString() {
    String stringWithNumbers = "Hell0 Wo4l8";
    String stringWithSymbols = "Enz% +/.?";
    String stringWithNumbersAndSymbols = "Ra3p08%/.dsqq";

    assertAll(
        () -> assertFalse(Validator.isLetter(stringWithNumbersAndSymbols)),
        () -> assertFalse(Validator.isLetter(stringWithNumbers)),
        () -> assertFalse(Validator.isLetter(stringWithSymbols)));
  }

  @Test
  void isNumberOfLengthReturningFalseWhenGivingBadFormattedString() {
    String stringWithLetters = "8982168219a69";
    String stringWithOnlyLetters = "DZGJAKGDKGDSL";
    String stringWithSymbols = "6/%*8271-8521";
    String stringWithNumbersThatDoNotCorrespondToLength = "65554120989685";
    String stringWithLettersAndSymbols = "9769a£8652Z*";

    assertAll(
        () -> assertFalse(Validator.isNumberOfLength(
            stringWithLetters, 11)),
        () -> assertFalse(Validator.isNumberOfLength(
            stringWithOnlyLetters, 11)),
        () -> assertFalse(Validator.isNumberOfLength(
            stringWithSymbols, 11)),
        () -> assertFalse(Validator.isNumberOfLength(
            stringWithOnlyLetters, 11)),
        () -> assertFalse(Validator.isNumberOfLength(
            stringWithNumbersThatDoNotCorrespondToLength, 11)),
        () -> assertFalse(Validator.isNumberOfLength(
            stringWithLettersAndSymbols, 11)));
  }

  @Test
  void isValidUUIDReturningFalseWhenHavingBadStringParam() {
    String stringWithGoodFormatingButContainingSymbols = "786231283aF9-76%D-7542-!'FD-SQDQ%*_°";
    assertAll(
        () -> assertFalse(Validator.isValidUUID(stringWithGoodFormatingButContainingSymbols)));
  }

  @Test
  void startWithNumbersAndEndWithLettersReturningFalseWhenHavingBadString() {
    String stringWithLetterFirstAndNumberAfter = "AAAAAA666666";
    String stringWithSymbols = "50.0%gr";
    String stringThatEndWithNumber = "500gr6";
    assertAll(
        () -> assertFalse(Validator.startWithNumbersAndEndWithLetters(
            stringWithLetterFirstAndNumberAfter)),
        () -> assertFalse(Validator.startWithNumbersAndEndWithLetters(
            stringWithSymbols)),
        () -> assertFalse(Validator.startWithNumbersAndEndWithLetters(
            stringThatEndWithNumber)));
  }

  @Test
  void isNumberCheckIfStringIsOnlyNumbers() {
    String stringWithLetters = "8982168219a69";
    String stringWithOnlyLetters = "DZGJAKGDKGDSL";
    String stringWithSymbols = "6/%*8271-8521";
    String stringWithLettersAndSymbols = "9769a£8652Z*";

    Exception e1 = assertThrows(IllegalArgumentException.class,
        () -> Validator.isNumber(stringWithLetters));
    Exception e2 = assertThrows(IllegalArgumentException.class,
        () -> Validator.isNumber(stringWithOnlyLetters));
    Exception e3 = assertThrows(IllegalArgumentException.class,
        () -> Validator.isNumber(stringWithSymbols));
    Exception e4 = assertThrows(IllegalArgumentException.class,
        () -> Validator.isNumber(stringWithLettersAndSymbols));

    assertAll(
        () -> assertEquals("ArgumentChecker Failed Not a number. ", e1.getMessage()),
        () -> assertEquals("ArgumentChecker Failed Not a number. ", e2.getMessage()),
        () -> assertEquals("ArgumentChecker Failed Not a number. ", e3.getMessage()),
        () -> assertEquals("ArgumentChecker Failed Not a number. ", e4.getMessage()));
  }
}
