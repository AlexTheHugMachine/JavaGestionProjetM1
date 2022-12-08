package fr.univ_lyon1.info.m1.mes.daos;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

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

import fr.univ_lyon1.info.m1.mes.model.Patient.Patient;

/**
 * Nous testons toutes les instances de AbstractDAO car c'est un anti pattern de
 * tester seuelemnt la classe abstraite.
 * https://enterprisecraftsmanship.com/posts/how-to-unit-test-an-abstract-class/
 */
public class PatientDAOTest {
  private PatientDAO patientDAO;

  private Patient john;
  private Patient doe;
  private Patient jack;
  private Patient james;
  private Patient eric;

  @BeforeEach
  public void setUp() {
    patientDAO = new PatientDAO();

    john = new Patient("John", "Wick", "6968686787598", "", "");
    doe = new Patient("John", "Doe", "7912327085687", "", "");
    jack = new Patient("Jack", "Sparrow", "0975310954209", "", "");
    james = new Patient("James", "Lebron", "1678966979912", "", "");
    eric = new Patient("Eric", "Zemmour", "0862183792365", "", "");

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
  }

  @Test
  @DisplayName("Assert that DAOs properly add Patient and handle if it already exists")
  public void add() {
    try {
      assertNotNull(patientDAO.add(new Patient("foo", "bar",
          "8686263813176", "", "")));
    } catch (NameAlreadyBoundException e) {
      fail("Patient with this RPPS is already supposed to be added " + e.getMessage());
    }
    try {
      patientDAO.add(new Patient("foo", "bar",
          "8686263813176", "", ""));
      fail("Should throw NameAlreadyBoundException");
    } catch (NameAlreadyBoundException e) {
      assert (true);
    }
  }

  @Test
  @DisplayName("Assert that DAOs properly delete a Patient and handle if it does not persist")
  public void delete() {
    try {
      patientDAO.delete(eric);
    } catch (NameNotFoundException e) {
      fail("Patient not existing " + e.getMessage());
    }
    try {
      patientDAO.findOne("0862183792365");
    } catch (NameNotFoundException e) {
      assert (true);
    }
  }

  @Test
  @DisplayName("Check that we throw a NameNotFoundException when deleting a non existing Patient")
  void throwNameNotFoundWhendeletingNonExistingPatientWithObject() {
    Patient nonExistingPatient = new Patient(
        "Enzo",
        "CECILLON",
        "1234567890769",
        "",
        "");
    assertThrows(NameNotFoundException.class, () -> {
      patientDAO.delete(nonExistingPatient);
    });
  }

  @Test
  @DisplayName("Assert that patientDAOs properly delete an Patient with his ID")
  public void deleteById() {
    try {
      patientDAO.deleteById(eric.getSsID());
    } catch (NameNotFoundException e) {
      fail("Patient not existing " + e.getMessage());
    }
    try {
      patientDAO.findOne("0862183792365");
    } catch (NameNotFoundException e) {
      assert (true);
    }
  }

  @Test
  @DisplayName("Throw NameNotFoundException when deleting with non existing id")
  void throwNameNotFoundWhendeletingNonExistingPatientBySpecifyingId() {
    assertThrows(NameNotFoundException.class, () -> {
      patientDAO.deleteById("32134789812");
    });
  }

  @Test
  @DisplayName("Assert that DAOs properly update the Patient informations")
  public void update() {
    Patient toto = new Patient("toto", "titi",
        eric.getSsID(), "1410 avenue jean dupont", "Jardin");
    patientDAO.update(eric.getSsID(), toto);
    Patient newEric;
    try {
      newEric = patientDAO.findOne(toto.getSsID());
      assertAll(
          () -> assertEquals(newEric.getName(), "toto"),
          () -> assertEquals(newEric.getSurname(), "titi"),
          () -> assertEquals(newEric.getSsID(), "0862183792365"),
          () -> assertEquals(newEric.getAdress(), "1410 avenue jean dupont"),
          () -> assertEquals(newEric.getCity(), "Jardin"));
    } catch (NameNotFoundException e) {
      fail("Fail to retrieve the updated patient");
    }
  }

  @Test
  void searchForPatientAndReturnItsIdStoredInDAO() {
    assertEquals(john.getSsID(), patientDAO.getId(john));
  }

  @Test
  void retrieveAllPatientIdStoredInDAO() {
    // Given
    Set<Serializable> everySSIDStoredInDAO = patientDAO.getAllIds();
    Set<Serializable> expectedListOfIds = new HashSet<>();
    expectedListOfIds.add(john.getSsID());
    expectedListOfIds.add(doe.getSsID());
    expectedListOfIds.add(jack.getSsID());
    expectedListOfIds.add(james.getSsID());
    expectedListOfIds.add(eric.getSsID());
    assertEquals(expectedListOfIds, everySSIDStoredInDAO);
  }

  @Test
  @DisplayName("Assert that DAOs don't update the Patient informations if SSID aren't the same.")
  public void checkThatWeDontUpdateWhenSSIDArentTheSame() {
    Patient titi = new Patient(
        "titi", "titi", "0975310954287", "", "");
    Exception message = assertThrows(IllegalArgumentException.class, () -> {
      patientDAO.update(eric.getSsID(), titi);
    });
    String expectedMessage = "SSID must be the same.";
    String actualMessage = message.getMessage();

    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  @DisplayName("Assert that DAOs properly retrieve the right Patient with his id and handle errors")
  public void findOne() {
    try {
      assertEquals(patientDAO.findOne(eric.getSsID()), eric);
      assertNotEquals(patientDAO.findOne(eric.getSsID()), john);
      assertNotEquals(patientDAO.findOne(john.getSsID()), eric);

    } catch (NameNotFoundException e) {
      fail("Can't access or retrieve Patient " + e.getMessage());
    }
    try {
      patientDAO.findOne("133274329");
      fail("Should throw NameNotFoundException because id does not exist");
    } catch (NameNotFoundException e) {
      assert (true);
    }
  }

  @Test
  @DisplayName("Check that we retrieve every Patient stored in the DAO")
  void findAll() {
    Collection<Patient> expectedCollectionOfPatient = new ArrayList<Patient>();
    expectedCollectionOfPatient.add(john);
    expectedCollectionOfPatient.add(doe);
    expectedCollectionOfPatient.add(jack);
    expectedCollectionOfPatient.add(james);
    expectedCollectionOfPatient.add(eric);
    assertThat(patientDAO.findAll(),
        Matchers.containsInAnyOrder(expectedCollectionOfPatient.toArray()));
  }

  @Test
  @DisplayName("Assert that DAOs return a list of Patient given a Name")
  void findByName() {
    // Given
    List<Patient> expectedList = new ArrayList<Patient>();
    expectedList.add(doe);
    expectedList.add(john);

    try {
      List<Patient> actualList = patientDAO.findByName("John");
      assertEquals(actualList.size(), expectedList.size());
      assertIterableEquals(actualList, expectedList);
      assertIterableEquals(patientDAO.findByName("JohN"), expectedList);
      assertThat(actualList, not(hasItem(eric)));
    } catch (NameNotFoundException e) {
      fail("We didn't find the expectedList given a specific name");
    }
    try {
      patientDAO.findByName("Hello");
      fail("Should throw NameNotFoundException");
    } catch (NameNotFoundException ignored) {
      assert (true);
    }
  }

  @Test
  @DisplayName("Assert that given a List of Patients SSID the DAO return the right Patients")
  void findManyPatientByThereSSID() {
    // Given
    Collection<Serializable> listOfSSID = new ArrayList<Serializable>();
    listOfSSID.add("7912327085687");
    listOfSSID.add("0862183792365");
    listOfSSID.add("0975310954209");

    List<Patient> expectedList = new ArrayList<Patient>();
    expectedList.add(doe);
    expectedList.add(eric);
    expectedList.add(jack);
    // When
    try {
      Collection<Patient> actualList = patientDAO.findByIds(listOfSSID);
      // Then
      assertEquals(actualList.size(), expectedList.size());
      assertIterableEquals(expectedList, actualList);
    } catch (NameNotFoundException | IllegalArgumentException e) {
      fail("We didn't find all the Patient stored inside the database.");
    }
  }

  @Test
  @DisplayName("Throw an IllegalArgumentException when calling findByIds with null list")
  void throwIllegalArgumentExceptionWhenPassingANullOrEmptyList() {
    // Given a null list
    List<Serializable> nullList = null;
    List<Serializable> emptyList = new ArrayList<Serializable>();
    assertThrows(IllegalArgumentException.class, () -> patientDAO.findByIds(nullList));
    assertThrows(IllegalArgumentException.class, () -> patientDAO.findByIds(emptyList));
  }

  @Test
  void checkThatWeThrowCorrectExceptionWhenWeDontFindAnyPatient() {
    Collection<Serializable> incorrectList = new ArrayList<Serializable>();
    incorrectList.add("7U2815788878");
    incorrectList.add("787");

    Exception message = assertThrows(NameNotFoundException.class,
        () -> patientDAO.findByIds(incorrectList));

    String expectedMessage = "One or multiples items aren't present.";
    String actualMessage = message.getMessage();
    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void checkThatWeThrowAnIllegalArgumentExceptionWhenPassingNullList() {
    Collection<Serializable> incorrectList = new ArrayList<Serializable>();
    Exception message = assertThrows(IllegalArgumentException.class,
        () -> patientDAO.findByIds(incorrectList));

    String expectedMessage = "List of ids is empty.";
    String actualMessage = message.getMessage();
    assertEquals(expectedMessage, actualMessage);
  }
}
