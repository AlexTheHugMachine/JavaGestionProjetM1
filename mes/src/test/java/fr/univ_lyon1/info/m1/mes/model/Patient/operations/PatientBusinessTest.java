package fr.univ_lyon1.info.m1.mes.model.Patient.operations;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;

import java.util.ArrayList;
import java.util.List;

import javax.naming.InvalidNameException;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.univ_lyon1.info.m1.mes.daos.PatientDAO;
import fr.univ_lyon1.info.m1.mes.daos.PrescriptionDAO;
import fr.univ_lyon1.info.m1.mes.model.Patient.Patient;
import fr.univ_lyon1.info.m1.mes.model.Prescription.Prescription;

public class PatientBusinessTest {
  private PatientBusiness patientBusiness;

  private Prescription doliprane500;
  private Prescription doliprane1000;
  private Prescription paracetamol;
  private Prescription eatFruit;

  private Patient john;
  private Patient doe;
  private Patient jack;
  private Patient james;
  private Patient eric;
  private Patient jean;

  @BeforeEach
  void setUp() {
    // In the real app we would pass this prescription like that we wouldn't create
    // Another one because it wouldn't make sense.
    PrescriptionDAO prescriptionDAO = new PrescriptionDAO();
    doliprane500 = new Prescription("Doliprane", "500g", "12345678901", "6968686787598");
    doliprane1000 = new Prescription("Doliprane", "1000g", "12345678901", "1234567890987");
    paracetamol = new Prescription("Paracetamol", "1000mg", "12345678901", "1234567890987");
    eatFruit = new Prescription("Fruit", "1000g", "12345678909", "6968686787598");
    try {
      prescriptionDAO.add(doliprane500);
      prescriptionDAO.add(doliprane1000);
      prescriptionDAO.add(paracetamol);
      prescriptionDAO.add(eatFruit);
    } catch (NameAlreadyBoundException e) {
      e.printStackTrace();
    }
    PatientDAO patientDAO = new PatientDAO();

    john = new Patient("John", "Wick", "6968686787598", "", "");
    doe = new Patient("John", "Doe", "7912327085687", "", "");
    jack = new Patient("Jack", "Sparrow", "0975310954209", "", "");
    james = new Patient("James", "Lebron", "1678966979912", "", "");
    eric = new Patient("Eric", "Zemmour", "0862183792365", "", "");
    jean = new Patient("Jean", "Tictac", "7214964912842", "", "");

    try {
      patientDAO.add(john);
      patientDAO.add(doe);
      patientDAO.add(jack);
      patientDAO.add(james);
      patientDAO.add(eric);
    } catch (NameAlreadyBoundException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    }

    patientBusiness = new PatientBusiness(prescriptionDAO);
  }

  @Test
  void getPrescriptionsPatientReturnTheListOfPresctionGivenAPatientObject() {
    try {
      List<Prescription> actualList = patientBusiness.getPrescriptionsPatient(john.getSsID());
      List<Prescription> expectedList = new ArrayList<Prescription>();
      expectedList.add(eatFruit);
      expectedList.add(doliprane500);

      assertAll(
          () -> assertEquals(expectedList.size(), actualList.size()),
          () -> assertThat(actualList,
              Matchers.containsInAnyOrder(expectedList.toArray())));
    } catch (NameNotFoundException e) {
      fail("Should not throw NameNotFoundException because we added the patient to DAO.");
    }
  }

  @Test
  void getPrescriptionsPatientThrowExceptionWhenPatientDoesNotExistInDAO() {
    Exception exception = assertThrows(
        NameNotFoundException.class,
        () -> patientBusiness.getPrescriptionsPatient(jean.getSsID()));
    String actualMessage = exception.getMessage();
    String expectedMessage = "No prescriptions have been found.";

    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void removePrescriptionDeletePrescriptionFromDAO() {
    try {
      // Integration
      getPrescriptionsPatientReturnTheListOfPresctionGivenAPatientObject();
      patientBusiness.removePrescription(doliprane500.getId(), john.getSsID());
      assertThat("Dolprane 500 is not in the prescriptions list of the patient anymore",
          patientBusiness.getPrescriptionsPatient(john.getSsID()), not(hasItem(doliprane500)));
    } catch (InvalidNameException | NameNotFoundException | IllegalAccessException e) {
      fail("Should not throw exception because the prescription is present.");
    }
  }

  @Test
  void removePrescriptionThrowIllegalAccessWhenRemovingAPrescriptionWithDifferentPatientId() {
    final Exception e = assertThrows(IllegalAccessException.class,
        () -> patientBusiness.removePrescription(doliprane500.getId(), jack.getSsID()));

    String actualMessage = e.getMessage();
    String expectedMessage = "This prescription is not assign to you.";

    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void removePrescriptionOfAnUnExistingPrescriptionThrowException() {
    final Exception e = assertThrows(NameNotFoundException.class,
        () -> patientBusiness.removePrescription("123456789098", john.getSsID()));

    String actualMessage = e.getMessage();

    assertEquals("No prescription found for this id.", actualMessage);
  }

  @Test
  void removePrescriptionOfAnUnExistingPatientThrowException() {
    final Exception e = assertThrows(IllegalAccessException.class,
        () -> patientBusiness.removePrescription(doliprane500.getId(), jean.getSsID()));

    String actualMessage = e.getMessage();

    assertEquals("This prescription is not assign to you.", actualMessage);
  }
}
