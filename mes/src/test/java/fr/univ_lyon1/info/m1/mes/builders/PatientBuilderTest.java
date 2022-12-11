package fr.univ_lyon1.info.m1.mes.builders;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.univ_lyon1.info.m1.mes.model.Patient.Patient;
import fr.univ_lyon1.info.m1.mes.builders.Patient.PatientBuilder;

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
    assertEquals("2032321312321", p.getSsID());
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
        () -> assertEquals("2202202212342", pWithoutAdressCity.getSsID()),
        () -> assertEquals("", pWithoutAdressCity.getAdress()),
        () -> assertEquals("", pWithoutAdressCity.getCity()));
  }

  @Test
  public void patientBuilderCanCreatePatientWithEmptyAdressOrCity() {
    builder.setName("Enzo")
        .setSurname("CECILLON")
        .setSSID("2202202212342")
        .setAdress("79 Jeriu Terminal")
        .setCity("");
    Patient pWithoutCity = builder.build();

    builder.setName("Enzo")
        .setSurname("CECILLON")
        .setSSID("2202202212342")
        .setAdress("")
        .setCity("Jardin");
    Patient pWithoutAdress = builder.build();

    assertAll("The patient should be initialized with an Adress and City Empty",
        () -> assertEquals("", pWithoutCity.getAdress()),
        () -> assertEquals("", pWithoutCity.getCity()),
        () -> assertEquals("", pWithoutAdress.getAdress()),
        () -> assertEquals("", pWithoutAdress.getCity()));
  }

  @Test
  public void patientBuilderCanCreatePatientWithNullAdressOrCity() {
    builder.setName("Enzo")
        .setSurname("CECILLON")
        .setSSID("2202202212342")
        .setAdress("79 Jeriu Terminal")
        .setCity(null);
    Patient pWithoutCity = builder.build();

    builder.setName("Enzo")
        .setSurname("CECILLON")
        .setSSID("2202202212342")
        .setAdress(null)
        .setCity("Jardin");
    Patient pWithoutAdress = builder.build();

    assertAll("The patient should be initialized with an Adress and City Empty",
        () -> assertEquals("", pWithoutCity.getAdress()),
        () -> assertEquals("", pWithoutCity.getCity()),
        () -> assertEquals("", pWithoutAdress.getAdress()),
        () -> assertEquals("", pWithoutAdress.getCity()));
  }

  @Test
  public void checkThatWeThrowAnExceptionWhenWeCreateAPatientWithoutSSID() {
    builder.setName("Enzo")
        .setSurname("CECILLON");
    Exception exception = assertThrows(
        IllegalArgumentException.class,
        () -> {
          builder.setName("Enzo")
              .setSurname("CECILLON")
              .build();
        });
    String expectedMessage =
    "ArgumentChecker Failed Null element. Les informations du patient sont invalides.";
    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);
  }
}
