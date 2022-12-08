package fr.univ_lyon1.info.m1.mes.model.HealthProfessional.operations;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
  private Prescription doSomeSports;

  private HealthProfessionnalBusiness hpBusiness;

  @BeforeEach
  void setUp() {
    PatientDAO patientDAO = new PatientDAO();
    PrescriptionDAO prescriptionDAO = new PrescriptionDAO();
    HealthProfessionalDAO hpDAO = new HealthProfessionalDAO();

    john = new Patient("John", "Wick", "6968686787598", "", "");
    jack = new Patient("Jack", "Sparrow", "0975310954209", "", "");
    lebron = new HealthProfessional("Lebron", "James", "12345678919", HPSpeciality.CHIRURGIEN);

    doliprane500 = new Prescription("Doliprane", "500mg", "12345678919", "0975310954209");
    paracetamol = new Prescription("Paracetamol", "1000mg", "12345678901", "0975310954209");
    doliprane1000 = new Prescription("Doliprane", "1000mg", "12345678919", "0975310954209");
    eatFruit = new Prescription("Eat Fruit", "", "12345678919", "6968686787598");
    doSomeSports = new Prescription(
        "Do some sports",
        "2 days a week",
        "12345678919",
        "6968686787598");

    try {
      patientDAO.add(john);
      patientDAO.add(jack);
      prescriptionDAO.add(doliprane500);
      prescriptionDAO.add(paracetamol);
      prescriptionDAO.add(doliprane1000);
      prescriptionDAO.add(eatFruit);
      prescriptionDAO.add(doSomeSports);
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
          () -> assertEquals("6968686787598", patient.getSsID()));
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

  @Test
  void getPrescriptionsPatientReturnAllTheExpectedPrescriptionsGivenToAPatient() {
    try {
      List<Prescription> actualList = hpBusiness.getPrescriptionsPatient(john.getSsID());

      List<Prescription> expectedList = new ArrayList<Prescription>();
      expectedList.add(doSomeSports);
      expectedList.add(eatFruit);

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
  void getPrescriptionsPatientThrowIllegalArgumentWhenIdIsNullOrEmpty() {
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
  void getPrescriptionsGivenByHPReturnAllThePrescriptionsGivenToAPatient() {
    try {
      String hpOfThePatient = "12345678919";
      List<Prescription> actualList = hpBusiness.getPrescriptionsGivenByHP(
          hpOfThePatient,
          jack.getSsID());

      List<Prescription> expectedList = new ArrayList<Prescription>();
      expectedList.add(doliprane1000);
      expectedList.add(doliprane500);

      assertAll(
          () -> assertEquals(expectedList.size(), actualList.size()),
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
  void getPrescriptionsGivenByHPThrowIllegalArgumentWhenIdOfHPIsNullOrEmpty() {
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
      hpBusiness.getPrescriptionsGivenByHP("12345678901", null);
    });
    assertThrows(IllegalArgumentException.class, () -> {
      hpBusiness.getPrescriptionsGivenByHP("12345678901", "");
    });
  }

  @Test
  void removePrescriptionProperly() throws InvalidNameException {
    try {
      hpBusiness.removePrescription(doliprane1000.getId());
      hpBusiness.removePrescription(eatFruit.getId());
      hpBusiness.removePrescription(doliprane500.getId());
      hpBusiness.removePrescription(paracetamol.getId());
    } catch (NameNotFoundException e) {
      fail("Should not throw this error because the prescriptions was added in the DAO.");
    } catch (IllegalArgumentException e) {
      fail("Should not throw this error because the id of the prescriptions are not null or empty");
    }
  }

  @Test
  void removePrescriptionThrowIllegalArguementWhenPrescriptionIdIsNullOrEmpty() {
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
  void addPrescriptionReturnTrueWhenCompleted()
      throws InvalidNameException, NameAlreadyBoundException {
    Prescription doliprane1500 = new Prescription(
        "Doliprane", "1500mg", "12345678919", "6968686787598");
    try {
      assertTrue(hpBusiness.addPrescription(doliprane1500));
    } catch (NameNotFoundException e) {
      fail("Should not throw the following exception: " + e.getMessage());
    } catch (IllegalArgumentException e) {
      fail("Should not throw this error because the prescription has correct args.");
    }
  }

  @Test
  void addPrescriptionThrowNameNotFoundWhenPatientSSIDIsNotDefinedInDAO() {
    Prescription doliprane1500 = new Prescription(
        "Doliprane", "1500mg", "12345678919", "6968686789999");
    Exception e = assertThrows(NameNotFoundException.class,
        () -> hpBusiness.addPrescription(doliprane1500));

    String expectedMessage = "No patient or health professional found.";
    assertEquals(expectedMessage, e.getMessage());
  }

  @Test
  void addPrescriptionThrowNameAlreadyBoundWhenPrescriptionAlreadyExist() {
    Exception e = assertThrows(NameAlreadyBoundException.class,
        () -> hpBusiness.addPrescription(doliprane1000));

    String expectedMessage = "Prescription already exists.";
    assertEquals(expectedMessage, e.getMessage());
  }

  @Test
  void createPatientProperlyCreateThePatient() {
    try {
      Patient joseph = new Patient("Joseph", "Stalin", "0111110114201", "", "");
      assertTrue(hpBusiness.createPatient(joseph));
      // On check pas si ce dernier a été créer car ce n'est pas dans le scope du
      // test.
    } catch (IllegalArgumentException e) {
      fail("Should not throw this error because the args are valid");
    } catch (NameAlreadyBoundException e) {
      fail("Should not throw this error because Stalin is unique.");
    }
  }

  @Test
  void createPatientThrowNameAlreadyBoundWhenPatientSSIDIsAlreadyDefinedInDAO() {
    Exception e = assertThrows(NameAlreadyBoundException.class,
        () -> hpBusiness.createPatient(john));

    String expectedMessage = "Patient already exists.";
    assertEquals(expectedMessage, e.getMessage());
  }
}
