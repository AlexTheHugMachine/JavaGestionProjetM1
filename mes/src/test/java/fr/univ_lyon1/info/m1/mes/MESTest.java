package fr.univ_lyon1.info.m1.mes;

import static org.hamcrest.Matchers.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.univ_lyon1.info.m1.mes.model.HealthProfessional;
import fr.univ_lyon1.info.m1.mes.model.MES;
import fr.univ_lyon1.info.m1.mes.model.Patient;
import fr.univ_lyon1.info.m1.mes.model.Prescription;

public class MESTest {
  private MES model;

  @BeforeEach
  public void setUp() {
    model = new MES();
  }

  @Test
  /**
   * Test that when we create multiple patients and add them to our "database" we
   * retrieve all of them inside our List of Patient.
   */
  public void RetrieveAllCreatedPatientsTest() {
    // Given
    Patient John = model.createPatient("John", "102020212345678");
    Patient Alice = model.createPatient("Alice", "20123456789012");
    Patient David = model.createPatient("David", "98662768484681");
    Patient Maine = model.createPatient("Maine", "199010212345678");
    // When
    List<Patient> patients = model.getPatients();
    // Then
    assertThat(patients, hasSize(4));
    assertThat(patients, hasItems(John, Alice, David, Maine));
  }

  @Test
  /**
   * Test that when we create multiple HealthProfessional we retrieve all of
   * them inside our List of HealthProfessional.
   */
  public void RetrieveAllCreatedHealthProfessionalTest() {
    // Given
    HealthProfessional John = new HealthProfessional("Dr.John", model);
    HealthProfessional Dolittle = new HealthProfessional("Dr.Dolittle", model);
    HealthProfessional Menendez = new HealthProfessional("Dr.Menendez", model);
    // When
    List<HealthProfessional> hps = model.getHealthProfessional();
    // Then
    assertThat(hps, hasSize(3));
    assertThat(hps, hasItems(John, Dolittle, Menendez));
  }
}
