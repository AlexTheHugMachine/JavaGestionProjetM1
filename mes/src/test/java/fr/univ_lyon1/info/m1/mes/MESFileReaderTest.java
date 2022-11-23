package fr.univ_lyon1.info.m1.mes;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.hamcrest.text.MatchesPattern.matchesPattern;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.univ_lyon1.info.m1.mes.constants.Constants;
import fr.univ_lyon1.info.m1.mes.model.MESFileReader;

public class MESFileReaderTest {
  private String filePathToTested = Constants.getLocalPath();
  private ArrayList<String[]> listContent;

  @BeforeEach
  public void setUp()
      throws NullPointerException,
      NoSuchElementException,
      IllegalStateException, IOException {
    listContent = MESFileReader.readFile(this.filePathToTested + "PatientList.txt");
  }

  @Test
  @DisplayName("Tests if the lenght of the list equals to the amount of objects in the txt")
  public void readThePatientListFileInsideDataFolder() {

    assertEquals(listContent.size(), 20);
  }

  @Test
  @DisplayName("Tests if each lines of the txt have the right amount of parameters")
  public void checkThatPatientFileHasTheCorrectNumberOfElementOnEachLine() {
    for (String[] patientInfos : listContent) {
      assertEquals(3, patientInfos.length);
    }
  }

  @Test
  @DisplayName("Tests if each paramater in each line of the txt, have the right type")
  public void checkThatPatientFileHasTheCorrectTypeForEachElement() {
    for (String[] patientInfos : listContent) {
      assertAll(
        () -> assertThat(patientInfos[0], matchesPattern("[a-zA-Z-]+")),
        () -> assertThat(patientInfos[1], matchesPattern("[a-zA-Z-]+")),
        () -> assertThat(patientInfos[2], matchesPattern("[0-9]+")));
    }
  }
}
