package fr.univ_lyon1.info.m1.mes.utils;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileNotFoundException;
import java.util.Map;

import org.junit.jupiter.api.Test;

import fr.univ_lyon1.info.m1.mes.constants.Constants;


public class YMLFileHandlerTest {

  @Test
  void readToMapReturnTheExpectedPatientMapWithAllTheKeyValueExpected() {
    try {
      Map<String, Object> response = YmlFileHandler.readToMap(
          Constants.getPatientPath() + "1047233536435.yml");
      assertAll(
          () -> assertEquals("Sabah", (String) response.get("name")),
          () -> assertEquals("Swift", (String) response.get("surname")),
          () -> assertEquals("5657183210192", response.get("SSID").toString()),
          () -> assertEquals("32 rue Michel Ange", (String) response.get("adress")),
          () -> assertEquals("Le Havre", (String) response.get("city")));
    } catch (FileNotFoundException e) {
      fail("This file is existing so it should not throw this exception");
    }
  }

  @Test
  void readToMapThrowFileNotFoundGivenAnIncorrectPath() {
  }
}
