package fr.univ_lyon1.info.m1.mes.builders;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HealthProfessional;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HPSpeciality;
import fr.univ_lyon1.info.m1.mes.builders.HealthProfessionnal.HealthProfessionalBuilder;

/**
 * Purpose : Reproduce how a developper will use this class to create new
 * HealthProfessionnal.
 */
public class HealthProfessionnalBuilderTest {
  private HealthProfessionalBuilder builder;

  @BeforeEach
  public void setUp() {
    builder = new HealthProfessionalBuilder();
  }

  @Test
  @DisplayName("Tests if the professional has been created using the builder")
  public void properlyCreateAnHealthProfessional() {
    HealthProfessional hp = builder
        .setName("John")
        .setSurname("Cena")
        .setRPPS("80744817017")
        .setSpeciality(HPSpeciality.GENERALISTE)
        .build();

    assertAll("Check that we properly define the HealthProfessional",
        () -> assertEquals("John", hp.getName()),
        () -> assertEquals("Cena", hp.getSurname()),
        () -> assertEquals(HPSpeciality.GENERALISTE, hp.getSpeciality()));
  }

  @Test
  @DisplayName("Tests if the speciality has been added to the professionnal")
  public void cannotBuildAnHPWithoutSpecifyingSpeciality() {
    Exception exception = assertThrows(
        IllegalArgumentException.class,
        () -> {
          builder.setName("John")
              .setSurname("Doe")
              .setRPPS("68686896989")
              .build();
        });
    String expMsg = "Les informations du professionnel de santé sont invalides.";
    String actualMsg = exception.getMessage();

    assertEquals(expMsg, actualMsg);
  }

  @Test
  void buildFailWhenRPPSIsNullOrEmptyDuringBuild() {
    Exception exceptionNull = assertThrows(
        IllegalArgumentException.class,
        () -> {
          builder.setName("John")
              .setSurname("Doe")
              .setSpeciality(HPSpeciality.CHIRURGIEN)
              .build();
        });
    Exception exceptionEmpty = assertThrows(
        IllegalArgumentException.class,
        () -> {
          builder.setName("John")
              .setSurname("Doe")
              .setRPPS("")
              .setSpeciality(HPSpeciality.CHIRURGIEN)
              .build();
        });
    String argCheckerMsgNull = "ArgumentChecker Failed Null element. ";
    String argCheckerMsgEmpty = "ArgumentChecker Failed Empty element. ";

    String expectedNullMsg = argCheckerMsgNull
        + "Les informations du professionnel de santé sont invalides.";
    String expectedEmptyMsg = argCheckerMsgEmpty
        + "Les informations du professionnel de santé sont invalides.";

    String actualNullMsg = exceptionNull.getMessage();
    String actualEmptyMsg = exceptionEmpty.getMessage();

    assertEquals(expectedNullMsg, actualNullMsg);
    assertEquals(expectedEmptyMsg, actualEmptyMsg);
  }
}
