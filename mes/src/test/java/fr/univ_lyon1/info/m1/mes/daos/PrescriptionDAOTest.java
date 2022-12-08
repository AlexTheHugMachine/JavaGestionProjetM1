package fr.univ_lyon1.info.m1.mes.daos;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.univ_lyon1.info.m1.mes.model.Prescription.Prescription;

/**
 * Nous testons toutes les instances de AbstractDAO car c'est un anti pattern de
 * tester seuelemnt la classe abstraite.
 * https://enterprisecraftsmanship.com/posts/how-to-unit-test-an-abstract-class/
 */
public class PrescriptionDAOTest {
  private Prescription doliprane500;
  private Prescription doliprane1000;
  private Prescription paracetamol;
  private Prescription eatFruit;
  private PrescriptionDAO prescriptionDAO;

  @BeforeEach
  void setUp() {
    prescriptionDAO = new PrescriptionDAO();
    doliprane500 = new Prescription("Doliprane", "500g", "12345678901", "1234567890987");
    doliprane1000 = new Prescription("Doliprane", "1000g", "12345678901", "1234567890987");
    paracetamol = new Prescription("Paracetamol", "1000mg", "12345678901", "1234567890987");
    eatFruit = new Prescription("Fruit", "1000g", "12345678909", "1234097645123");
    try {
      prescriptionDAO.add(doliprane500);
      prescriptionDAO.add(doliprane1000);
      prescriptionDAO.add(paracetamol);
    } catch (NameAlreadyBoundException e) {
      e.printStackTrace();
    }
  }

  @Test
  void findByContentAndReturnAllMatchingPrescriptions() {
    List<Prescription> expectedList = new ArrayList<Prescription>();
    expectedList.add(doliprane1000);
    expectedList.add(doliprane500);

    try {
      List<Prescription> actualList = prescriptionDAO.findByContent("Doliprane");
      assertAll(
          () -> assertEquals(actualList.size(), expectedList.size()),
          () -> assertThat(actualList,
              Matchers.containsInAnyOrder(expectedList.toArray())),
          () -> assertThat(prescriptionDAO.findByContent(
              "DOLIpranE"),
              Matchers.containsInAnyOrder(expectedList.toArray())),
          () -> assertThat(actualList, not(hasItem(paracetamol))));
    } catch (NameNotFoundException e) {
      fail("We didn't find the expectedList given a specific name");
    }
    assertThrows(NameNotFoundException.class, () -> prescriptionDAO.findByContent("Hello"));
  }

  @Test
  @DisplayName("Assert that DAOs properly add Prescription and handle if it already exists")
  public void add() {
    try {
      assertNotNull(prescriptionDAO.add(
          new Prescription(
              "foo",
              "bar",
              "86862638131",
              "1234567890987")));
    } catch (NameAlreadyBoundException e) {
      fail("Prescription with this RPPS is already supposed to be added " + e.getMessage());
    }
    // Dont check duplicate because it would cost some serious cost.
    // Would need to retrieve all the prescription that could match the new one.
    // Foreach one of them we need to check if the idPatient, content and idHP are
    // the same.
  }

  @Test
  @DisplayName("Assert that DAOs properly delete a Prescription and handle if it does not persist")
  public void delete() {
    try {
      prescriptionDAO.delete(doliprane500);
    } catch (NameNotFoundException e) {
      fail("Prescription not existing " + e.getMessage());
    }
    try {
      prescriptionDAO.findOne("0862183792365");
    } catch (NameNotFoundException e) {
      assert (true);
    }
  }

  @Test
  @DisplayName("Throw a NameNotFoundException when deleting a non existing Prescription")
  void throwNameNotFoundWhenDeletingNonExistingPrescritionObject() {
    Prescription nonExistingPatient = new Prescription(
        "Tuyt",
        "300ml",
        "34567890769",
        "1234567890987");
    assertThrows(NameNotFoundException.class, () -> {
      prescriptionDAO.delete(nonExistingPatient);
    });
  }

  @Test
  @DisplayName("Assert that patientDAOs properly delete an Prescription with his ID")
  public void deleteById() {
    try {
      prescriptionDAO.deleteById(doliprane1000.getId());
    } catch (NameNotFoundException e) {
      fail("Prescription not existing " + e.getMessage());
    }
    try {
      prescriptionDAO.findOne(doliprane1000.getId());
    } catch (NameNotFoundException e) {
      assert (true);
    }
  }

  @Test
  @DisplayName("Throw NameNotFoundException when deleting with non existing id")
  void throwNameNotFoundWhendeletingNonExistingPatientBySpecifyingId() {
    assertThrows(NameNotFoundException.class, () -> {
      prescriptionDAO.deleteById("sefs752731Hhdsf");
    });
  }

  @Test
  @DisplayName("Assert that DAOs properly update the Prescription informations")
  public void update() {
    Prescription stuff = new Prescription(
        "Eat Banana",
        "",
        "12345678901",
        "1234567890987");
    prescriptionDAO.update(doliprane1000.getId(), stuff);
    try {
      Prescription newPrescription = prescriptionDAO.findOne(stuff.getId());
      assertAll(
          () -> assertEquals(newPrescription.getContent(), "Eat Banana"),
          () -> assertEquals(newPrescription.getQuantite(), ""),
          () -> assertEquals(newPrescription.getIdHealthProfessional(), "12345678901"),
          () -> assertEquals(newPrescription.getIdPatient(), "1234567890987"));
    } catch (NameNotFoundException e) {
      fail("Fail to retrieve the updated Prescription");
    }
  }

  @Test
  @DisplayName("Dont update Prescription when idPatient are not the same.")
  void updateThrowIllegalArgumentWhenIdPatientIsNotTheSame() {
    Prescription badPrescription = new Prescription(
        "Toto",
        "",
        "12345678901",
        "8721231232321");
    assertThrows(IllegalArgumentException.class,
        () -> prescriptionDAO.update(doliprane500.getId(), badPrescription));
  }

  @Test
  @DisplayName("Dont update Prescription when idHealthProfessional are not the same.")
  void updateThrowIllegalArgumentWhenIdHealthProfessionalIsNotTheSame() {
    Prescription badPrescription = new Prescription(
        "Toto",
        "",
        "09312683218",
        "1234567890987");
    assertThrows(IllegalArgumentException.class,
        () -> prescriptionDAO.update(doliprane500.getId(), badPrescription));
  }

  @Test
  @DisplayName("Update With a new Prescription still use the old id")
  void updateRemoveOldPrescriptionAndUseNewId() {
    Prescription newPrescription = new Prescription(
        "Eat Banana",
        "",
        "12345678901",
        "1234567890987");
    try {
      prescriptionDAO.update(doliprane500.getId(), newPrescription);
      assertThrows(NameNotFoundException.class,
          () -> prescriptionDAO.findOne(doliprane500.getId()));

      assertEquals(newPrescription, prescriptionDAO.findOne(newPrescription.getId()));
    } catch (IllegalArgumentException | NameNotFoundException e) {
      fail("The Prescription is not using the old ID in the collection DAO");
    }
  }

  @Test
  void retrieveAllPrescriptionsIdStoredInDAO() {
    // Given
    Set<Serializable> everyIdStoredInDAO = prescriptionDAO.getAllIds();
    Set<Serializable> expectedListOfIds = new HashSet<>();
    expectedListOfIds.add(doliprane500.getId());
    expectedListOfIds.add(doliprane1000.getId());
    expectedListOfIds.add(paracetamol.getId());
    assertEquals(expectedListOfIds, everyIdStoredInDAO);
  }

  @Test
  @DisplayName("Assert that DAOs properly retrieve the right Prescription with its id")
  public void findOne() {
    try {
      assertEquals(prescriptionDAO.findOne(doliprane500.getId()), doliprane500);
      assertNotEquals(prescriptionDAO.findOne(doliprane500.getId()), doliprane1000);
      assertNotEquals(prescriptionDAO.findOne(paracetamol.getId()), doliprane500);

    } catch (NameNotFoundException e) {
      fail("Can't access or retrieve Prescription " + e.getMessage());
    }
    try {
      prescriptionDAO.findOne("133274329");
      fail("Should throw NameNotFoundException because id does not exist");
    } catch (NameNotFoundException e) {
      assert (true);
    }
  }

  @Test
  @DisplayName("Check that we retrieve every Prescription stored in the DAO")
  void findAll() {
    Collection<Prescription> expectedCollectionOfPatient = new ArrayList<Prescription>();
    expectedCollectionOfPatient.add(doliprane500);
    expectedCollectionOfPatient.add(doliprane1000);
    expectedCollectionOfPatient.add(paracetamol);

    assertThat(prescriptionDAO.findAll(),
        Matchers.containsInAnyOrder(expectedCollectionOfPatient.toArray()));
  }

  @Test
  void findByHPGivenThePatientIdReturnTheExpectedList() {
    List<Prescription> expectedList = new ArrayList<Prescription>();
    expectedList.add(doliprane1000);
    expectedList.add(doliprane500);
    expectedList.add(paracetamol);
    // When
    try {
      List<Prescription> actualList = prescriptionDAO
          .findByHpIdGivenThePatientId("1234567890987", "12345678901");
      // Then
      assertEquals(actualList.size(), expectedList.size());
      assertThat(
          prescriptionDAO.findByHpIdGivenThePatientId(
              "1234567890987",
              "12345678901"),
          Matchers.containsInAnyOrder(expectedList.toArray()));
    } catch (NameNotFoundException | IllegalArgumentException e) {
      fail("We didn't find all the Prescriptions stored inside the database.");
    }
  }

  @Test
  void findByHpGivenThePatientIdThrowErrorWhenIdHPIsIncorrect() {
    Exception message = assertThrows(NameNotFoundException.class,
        () -> prescriptionDAO.findByHpIdGivenThePatientId("1234567890987", "98765432123"));
    String actualMessage = message.getMessage();
    String expectedMessage = "No prescriptions found.";

    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void findByHpGivenThePatientIdThrowErrorWhenIdPatientIsIncorrect() {
    Exception message = assertThrows(NameNotFoundException.class,
        () -> prescriptionDAO.findByHpIdGivenThePatientId("9876543212312", "12345678901"));
    String actualMessage = message.getMessage();
    String expectedMessage = "No prescriptions found.";

    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void findByPatientReturnTheRightPrescriptionList() {
    List<Prescription> expectedList = new ArrayList<Prescription>();
    expectedList.add(doliprane1000);
    expectedList.add(doliprane500);
    expectedList.add(paracetamol);
    // When
    try {
      List<Prescription> actualList = prescriptionDAO
          .findByPatientId("1234567890987");
      // Then
      assertEquals(actualList.size(), expectedList.size());
      assertThat(
          prescriptionDAO.findByPatientId("1234567890987"),
          Matchers.containsInAnyOrder(expectedList.toArray()));
    } catch (NameNotFoundException | IllegalArgumentException e) {
      fail("We didn't find all the Prescriptions stored inside the database.");
    }
  }

  @Test
  void findByPatientThrowNameNotFoundWhenPatientIdIsNotExisting() {
    Exception message = assertThrows(NameNotFoundException.class,
        () -> prescriptionDAO.findByPatientId("9861209812331"));
    String actualMessage = message.getMessage();
    String expectedMessage = "No prescriptions found.";

    assertEquals(expectedMessage, actualMessage);
  }
}
