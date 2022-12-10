package fr.univ_lyon1.info.m1.mes.model.Prescription;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HPSpeciality;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HealthProfessional;

public class PrescriptionTest {

  private Prescription prescription;

  @BeforeEach
  void setUp() {
    this.prescription = new Prescription(
        "Doliprane",
        "500g",
        "20123417965",
        "8689621985291");
  }

  @Test
  void properlySetIdOfPrescription() {
    this.prescription.setId("1");
    assertEquals("1", this.prescription.getId());
  }

  @Test
  @DisplayName("Check Prescription Getters")
  void properlyGetAllThePrescriptionInformations() {
    assertAll(
        () -> assertEquals("Doliprane", this.prescription.getContent()),
        () -> assertEquals("500g", this.prescription.getQuantite()),
        () -> assertEquals("8689621985291", this.prescription.getIdPatient()),
        () -> assertEquals("20123417965", this.prescription.getIdHealthProfessional()));
  }

  @Test
  public void checkThatAnHPIdGivenToAPrescriptionMatchAnExistingHP() {
    // Given
    HealthProfessional hp = new HealthProfessional(
        "Robert",
        "BARATHEON",
        "12742074123",
        HPSpeciality.GENERALISTE);
    Prescription p = new Prescription("Doliprane", "500gr", "12742074123", "1234567890987");
    // When
    String idHealthProfessionnal = hp.getRPPS();
    // Then
    assertEquals(idHealthProfessionnal, p.getIdHealthProfessional());
  }

  @Test
  void constructThrowExceptionWhenCreatingPrescriptionWithBadHPId() {
    final Exception e = assertThrows(IllegalArgumentException.class,
        () -> new Prescription("Doliprane", "500mg", "123456789098", "0123456789098"));

    final String expectedMessage = "Les informations de Prescription sont invalides.";
    final String actualMessage = e.getMessage();
    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void constructThrowExceptionWhenCreatingPrescriptionWithBadPatientID() {
    final Exception e = assertThrows(IllegalArgumentException.class,
        () -> new Prescription("Doliprane", "500mg", "23456789098", "01234567890"));

    final String expectedMessage = "Les informations de Prescription sont invalides.";
    final String actualMessage = e.getMessage();
    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void constructThrowExceptionWhenContentDoNotContainLetter() {
    String withNumber = "D0liprane";
    String withSymbol = "D%P:v 76`";
    assertAll(
        () -> assertThrows(IllegalArgumentException.class,
            () -> new Prescription(
                withNumber,
                "%¨%¨",
                "12345678909",
                "1234567890987")),
        () -> assertThrows(
            IllegalArgumentException.class,
            () -> new Prescription(
                withSymbol,
                "%¨%¨",
                "12345678909",
                "1234567890987")));
  }

  @Test
  void constructThrowExceptionWhenContentIsNullOrEmpty() {
    Exception prescriptionNull = assertThrows(IllegalArgumentException.class,
        () -> new Prescription(null,
            "500g",
            "20123417965",
            "8689621985291"));

    Exception prescriptionEmpty = assertThrows(IllegalArgumentException.class,
        () -> new Prescription("",
            "500g",
            "20123417965",
            "8689621985291"));

    String actualMessageNull = prescriptionNull.getMessage();
    String actualMessageEmpty = prescriptionEmpty.getMessage();

    String argumentCheckerMessageNull = "ArgumentChecker Failed Null element. ";
    String argumentCheckerMessageEmpty = "ArgumentChecker Failed Empty element. ";
    String expectedMessageNull =
    argumentCheckerMessageNull + "Les informations de Prescription sont invalides.";
    String expectedMessageEmpty =
    argumentCheckerMessageEmpty + "Les informations de Prescription sont invalides.";

    assertAll(
        () -> assertEquals(expectedMessageEmpty, actualMessageEmpty),
        () -> assertEquals(expectedMessageNull, actualMessageNull));

  }

  @Test
  void constructThrowExceptionWhenIdHPIsNullOrEmpty() {
    Exception prescriptionNull = assertThrows(IllegalArgumentException.class,
        () -> new Prescription("Doliprane",
            "500g",
            null,
            "8689621985291"));

    Exception prescriptionEmpty = assertThrows(IllegalArgumentException.class,
        () -> new Prescription("Doliprane",
            "500g",
            "",
            "8689621985291"));

    String actualMessageNull = prescriptionNull.getMessage();
    String actualMessageEmpty = prescriptionEmpty.getMessage();

    String argumentCheckerMessageNull = "ArgumentChecker Failed Null element. ";
    String argumentCheckerMessageEmpty = "ArgumentChecker Failed Empty element. ";
    String expectedMessageNull =
        argumentCheckerMessageNull + "Les informations de Prescription sont invalides.";
    String expectedMessageEmpty =
        argumentCheckerMessageEmpty + "Les informations de Prescription sont invalides.";

    assertAll(
      () -> assertEquals(expectedMessageEmpty, actualMessageEmpty),
      () -> assertEquals(expectedMessageNull, actualMessageNull));
  }

  @Test
  void constructThrowExceptionWhenIdPatientIsNullOrEmpty() {
    Exception prescriptionNull = assertThrows(IllegalArgumentException.class,
        () -> new Prescription("Doliprane",
            "500g",
            "20123417965",
            null));

    Exception prescriptionEmpty = assertThrows(IllegalArgumentException.class,
        () -> new Prescription("Doliprane",
            "500g",
            "20123417965",
            ""));

    String actualMessageNull = prescriptionNull.getMessage();
    String actualMessageEmpty = prescriptionEmpty.getMessage();

    String argumentCheckerMessageNull = "ArgumentChecker Failed Null element. ";
    String argumentCheckerMessageEmpty = "ArgumentChecker Failed Empty element. ";
    String expectedMessageNull =
    argumentCheckerMessageNull + "Les informations de Prescription sont invalides.";
    String expectedMessageEmpty =
     argumentCheckerMessageEmpty + "Les informations de Prescription sont invalides.";

    assertAll(
        () -> assertEquals(expectedMessageEmpty, actualMessageEmpty),
        () -> assertEquals(expectedMessageNull, actualMessageNull));
  }

  @Test
  @DisplayName("Check PrescriptionUID is nor null, empty and match cryptographic pattern")
  void checkThatUIDIsProperlySetAndValid() {
    assertAll(
        () -> assertNotNull(prescription.getId()),
        () -> assertNotEquals("", prescription.getId()),
        () -> assertEquals(
            prescription
                .getId()
                .matches(
"^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$"),
            true));
  }
}
