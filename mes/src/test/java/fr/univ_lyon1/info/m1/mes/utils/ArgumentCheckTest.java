package fr.univ_lyon1.info.m1.mes.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ArgumentCheckTest {

  private List<String> testStringList;
  private String testString;

  @BeforeEach
  void setUp() {
    testStringList = new ArrayList<String>();
  }

  @Test
  void checkStringListThrowIllegalArgumentWhenListIsNull() {
    testStringList = null;
    final Exception exception = assertThrows(
        IllegalArgumentException.class,
        () -> ArgumentChecker.checkStringListNotNullOrEmpty(testStringList));

    final String actualMessage = exception.getMessage();
    final String expectedMessage = "ArgumentChecker Failed Null list. ";

    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void checkStringListThrowIllegalArgumentWhenListIsEmpty() {
    final Exception exception = assertThrows(
        IllegalArgumentException.class,
        () -> ArgumentChecker.checkStringListNotNullOrEmpty(testStringList));

    final String actualMessage = exception.getMessage();
    final String expectedMessage = "ArgumentChecker Failed Empty list. ";

    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void checkStringListDontThrowIllegalArgumentGivenNorNullOrEmptyList() {
    try {
      testStringList.add("Hello");
      testStringList.add("World");
      ArgumentChecker.checkStringListNotNullOrEmpty(testStringList);
    } catch (Exception e) {
      fail("Failed with exception: " + e);
    }
  }

  @Test
  void checkManyStringThrowIllegalArgumentWhenHavingNullElement() {
    testStringList.add("Test");
    testStringList.add(null);
    testStringList.add("Enzo");
    testStringList.add("World");

    final Exception exception = assertThrows(
        IllegalArgumentException.class,
        () -> ArgumentChecker.checkManyStringNotNullOrEmpty(testStringList));

    final String actualMessage = exception.getMessage();
    final String expectedMessage = "ArgumentChecker Failed Null element. ";

    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void checkManyStringThrowIllegalArgumentWhenHavingEmptyElement() {
    testStringList.add("Test");
    testStringList.add("");
    testStringList.add("Enzo");
    testStringList.add("World");

    final Exception exception = assertThrows(
        IllegalArgumentException.class,
        () -> ArgumentChecker.checkManyStringNotNullOrEmpty(testStringList));

    final String actualMessage = exception.getMessage();
    final String expectedMessage = "ArgumentChecker Failed Empty element. ";

    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void checkManyStringDontThrowExceptionWhenGivenARightStringList() {
    try {
      testStringList.add("Hello");
      testStringList.add("World");
      testStringList.add("Enzo");
      testStringList.add("CECILLON");
      ArgumentChecker.checkManyStringNotNullOrEmpty(testStringList);
    } catch (Exception e) {
      fail("Failed with exception " + e.getMessage());
    }
  }

  @Test
  void checkStringThrowIllegalArgumentnWhenVariableNull() {
    final Exception exception = assertThrows(
        IllegalArgumentException.class,
        () -> ArgumentChecker.checkStringNotNullOrEmpty(testString));

    final String actualMessage = exception.getMessage();
    final String expectedMessage = "ArgumentChecker Failed Null variable. ";

    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void checkStringThrowIllegalArgumentnWhenVariableIsEmpty() {
    testString = "";
    final Exception exception = assertThrows(
        IllegalArgumentException.class,
        () -> ArgumentChecker.checkStringNotNullOrEmpty(testString));

    final String actualMessage = exception.getMessage();
    final String expectedMessage = "ArgumentChecker Failed Empty variable. ";

    assertEquals(expectedMessage, actualMessage);
  }

}
