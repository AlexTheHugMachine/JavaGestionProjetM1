package fr.univ_lyon1.info.m1.mes.model.Patient.operations;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Collection;

import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.Test;

import fr.univ_lyon1.info.m1.mes.builders.Patient.PatientBuilder;
import fr.univ_lyon1.info.m1.mes.daos.PatientDAO;
import fr.univ_lyon1.info.m1.mes.dto.patient.PatientRequestDto;
import fr.univ_lyon1.info.m1.mes.model.Patient.Patient;

public class PatientRessourceTest {
  private PatientRessource patientRessource;

  private Patient john;
  private Patient doe;
  private Patient jack;
  private Patient james;
  private Patient eric;

  @BeforeEach
  void setup() {
    PatientDAO patientDAO = new PatientDAO();
    PatientBuilder builder = new PatientBuilder();

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

    patientRessource = new PatientRessource(patientDAO, builder);
  }

  @Test
  void readOneReturnTheExpectedPatientResponse() {
    String johnSSID = "6968686787598";
    try {
      Patient result = patientRessource.readOne(johnSSID);
      assertEquals(result, john);
    } catch (NameNotFoundException e) {
      fail("Should not throw this error because the patient exist.");
    }
  }

  @Test
  void readOneThrowNameNotFoundWhenPatientIdNotInDAO() {
    Exception e = assertThrows(NameNotFoundException.class,
        () -> patientRessource.readOne("6968686787599"));
    String actualMessage = e.getMessage();
    String expectedMessage = "No patient found.";
    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void readOneThrowIllegalArgumentWhenKeyIsNullOrEmpty() {
    // handled by toString() builtin method of Java.
    assertThrows(NullPointerException.class,
        () -> patientRessource.readOne(null));
    Exception eEmpty = assertThrows(IllegalArgumentException.class,
        () -> patientRessource.readOne(""));

    String actualMessageEmpty = eEmpty.getMessage();
    String expectedMessageEmpty = "The key is null or empty.";

    assertEquals(expectedMessageEmpty, actualMessageEmpty);
  }

  @Test
  void readAllReturnAllThePatientsStoredInDAO() {
    Collection<Patient> expectedCollectionOfPatient = new ArrayList<Patient>();
    expectedCollectionOfPatient.add(john);
    expectedCollectionOfPatient.add(doe);
    expectedCollectionOfPatient.add(jack);
    expectedCollectionOfPatient.add(james);
    expectedCollectionOfPatient.add(eric);
    assertThat(patientRessource.readAll(),
        Matchers.containsInAnyOrder(expectedCollectionOfPatient.toArray()));
  }

  @Test
  void updateCorrectlyChangedPatientGivenPatientRequest() {
    PatientRequestDto newInformationsOnEric = new PatientRequestDto(
        eric.getName(),
        eric.getSurname(),
        eric.getSsID(),
        "76 avenue Guichard",
        "Lyon");
    assertTrue(patientRessource.update(newInformationsOnEric));
    try {
      Patient newPatientStored = patientRessource.readOne(eric.getSsID());
      assertAll(
          () -> assertEquals(newPatientStored.getName(), "Eric"),
          () -> assertEquals(newPatientStored.getSurname(), "Zemmour"),
          () -> assertEquals(newPatientStored.getSsID(), "0862183792365"),
          () -> assertEquals(newPatientStored.getAdress(),
              "76 avenue Guichard"),
          () -> assertEquals(newPatientStored.getCity(), "Lyon"));
    } catch (NameNotFoundException e) {
      fail("Should not throw an exeception because the patient is still in dao.");
    }
  }

  @Test
  void updateThrowIllegalArgumentWhenSSIDArentTheSame() {
    PatientRequestDto newInformationsOnEric = new PatientRequestDto(
        eric.getName(),
        eric.getSurname(),
        "0862134542365",
        "76 avenue Guichard",
        "Lyon");
    Exception e = assertThrows(IllegalArgumentException.class,
        () -> patientRessource.update(newInformationsOnEric));
    String actualMessage = e.getMessage();
    String expectedMessage = "SSID must be the same.";
    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void updateThrowIllegalArgumentWhenNewNameIsNullOrEmpty() {
    PatientRequestDto newInformationsOnEric = new PatientRequestDto(
        "",
        eric.getSurname(),
        eric.getSsID(),
        "76 avenue Guichard",
        "Lyon");
    PatientRequestDto newInfosWithNull = new PatientRequestDto(
        null,
        eric.getSurname(),
        eric.getSsID(),
        "76 avenue Guichard",
        "Lyon");
    String argCheckerEmpty = "ArgumentChecker Failed Empty element. ";
    String argCheckerNull = "ArgumentChecker Failed Null element. ";

    Exception eEmpty = assertThrows(IllegalArgumentException.class,
        () -> patientRessource.update(newInformationsOnEric));
    Exception eNull = assertThrows(IllegalArgumentException.class,
        () -> patientRessource.update(newInfosWithNull));

    String actualEmptyMessage = eEmpty.getMessage();
    String actualNullMessage = eNull.getMessage();

    String expectedEmptyMsg = argCheckerEmpty + "Les informations du patient sont invalides.";
    String expectedNullMsg = argCheckerNull + "Les informations du patient sont invalides.";

    assertEquals(expectedEmptyMsg, actualEmptyMessage);
    assertEquals(expectedNullMsg, actualNullMessage);
  }

  @Test
  void updateThrowIllegalArgumentWhenNewSurnameIsNullOrEmpty() {
    PatientRequestDto newInformationsOnEric = new PatientRequestDto(
        "Enzo",
        "",
        eric.getSsID(),
        "76 avenue Guichard",
        "Lyon");
    PatientRequestDto newInfosWithNull = new PatientRequestDto(
        "Enzo",
        null,
        eric.getSsID(),
        "76 avenue Guichard",
        "Lyon");
    String argCheckerEmpty = "ArgumentChecker Failed Empty element. ";
    String argCheckerNull = "ArgumentChecker Failed Null element. ";

    Exception eEmpty = assertThrows(IllegalArgumentException.class,
        () -> patientRessource.update(newInformationsOnEric));
    Exception eNull = assertThrows(IllegalArgumentException.class,
        () -> patientRessource.update(newInfosWithNull));

    String actualEmptyMessage = eEmpty.getMessage();
    String actualNullMessage = eNull.getMessage();

    String expectedEmptyMsg = argCheckerEmpty + "Les informations du patient sont invalides.";
    String expectedNullMsg = argCheckerNull + "Les informations du patient sont invalides.";

    assertEquals(expectedEmptyMsg, actualEmptyMessage);
    assertEquals(expectedNullMsg, actualNullMessage);
  }

  @Test
  void updateThrowIllegalArgumentWhenPatientRequestIsNull() {
    assertThrows(NullPointerException.class,
        () -> patientRessource.update(null));
  }

  @Test
  void deleteByIdProperlyRemovePatientAndReturnTrue() {
    try {
      assertTrue(patientRessource.deleteById(eric.getSsID()));
    } catch (NameNotFoundException e) {
      fail("Should not throw this exception because the patient exist.");
    }
    assertThrows(NameNotFoundException.class,
        () -> patientRessource.readOne(eric.getSsID()));
  }

  @Test
  void deleteByIdThrowNameNotFoundWhenPatientIsNotExistingInDAO() {
    Exception e = assertThrows(NameNotFoundException.class,
        () -> patientRessource.deleteById("0862183792366"));
    String actualMessage = e.getMessage();
    String expectedMessage = "This patient does not exist.";
    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void deleteByIdThrowIllegalArgumentWhenKeyIsNullOrEmpty() {
    assertThrows(NullPointerException.class,
        () -> patientRessource.deleteById(null));
    Exception eEmpty = assertThrows(IllegalArgumentException.class,
        () -> patientRessource.deleteById(""));

    String actualMessageEmpty = eEmpty.getMessage();
    String expectedMessageEmpty = "The id provided is null or empty";

    assertEquals(expectedMessageEmpty, actualMessageEmpty);
  }

  @Test
  void deleteByIdThrowNameNotFoundGivenAnIdWithLetters() {
    Exception e = assertThrows(NameNotFoundException.class,
        () -> patientRessource.deleteById("08ff62183792365a"));
    String actualMessage = e.getMessage();
    String expectedMessage = "This patient does not exist.";
    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void deleteProperlyRemovePatientWhenGivingPatientRequest() {
    PatientRequestDto patientRequest = new PatientRequestDto(
        eric.getName(),
        eric.getSurname(),
        eric.getSsID(),
        eric.getAdress(),
        eric.getCity());
    try {
      patientRessource.delete(patientRequest);
    } catch (NameNotFoundException e) {
      fail("Should not return NameNotFoundException because the patient is in the DAO.");
    }
    assertThrows(NameNotFoundException.class,
        () -> patientRessource.readOne(eric.getSsID()));
  }

  @Test
  void deleteThrowNameNotFoundWhenGivenPatientRequestIsIncorrect() {
    PatientRequestDto patientRequest = new PatientRequestDto(
        eric.getName(),
        eric.getSurname(),
        "0862183792366",
        eric.getAdress(),
        eric.getCity());
    Exception e = assertThrows(NameNotFoundException.class,
        () -> patientRessource.delete(patientRequest));
    String actualMessage = e.getMessage();
    String expectedMessage = "This patient does not exist.";
    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void deleteThrowIllegalArgumentWhenGivenPatientRequestIsNull() {
    Exception e = assertThrows(NameNotFoundException.class,
        () -> patientRessource.delete(null));
    String actualMessage = e.getMessage();
    String expectedMessage = "This patient does not exist.";
    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void createPatientProperlyCreateThePatient() {
    PatientRequestDto patientRequest = new PatientRequestDto(
        "John",
        "Doe",
        "0862183792366",
        "76 avenue Guichard",
        "Lyon");
    try {
      patientRessource.create(patientRequest);
    } catch (NameAlreadyBoundException e) {
      fail("Should not throw this exception because the patient does not exist.");
    }
    try {
      Patient patientCreated = patientRessource.readOne("0862183792366");
      assertAll(
          () -> assertEquals(patientCreated.getName(), "John"),
          () -> assertEquals(patientCreated.getSurname(), "Doe"),
          () -> assertEquals(patientCreated.getSsID(), "0862183792366"),
          () -> assertEquals(patientCreated.getAdress(),
              "76 avenue Guichard"),
          () -> assertEquals(patientCreated.getCity(), "Lyon"));
    } catch (NameNotFoundException e) {
      fail("Should not throw this exception because the patient is in the DAO.");
    }
  }

  @Test
  void createPatientThrowNameAlreadyBoundWhenPatientAlreadyExist() {
    PatientRequestDto patientRequest = new PatientRequestDto(
        eric.getName(),
        eric.getSurname(),
        eric.getSsID(),
        eric.getAdress(),
        eric.getCity());
    Exception e = assertThrows(NameAlreadyBoundException.class,
        () -> patientRessource.create(patientRequest));
    String actualMessage = e.getMessage();
    String expectedMessage = "This ressource already exist.";
    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void createPatientThrowNullPointerWhenGivenNullPatientRequest() {
    assertThrows(NullPointerException.class,
        () -> patientRessource.create(null));
  }

  @Test
  void createPatientThrowIllegalArgumentWhenGivenPatientRequestSSIDIsNull() {
    PatientRequestDto patientRequest = new PatientRequestDto(
        eric.getName(),
        eric.getSurname(),
        null,
        eric.getAdress(),
        eric.getCity());
    Exception e = assertThrows(IllegalArgumentException.class,
        () -> patientRessource.create(patientRequest));
    String actualMessage = e.getMessage();
    String expectedMessage =
    "ArgumentChecker Failed Null element. Les informations du patient sont invalides.";
    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void createPatientThrowIllegalArgumentWhenGivenPatientRequestNameIsNull() {
    PatientRequestDto patientRequest = new PatientRequestDto(
        null,
        eric.getSurname(),
        eric.getSsID(),
        eric.getAdress(),
        eric.getCity());
    Exception e = assertThrows(IllegalArgumentException.class,
        () -> patientRessource.create(patientRequest));
    String actualMessage = e.getMessage();
    String expectedMessage =
    "ArgumentChecker Failed Null element. Les informations du patient sont invalides.";
    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void createPatientThrowIllegalArgumentWhenGivenPatientRequestSurnameIsNull() {
    PatientRequestDto patientRequest = new PatientRequestDto(
        eric.getName(),
        null,
        eric.getSsID(),
        eric.getAdress(),
        eric.getCity());
    Exception e = assertThrows(IllegalArgumentException.class,
        () -> patientRessource.create(patientRequest));
    String actualMessage = e.getMessage();
    String expectedMessage =
    "ArgumentChecker Failed Null element. Les informations du patient sont invalides.";
    assertEquals(expectedMessage, actualMessage);
  }
}
