package fr.univ_lyon1.info.m1.mes.model;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import javax.naming.NameAlreadyBoundException;

import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HPSpeciality;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HealthProfessional;
import fr.univ_lyon1.info.m1.mes.model.Patient.Patient;
import fr.univ_lyon1.info.m1.mes.model.Prescription.Prescription;
import fr.univ_lyon1.info.m1.mes.daos.PrescriptionDAO;

public class PatientTest {
  private Patient p;
  private PrescriptionDAO prescriptionDAO;

  @BeforeEach
  public void setUp() {
    p = new Patient("John", "Doe", "284748283", "87 DDS", "DELAWARE");
    prescriptionDAO = new PrescriptionDAO();
  }

  /**
   * Test that the name given to a patient during the construction is correctly
   * set.
   */
  @Test
  @DisplayName("Tests if the name of the patient is correct")
  public void patientNameTest() {
    String patientName = p.getName();
    // Then
    assertThat(patientName, is("John"));
  }

  /**
   * Test that the ssID given to a patient is the one that we passed during
   * the creation of the Object.
   */
  @Test
  @DisplayName("Tests if the SSID of the patient is correct")
  public void patientSsIDTest() {
    // Given
    Patient p = new Patient("John", "DOE", "102020212345678", "", "");
    // When
    String patientSsID = p.getSSID();
    // Then
    assertThat(patientSsID, is("102020212345678"));
  }

  @Test
  @DisplayName("Tests if the name have the right type")
  public void showErrorMessageWhenCreatingPatientByReversingNameAndSSID() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Patient("201342836674", "Alice", "20123456789012");
    });

    String expMsg = "Le nom doit être en lettre et le numéro de sécurité sociale avec 13 chiffres";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expMsg));
  }

  @Test
  @DisplayName("Tests if the SSID have the right type")
  public void checkThatTheSSIDIs13NumbersLength() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Patient("Alice", "WONDERLAND", "20123456782");
    });

    String expMsg = "Le nom doit être en lettre et le numéro de sécurité sociale avec 13 chiffres";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expMsg));
  }

  /**
   * Test that the patient can retrieve the prescriptions that an
   * healthProfessional gave him.
   *
   * @throws NameAlreadyBoundException
   */
  @Test
  @DisplayName("Tests if a prescription has been added to the patient")
  public void patientPrescriptionsGivenByHealtProfessionalTest() throws NameAlreadyBoundException {
    // Given
    HealthProfessional hp = new HealthProfessional(
        "Dr. Smith", "Richard", HPSpeciality.GENERALISTE);
    Patient p = new Patient("John", "Cena", "20123456789012");
    prescriptionDAO.add(new Prescription("Eat fruits", hp.getId(), p.getId()));

    // When
    List<Prescription> prescriptions = prescriptionDAO.findPrescriptionsGivenByHp(
        p.getId(), hp.getId());
    // Then
    assertThat(prescriptions, hasItem(
        hasProperty("content", equalTo("Eat fruits"))));
  }

  // TODO : Mettre en place un observer qui déclenche la mise à jour de la liste
  // des prescriptions.
  /*
   * Teste que lorsqu'un Professionnel de santé supprime une prescription d'un
   * patient,
   * elle n'est plus présente dans la liste de ce dernier patient.
   */
  /*
   * @Test
   * public void patientRetrieveUpdatedPrescriptionList() {
   * // Given
   *
   * HealthProfessional hp = new HealthProfessional(
   * "Dr. Smith", "Richard", HPSpeciality.GENERALISTE);
   * Patient p = new Patient("John", "CENA", "102020212345678");
   * Prescription prescription = new Prescription("Eat fruits", hp.getId(),
   * p.getId());
   * prescriptionDAO.add(prescription);
   * // When
   * prescriptionDAO.delete(prescription);
   * List<Prescription> prescriptions = p.getPrescriptions();
   * // Then
   *
   * assertThat(prescriptions,
   * not(hasItem(
   * hasProperty("content", equalTo("Eat fruits")))));
   * }
   */
}
