package fr.univ_lyon1.info.m1.mes;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.univ_lyon1.info.m1.mes.model.Patient.Patient;
import fr.univ_lyon1.info.m1.mes.model.builders.Patient.PatientBuilder;

public class PatientBuilderTest {
  private PatientBuilder builder;
  private Patient p;

  @BeforeEach
  public void setUp() {
    this.builder = new PatientBuilder();

    builder.setName("Enzo")
        .setSurname("CECILLON")
        .setSSID("2032321312321")
        .setAdress("1410 Avenue Jean Monnet 38200")
        .setCity("Jardin");

    p = builder.build();
  }

  @Test
  public void patientBuilderCorrectlySetName() {
    assertEquals("Enzo", p.getName());
  }

  @Test
  public void patientBuilderCorrectlySetSurname() {
    assertEquals("CECILLON", p.getSurname());
  }

  @Test
  public void patientBuilderCorrectlySetSSID() {
    assertEquals("2032321312321", p.getSSID());
  }

  @Test
  public void patientBuilderCorrectlySetAdress() {
    assertEquals("1410 Avenue Jean Monnet 38200", p.getAdress());
  }

  @Test
  public void patientBuilderCorrectlySetCity() {
    assertEquals("Jardin", p.getCity());
  }

  @Test
  public void patientBuilderHandleCreatePatientWithoutAnAdressAndCity() {
    builder.setName("Enzo")
        .setSurname("CECILLON")
        .setSSID("2202202212342");
    Patient pWithoutAdressCity = builder.build();

    assertAll("The patient should be initialized with an Adress and City Empty",
        () -> assertEquals("Enzo", pWithoutAdressCity.getName()),
        () -> assertEquals("CECILLON", pWithoutAdressCity.getSurname()),
        () -> assertEquals("2202202212342", pWithoutAdressCity.getSSID()),
        () -> assertEquals(null, pWithoutAdressCity.getAdress()),
        () -> assertEquals(null, pWithoutAdressCity.getCity()));
  }
}
