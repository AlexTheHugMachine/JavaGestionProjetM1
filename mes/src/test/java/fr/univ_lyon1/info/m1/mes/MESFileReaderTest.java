package fr.univ_lyon1.info.m1.mes;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.hamcrest.text.MatchesPattern.matchesPattern;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.univ_lyon1.info.m1.mes.model.MESFileReader;

public class MESFileReaderTest {
  private String filePathToTested = "./src/public/data/";
  private ArrayList<String[]> listContent;

  @BeforeEach
  public void setUp()
      throws NullPointerException,
      NoSuchElementException,
      IllegalStateException, IOException {
    listContent = MESFileReader.readFile(this.filePathToTested + "PatientList.txt");
  }

  @Test
  public void readThePatientListFileInsideDataFolder() {
    assertEquals(listContent.size(), 20);
  }

  @Test
  public void checkThatPatientFileHasTheCorrectNumberOfElementOnEachLine() {
    for (String[] patientInfos : listContent) {
      assertEquals(3, patientInfos.length);
    }
  }

  @Test
  public void checkThatPatientFileHasTheCorrectTypeForEachElement() {
    for (String[] patientInfos : listContent) {
      assertAll(
        () -> assertThat(patientInfos[0], matchesPattern("[a-zA-Z-]+")),
        () -> assertThat(patientInfos[1], matchesPattern("[a-zA-Z-]+")),
        () -> assertThat(patientInfos[2], matchesPattern("[0-9]+")));
    }
  }
}
