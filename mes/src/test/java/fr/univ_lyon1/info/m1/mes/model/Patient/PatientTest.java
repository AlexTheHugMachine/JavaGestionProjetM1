package fr.univ_lyon1.info.m1.mes.model.Patient;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PatientTest {
  private Patient p;
  @BeforeEach
  public void setUp() {
    p = new Patient("John", "Doe", "2847482839712", "87 DDS", "DELAWARE");
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
    Patient p = new Patient("John", "DOE", "2020212345678", "", "");
    // When
    String patientSsID = p.getSSID();
    // Then
    assertThat(patientSsID, is("2020212345678"));
  }

  @Test
  @DisplayName("Tests if the name have the right type")
  public void showErrorMessageWhenCreatingPatientByReversingNameAndSSID() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Patient("201342836674", "Alice", "0123456789012", "", "");
    });

    String expMsg = "Les informations du patient sont invalides.";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expMsg));
  }

  @Test
  @DisplayName("Tests if the SSID have the right type")
  public void checkThatTheSSIDIs13NumbersLength() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Patient("Alice", "WONDERLAND", "20123456782", "", "");
    });

    String expMsg = "Les informations du patient sont invalides.";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expMsg));
  }
}
