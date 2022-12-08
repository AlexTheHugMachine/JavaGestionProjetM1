package fr.univ_lyon1.info.m1.mes.utils;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.univ_lyon1.info.m1.mes.dto.patient.PatientRequestDto;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HPSpeciality;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HealthProfessional;
import fr.univ_lyon1.info.m1.mes.model.Patient.Patient;
import fr.univ_lyon1.info.m1.mes.model.Prescription.Prescription;

public class YMLFileHandlerTest {

  private String testPath;

  @BeforeAll
  static void setup() {
    // On créer les fichiers qui serviront pour les tests.
    String testPath = "./src/test/java/fr/univ_lyon1/info/m1/mes/data/";
    try {
      // Very expensive so avoid this type of solution.
      ExecuteScript.execute(testPath, "createTestFile.sh");
    } catch (IOException | InterruptedException | ExecutionException e) {
      fail("Something went wrong during the execution : " + e.getMessage());
    }
  }

  @AfterAll
  static void unmount() {
    // On supprime les fichiers de test.
    String testPath = "./src/test/java/fr/univ_lyon1/info/m1/mes/data/";
    try {
      ExecuteScript.execute(testPath, "removeTestFile.sh");
    } catch (IOException | InterruptedException | ExecutionException e) {
      fail("Something went wrong during the execution : " + e.getMessage());
    }
  }

  @BeforeEach
  void construct() {
    testPath = "./src/test/java/fr/univ_lyon1/info/m1/mes/data/";
  }

  @Test
  void readToCustomTypeReturnExpectedPatientWithCorrectInfos() {
    try {
      String path = testPath + "Patient.yml";
      PatientRequestDto data = (PatientRequestDto) YmlFileHandler.readToCustomType(
          path,
          PatientRequestDto.class);
      assertAll(
          () -> assertEquals("Sabah", data.getName()),
          () -> assertEquals("Swift", data.getSurname()),
          () -> assertEquals("5657183210192", data.getSsID()),
          () -> assertEquals("32 rue Michel Ange", data.getAdress()),
          () -> assertEquals("Le Havre", data.getCity()));
    } catch (FileNotFoundException e) {
      fail("This file is existing so it should not throw this exception");
    }
  }

  @Test
  void readToCustomTypeReturnAnHealthProfessionalWithCorrespondingInfo() {
    try {
      String path = testPath + "HP.yml";
      HealthProfessional data = (HealthProfessional) YmlFileHandler
          .readToCustomType(
              path,
              HealthProfessional.class);
      assertAll(
          () -> assertEquals("Farah", data.getName()),
          () -> assertEquals("Ryan", data.getSurname()),
          () -> assertEquals("42766270552", data.getRPPS()),
          () -> assertEquals(HPSpeciality.GENERALISTE, data.getSpeciality()));
    } catch (FileNotFoundException e) {
      fail("This file is existing so it should not throw this exception");
    }
  }

  @Test
  void readToCustomTypeReturnPrescriptionDtoWithExpectedInfo() {
    try {
      String path = testPath + "Prescription.yml";
      Prescription data = (Prescription) YmlFileHandler
          .readToCustomType(
              path,
              Prescription.class);
      assertAll(
          () -> assertEquals("Sancturelin", data.getContent()),
          () -> assertEquals("200g", data.getQuantite()),
          () -> assertEquals("60849182633", data.getIdHealthProfessional()),
          () -> assertEquals("3921322516464", data.getIdPatient()));
    } catch (FileNotFoundException e) {
      fail("This file is existing so it should not throw this exception");
    }
  }

  @Test
  void readToMapThrowFileNotFoundGivenAnIncorrectPath() {
    try {
      YmlFileHandler.readToCustomType(
          "hello world" + "1047233536435.yml", PatientRequestDto.class);
    } catch (FileNotFoundException e) {
      assertTrue(true, "Correctly throw the expected exception when path is incorrect");
    }
  }

  @Test
  void writeObjectToYML() {
    String path = testPath + "testWritePatient.yml";
    Patient patientToExport = new Patient(
        "Enzo",
        "CECILLON",
        "1234567890987",
        "",
        "");

    try {
      YmlFileHandler.writeObjectToYML(
          path,
          patientToExport);
      // Not necessary but useful to check if it put the right infos.
      PatientRequestDto data = (PatientRequestDto) YmlFileHandler
          .readToCustomType(
              path,
              PatientRequestDto.class);
      assertAll(
          () -> assertEquals("Enzo", data.getName()),
          () -> assertEquals("CECILLON", data.getSurname()),
          () -> assertEquals("1234567890987", data.getSsID()),
          () -> assertEquals("", data.getAdress()),
          () -> assertEquals("", data.getCity()));
    } catch (FileNotFoundException | IllegalArgumentException e) {
      fail("Should not throw this error because Object and path are correct");
    }
  }

  @Test
  void writeObjectToYMLThrowExceptionBecauseObjectIsNull() {
    String path = testPath + "testWritePatient.yml";
    Patient patientToExport = null;

    try {
      YmlFileHandler.writeObjectToYML(
          path,
          patientToExport);
      fail("Object provided is null so it should not continue.");
    } catch (FileNotFoundException | IllegalArgumentException e) {
      assertTrue(true, "Correctly throw error when object is null.");
    }
  }
}
