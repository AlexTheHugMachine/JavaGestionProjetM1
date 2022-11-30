package fr.univ_lyon1.info.m1.mes.model.HealthProfessional.operations;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.univ_lyon1.info.m1.mes.daos.PatientDAO;
import fr.univ_lyon1.info.m1.mes.daos.PrescriptionDAO;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.operations.HealthProfessionnalBusiness;
import fr.univ_lyon1.info.m1.mes.model.Patient.Patient;
import fr.univ_lyon1.info.m1.mes.model.Prescription.Prescription;

public class HealthProfessionalBusinessTest {
  private Patient john;
  private Patient jack;

  private Prescription doliprane500;
  private Prescription doliprane1000;
  private Prescription paracetamol;
  private Prescription eatFruit;

  private HealthProfessionnalBusiness hpBusiness;

  @BeforeEach
  void setUp() {
    PatientDAO patientDAO = new PatientDAO();
    PrescriptionDAO prescriptionDAO = new PrescriptionDAO();

    john = new Patient("John", "Wick", "6968686787598", "", "");
    jack = new Patient("Jack", "Sparrow", "0975310954209", "", "");

    doliprane500 = new Prescription("Doliprane", "500mg", "12345678901", "6968686787598");
    paracetamol = new Prescription("Paracetamol", "1000mg", "12345678901", "6968686787598");
    doliprane1000 = new Prescription("Doliprane", "1000mg", "12345678901", "0975310954209");
    eatFruit = new Prescription("Eat Fruit", "", "12345678901", "0975310954209");

    try {
      patientDAO.add(john);
      patientDAO.add(jack);
      prescriptionDAO.add(doliprane500);
      prescriptionDAO.add(paracetamol);
      prescriptionDAO.add(doliprane1000);
      prescriptionDAO.add(eatFruit);
    } catch (NameAlreadyBoundException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    }

    hpBusiness = new HealthProfessionnalBusiness(
        patientDAO,
        prescriptionDAO);
  }

  @Test
  void getPatientBySSIDReturnTheExpectedPatient() {

  }

  @Test
  void getPatientBySSIDThrowNameNotFoundWhenSSIDNotExistingInDAO() {

  }

  @Test
  void getPatientBySSIDThrowIllegalArgumentWhenSSIDEmptyOrNull() {

  }

  @Test
  void getPatientBySSIDThrowExceptionWhenSSIDIsLetters() {

  }

  @Test
  void getPatientBySSIDThrowExceptionWhenSSIDIsSymbols() {

  }

  // TODO : checker les cas
  @Test
  void getPrescriptionsPatientReturnAllTheExpectedPrescriptionsGivenToAPatient() {
    try {
      List<Prescription> actualList = hpBusiness.getPrescriptionsPatient(john.getSSID());

      List<Prescription> expectedList = new ArrayList<Prescription>();
      expectedList.add(doliprane500);
      expectedList.add(paracetamol);

      assertAll(
          () -> assertEquals(actualList.size(), expectedList.size()),
          () -> assertThat(expectedList,
              Matchers.containsInAnyOrder(actualList.toArray())));
    } catch (NameNotFoundException | IllegalArgumentException e) {
      fail("Should not throw this error because the patient with this SSID has Prescriptions.");
    }
  }

  @Test
  void getPrescriptionsPatientThrowNameNotfFoundWhenPatientSSIDIsNotDefinedInDAO() {
    Exception e = assertThrows(NameNotFoundException.class,
        () -> hpBusiness.getPrescriptionsPatient("0987654321234"));

    String expectedMessage = "No prescriptions found.";
    assertEquals(expectedMessage, e.getMessage());
  }

  @Test
  void getPrescriptionsPatientThrowNameNotFoundWhenWeReverseHpAndPatientId() {
    Exception exceptionNull = assertThrows(IllegalArgumentException.class,
        () -> hpBusiness.getPrescriptionsPatient(null));

    Exception exceptionEmpty = assertThrows(
        IllegalArgumentException.class,
        () -> hpBusiness.getPrescriptionsPatient(""));

    String actualMessageNull = exceptionNull.getMessage();
    String actualMessageEmpty = exceptionEmpty.getMessage();

    String expectedMessageNull = "ArgumentChecker Failed Null variable. ";
    String expectedMessageEmpty = "ArgumentChecker Failed Empty variable. ";

    assertAll(
        () -> assertEquals(expectedMessageEmpty, actualMessageEmpty),
        () -> assertEquals(expectedMessageNull, actualMessageNull));

  }

  @Test
  void getPrescriptionsPatientThrowIllegalArguementWhenPatientSSIDIsNullOrEmpty() {
  }
  // TODO : checker les cas

  @Test
  void getPrescriptionsGivenByHPReturnAllThePrescriptionsIGaveToThePatient() {
    try {
      String hpOfThePatient = "12345678901";
      List<Prescription> actualList = hpBusiness.getPrescriptionsGivenByHP(
          hpOfThePatient,
          jack.getSSID());

      List<Prescription> expectedList = new ArrayList<Prescription>();
      expectedList.add(doliprane1000);
      expectedList.add(eatFruit);

      assertAll(
          () -> assertEquals(actualList.size(), expectedList.size()),
          () -> assertThat(expectedList,
              Matchers.containsInAnyOrder(actualList.toArray())));
    } catch (NameNotFoundException e) {
      fail("Should not throw this error because the patient with this SSID has Prescriptions.");
    } catch (IllegalArgumentException e) {
      fail("Should not throw this error because the patient with this SSID has Prescriptions.");
    }
  }
}
