package fr.univ_lyon1.info.m1.mes.utils;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import fr.univ_lyon1.info.m1.mes.constants.Constants;

public class FileHandlerTest {

  @AfterAll
  static void unmount() {
    try {
      ExecuteScript.execute(
          Constants.getDataTestPath(),
          "./removeTestFile.sh");
    } catch (IOException | InterruptedException | ExecutionException e) {
      fail("The execution of the script failed unexpectedly.");
    }
  }

  @Test
  void writeContentToFileCorrectly() {
    try {
      FileHandler.writeContentToFile(
          "Hello world",
          Constants.getDataTestPath(),
          "test.yml");
    } catch (IOException e) {
      fail("Something went wrong during write.");
    }
  }

  @Test
  void writeContentWithLineReturn() {
    String content = String.format(
        "%n Error Message : %s %n ====== Trace ====== %n %s", "Hello world", "Mr Kenobi");
    try {
      FileHandler.writeContentToFile(
          content,
          Constants
              .getDataTestPath(),
          "test.yml");
    } catch (IOException e) {
      fail("Something went wrong during write.");
    }
  }

  @Test
  void writeContentThrowAnErrorWhenTryingToDuplicateFile() {
    String content = String.format(
        "%n Error Message : %s %n ====== Trace ====== %n %s", "Hello world", "Mr Kenobi");
    try {
      FileHandler.writeContentToFile(
          content,
          Constants
              .getDataTestPath(),
          "test.yml");
    } catch (IOException e) {
      fail("Something went wrong during write.");
    }
  }
}
