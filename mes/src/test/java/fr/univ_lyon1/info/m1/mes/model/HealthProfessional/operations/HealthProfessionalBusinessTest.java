package fr.univ_lyon1.info.m1.mes.model.HealthProfessional.operations;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import javax.naming.InvalidNameException;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.univ_lyon1.info.m1.mes.daos.HealthProfessionalDAO;
import fr.univ_lyon1.info.m1.mes.daos.PatientDAO;
import fr.univ_lyon1.info.m1.mes.daos.PrescriptionDAO;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HPSpeciality;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HealthProfessional;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.operations.HealthProfessionnalBusiness;
import fr.univ_lyon1.info.m1.mes.model.Patient.Patient;
import fr.univ_lyon1.info.m1.mes.model.Prescription.Prescription;

public class HealthProfessionalBusinessTest {
  private Patient john;
  private Patient jack;

  private HealthProfessional lebron;

  private Prescription doliprane500;
  private Prescription doliprane1000;
  private Prescription paracetamol;
  private Prescription eatFruit;

  private HealthProfessionnalBusiness hpBusiness;

  @BeforeEach
  void setUp() {
    PatientDAO patientDAO = new PatientDAO();
    PrescriptionDAO prescriptionDAO = new PrescriptionDAO();
    HealthProfessionalDAO hpDAO = new HealthProfessionalDAO();

    john = new Patient("John", "Wick", "6968686787598", "", "");
    jack = new Patient("Jack", "Sparrow", "0975310954209", "", "");
    lebron = new HealthProfessional("Lebron", "James", "12345678919", HPSpeciality.CHIRURGIEN);

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
      hpDAO.add(lebron);
    } catch (NameAlreadyBoundException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    }

    hpBusiness = new HealthProfessionnalBusiness(
        patientDAO,
        prescriptionDAO,
        hpDAO);
  }

  @Test
  void getPatientBySSIDReturnTheExpectedPatient() throws InvalidNameException {
    try {
      Patient patient = hpBusiness.getPatientBySSID("6968686787598");
      assertAll(
          () -> assertEquals("John", patient.getName()),
          () -> assertEquals("Wick", patient.getSurname()),
          () -> assertEquals("6968686787598", patient.getSSID())
      );
    } catch (NameNotFoundException e) {
      fail("Patient not found");
    }
  }

  @Test
  void getPatientBySSIDThrowNameNotFoundWhenSSIDNotExistingInDAO() {
    assertThrows(NameNotFoundException.class, () -> {
      hpBusiness.getPatientBySSID("1234567890123");
    });
  }

  @Test
  void getPatientBySSIDThrowIllegalArgumentWhenSSIDEmptyOrNull() {
    assertThrows(IllegalArgumentException.class, () -> {
      hpBusiness.getPatientBySSID("");
    });
    assertThrows(IllegalArgumentException.class, () -> {
      hpBusiness.getPatientBySSID(null);
    });
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
    assertThrows(IllegalArgumentException.class, () -> {
      hpBusiness.getPrescriptionsPatient("");
    });
    assertThrows(IllegalArgumentException.class, () -> {
      hpBusiness.getPrescriptionsPatient(null);
    });
  }

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

  @Test
  void getPrescriptionsGivenByHPThrowNameNotFoundWhenPatientSSIDIsNotDefinedInDAO() {
    Exception e = assertThrows(NameNotFoundException.class,
        () -> hpBusiness.getPrescriptionsGivenByHP("12345678901", "0987654321234"));

    String expectedMessage = "No prescriptions found.";
    assertEquals(expectedMessage, e.getMessage());
  }

  @Test
  void getPrescriptionsGivenByHPThrowNameNotFoundWhenWeReverseHpAndPatientId() {
    Exception exceptionNull = assertThrows(IllegalArgumentException.class,
        () -> hpBusiness.getPrescriptionsGivenByHP(null, "0975310954209"));

    Exception exceptionEmpty = assertThrows(
        IllegalArgumentException.class,
        () -> hpBusiness.getPrescriptionsGivenByHP("", "0975310954209"));

    String actualMessageNull = exceptionNull.getMessage();
    String actualMessageEmpty = exceptionEmpty.getMessage();

    String expectedMessageNull = "ArgumentChecker Failed Null element. ";
    String expectedMessageEmpty = "ArgumentChecker Failed Empty element. ";

    assertAll(
        () -> assertEquals(expectedMessageEmpty, actualMessageEmpty),
        () -> assertEquals(expectedMessageNull, actualMessageNull));
  }

  @Test
  void getPrescriptionsGivenByHPThrowIllegalArguementWhenPatientSSIDIsNullOrEmpty() {
    assertThrows(IllegalArgumentException.class, () -> {
      hpBusiness.getPrescriptionsGivenByHP("", "0975310954209");
    });
    assertThrows(IllegalArgumentException.class, () -> {
      hpBusiness.getPrescriptionsGivenByHP(null, "0975310954209");
    });
  }

  @Test
  void getPatientInfosProperlyReturnThePatientInfos() throws InvalidNameException {
    try {
      Patient patientInfos = hpBusiness.getPatientInfos(john.getSSID());

      assertAll(
          () -> assertEquals("John", patientInfos.getName()),
          () -> assertEquals("Wick", patientInfos.getSurname()),
          () -> assertEquals("6968686787598", patientInfos.getSSID()),
          () -> assertEquals("", patientInfos.getAdress()),
          () -> assertEquals("", patientInfos.getCity()));
    } catch (NameNotFoundException e) {
      fail("Should not throw this error because the patient with this SSID has Prescriptions.");
    } catch (IllegalArgumentException e) {
      fail("Should not throw this error because the patient with this SSID has Prescriptions.");
    }
  }

  @Test
  void getPatientInfosThrowNameNotFoundWhenPatientSSIDIsNotDefinedInDAO() {
    Exception e = assertThrows(NameNotFoundException.class,
        () -> hpBusiness.getPatientInfos("0987654321234"));

    String expectedMessage = "No patient found.";
    assertEquals(expectedMessage, e.getMessage());
  }

  @Test
  void getPatientInfosThrowNameNotFoundWhenWeReverseHpAndPatientId() {
    Exception exceptionNull = assertThrows(IllegalArgumentException.class,
        () -> hpBusiness.getPatientInfos(null));

    Exception exceptionEmpty = assertThrows(
        IllegalArgumentException.class,
        () -> hpBusiness.getPatientInfos(""));

    String actualMessageNull = exceptionNull.getMessage();
    String actualMessageEmpty = exceptionEmpty.getMessage();

    String expectedMessageNull = "ArgumentChecker Failed Null variable. ";
    String expectedMessageEmpty = "ArgumentChecker Failed Empty variable. ";

    assertAll(
        () -> assertEquals(expectedMessageEmpty, actualMessageEmpty),
        () -> assertEquals(expectedMessageNull, actualMessageNull));
  }

  @Test
  void getPatientInfosThrowIllegalArguementWhenPatientSSIDIsNullOrEmpty() {
    assertThrows(IllegalArgumentException.class, () -> {
      hpBusiness.getPatientInfos("");
    });
    assertThrows(IllegalArgumentException.class, () -> {
      hpBusiness.getPatientInfos(null);
    });
  }

  //================================================================================================

  @Test
  void removePrescriptionProperlyRemoveThePrescription() throws InvalidNameException {
    try {
      hpBusiness.removePrescription(doliprane1000.getId());
      hpBusiness.removePrescription(eatFruit.getId());
      hpBusiness.removePrescription(doliprane500.getId());
      hpBusiness.removePrescription(paracetamol.getId());
    } catch (NameNotFoundException e) {
      fail("Should not throw this error because the patient with this SSID has Prescriptions.");
    } catch (IllegalArgumentException e) {
      fail("Should not throw this error because the patient with this SSID has Prescriptions.");
    }
  }

  @Test
  void removePrescriptionThrowIllegalArguementWhenPrescriptionSSIDIsNullOrEmpty() {
    assertThrows(IllegalArgumentException.class, () -> {
      hpBusiness.removePrescription("");
    });
    assertThrows(IllegalArgumentException.class, () -> {
      hpBusiness.removePrescription(null);
    });
  }

  @Test
  void removePrescriptionThrowNameNotFoundWhenPrescriptionIdIsNotDefinedInDAO() {
    Exception e = assertThrows(NameNotFoundException.class,
        () -> hpBusiness.removePrescription("123456789"));

    String expectedMessage = "No prescription found.";
    assertEquals(expectedMessage, e.getMessage());
  }

  @Test
  void addPrescriptionProperlyAddThePrescription() 
    throws InvalidNameException, NameAlreadyBoundException {
      Prescription doliprane1500 = new Prescription(
          "Doliprane", "1500mg", "22225678901", "6968686787598");
    try {
      hpBusiness.addprescription(doliprane1500);
    } catch (NameNotFoundException e) {
      fail("Should not throw this error because the patient with this SSID has Prescriptions.");
    } catch (IllegalArgumentException e) {
      fail("Should not throw this error because the patient with this SSID has Prescriptions.");
    }
  }

  @Test
  void addPrescriptionThrowNameNotFoundWhenPatientSSIDIsNotDefinedInDAO() {
    Prescription doliprane1500 = new Prescription(
          "Doliprane", "1500mg", "22225678901", "6968686789999");
    Exception e = assertThrows(NameNotFoundException.class,
        () -> hpBusiness.addprescription(doliprane1500));

    String expectedMessage = "No patient or health professional found.";
    assertEquals(expectedMessage, e.getMessage());
  }

  /*@Test
  void addPrescriptionThrowIllegalArguementWhenPrescriptionSSIDIsNullOrEmpty() {
    assertThrows(IllegalArgumentException.class, () -> {
      hpBusiness.addprescription(null);
    });
  }*/

  /* 
  @Test
  void addPrescriptionThrowNameNotFoundWhenPatientSSIDIsNotDefinedInDAO() {
    Exception e = assertThrows(NameNotFoundException.class,
        () -> hpBusiness.addprescription("0987654321234", doliprane1000));

    String expectedMessage = "No patient found.";
    assertEquals(expectedMessage, e.getMessage());
  }

  @Test
  void addPrescriptionThrowNameNotFoundWhenWeReverseHpAndPatientId() {
    Exception exceptionNull = assertThrows(IllegalArgumentException.class,
        () -> hpBusiness.addprescription(null, doliprane1000));

    Exception exceptionEmpty = assertThrows(
        IllegalArgumentException.class,
        () -> hpBusiness.addprescription("", doliprane1000));

    String actualMessageNull = exceptionNull.getMessage();
    String actualMessageEmpty = exceptionEmpty.getMessage();

    String expectedMessageNull = "ArgumentChecker Failed Null element. ";
    String expectedMessageEmpty = "ArgumentChecker Failed Empty element. ";

    assertAll(
        () -> assertEquals(expectedMessageEmpty, actualMessageEmpty),
        () -> assertEquals(expectedMessageNull, actualMessageNull));
  }

  @Test
  void addPrescriptionThrowIllegalArguementWhenPatientSSIDIsNullOrEmpty() {
    assertThrows(IllegalArgumentException.class, () -> {
      hpBusiness.addprescription("", doliprane1000);
    });
    assertThrows(IllegalArgumentException.class, () -> {
      hpBusiness.addprescription(null, doliprane1000);
    });
  }

  @Test
  void addPrescriptionThrowNameNotFoundWhenPrescriptionIdIsNotDefinedInDAO() {
    Exception e = assertThrows(NameNotFoundException.class,
        () -> hpBusiness.addprescription(john.getSSID(), null));

    String expectedMessage = "No prescription found.";
    assertEquals(expectedMessage, e.getMessage());
  }

  @Test
  void addPrescriptionThrowNameNotFoundWhenWeReverseHpAndPrescriptionId() {
    Exception exceptionNull = assertThrows(IllegalArgumentException.class,
        () -> hpBusiness.addprescription(john.getSSID(), null));

    Exception exceptionEmpty = assertThrows(
        IllegalArgumentException.class,
        () -> hpBusiness.addprescription(john.getSSID(), null));

    String actualMessageNull = exceptionNull.getMessage();
    String actualMessageEmpty = exceptionEmpty.getMessage();

    String expectedMessageNull = "ArgumentChecker Failed Null element. ";
    String expectedMessageEmpty = "ArgumentChecker Failed Empty element. ";

    assertAll(
        () -> assertEquals(expectedMessageEmpty, actualMessageEmpty),
        () -> assertEquals(expectedMessageNull, actualMessageNull));
  }

  @Test
  void addPrescriptionThrowIllegalArguementWhenPrescriptionIdIsNullOrEmpty() {
    assertThrows(IllegalArgumentException.class, () -> {
      hpBusiness.addprescription(john.getSSID(), null);
    });
    assertThrows(IllegalArgumentException.class, () -> {
      hpBusiness.addprescription(john.getSSID(), null);
    });
  }*/

  //======================

  @Test
  void createPatientProperlyCreateThePatient() {
    try {
      Patient joseph = new Patient("Joseph", "Allen", "0111110114201", "", "");
      hpBusiness.createPatient(joseph);
      try {
        Patient patient = hpBusiness.getPatientBySSID("0111110114201");
        assertEquals(joseph.getSSID(), patient.getSSID());
      } catch (NameNotFoundException | InvalidNameException e) {
        fail("Should not throw this error because the patient with this SSID has been created.");
      }
    } catch (IllegalArgumentException e) {
      fail("Should not throw this error because the patient with this SSID has Prescriptions.");
    } catch (NameAlreadyBoundException e) {
      fail("Should not throw this error because the patient with this SSID has Prescriptions.");
    }
  }

  @Test
  void createPatientThrowNameAlreadyBoundWhenPatientSSIDIsAlreadyDefinedInDAO() {
    Exception e = assertThrows(NameAlreadyBoundException.class,
        () -> hpBusiness.createPatient(john));

    String expectedMessage = "Patient already exists.";
    assertEquals(expectedMessage, e.getMessage());
  }

  @Test
  void createPatientThrowIllegalArguementWhenPatientIsNotValid() {
    assertThrows(IllegalArgumentException.class, () -> {
      hpBusiness.createPatient(new Patient("", "", "", "", ""));
    });
  }
}
