package fr.univ_lyon1.info.m1.mes;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

import fr.univ_lyon1.info.m1.mes.model.HealthProfessional;
import fr.univ_lyon1.info.m1.mes.model.MES;
import fr.univ_lyon1.info.m1.mes.model.Patient;
import fr.univ_lyon1.info.m1.mes.model.Prescription;

public class PatientTest {
  MES model = new MES();

  @Test
  /**
   * Test that the name given to a patient during the construction is correctly
   * set.
   */
  public void PatientNameTest() {
    // Given
    Patient p = new Patient("John", "102020212345678");
    // When
    String patientName = p.getName();
    // Then
    assertThat(patientName, is("John"));
  }

  @Test
  /**
   * Test that the ssID given to a patient is the one that we passed during
   * the creation of the Object.
   */
  public void PatientSsIDTest() {
    // Given
    Patient p = new Patient("John", "102020212345678");
    // When
    String patientSsID = p.getSSID();
    // Then
    assertThat(patientSsID, is("102020212345678"));
  }

  @Test
  public void showErrorMessageWhenCreatingPatientByReversingNameAndSSID() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      Patient p = new Patient("201342836674", "Alice");
    });

    String expectedMessage = "Le nom doit être en lettre et le numéro de sécurité sociale en chiffre.";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  /**
   * Test that the patient can retrieve the prescriptions that an
   * healthProfessional gave him.
   */
  public void PatientPrescriptionsGivenByHealtProfessionalTest() {
    // Given
    HealthProfessional hp = new HealthProfessional("Dr. Smith", model);
    Patient p = new Patient("John", "102020212345678");
    p.addPrescription(hp, "Eat fruits");
    // When
    List<Prescription> prescriptions = p.getPrescriptions(hp);
    // Then
    assertThat(prescriptions, hasItem(
        hasProperty("content", equalTo("Eat fruits"))));
  }

  @Test
  /**
   * Test qu'un professionnel de santé qui n'a pas donné de prescription à un
   * patient ne retourne rien.
   */
  public void PatientPrescriptionsAttachOnlyToAnHelathProfessionnal() {
    // Given
    HealthProfessional hp = new HealthProfessional("Dr. Smith", model);
    HealthProfessional hp2 = new HealthProfessional("Dr. Dolittle", model);
    Patient p = new Patient("John", "102020212345678");
    p.addPrescription(hp, "Eat fruits");
    // When
    List<Prescription> prescriptions = p.getPrescriptions(hp2);
    // Then
    assertThat(prescriptions, not(
        hasItem(
            hasProperty("content", equalTo("Eat fruits")))));
  }

  @Test
  /**
   * Teste que lorsqu'un Professionnel de santé supprime une prescription d'un
   * patient,
   * elle n'est plus présente dans la liste de ce dernier patient.
   */
  public void RemovePatientPrescriptionTest() {
    // Given
    HealthProfessional hp = new HealthProfessional("Dr. Smith", model);
    Patient p = new Patient("John", "102020212345678");
    Prescription prescription = new Prescription(hp, "Eat fruits");
    p.addPrescription(prescription);
    // When
    p.removePrescription(prescription);
    List<Prescription> prescriptions = p.getPrescriptions();
    // Then
    assertThat(prescriptions,
        not(hasItem(
            hasProperty("content", equalTo("Eat fruits")))));
  }

}
