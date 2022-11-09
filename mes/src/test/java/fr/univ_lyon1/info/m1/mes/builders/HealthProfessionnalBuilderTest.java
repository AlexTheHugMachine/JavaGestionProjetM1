package fr.univ_lyon1.info.m1.mes;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HealthProfessional;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HPSpeciality;
import fr.univ_lyon1.info.m1.mes.model.builders.HealthProfessionnal.HealthProfessionalBuilder;

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
    HealthProfessional hp =
    builder.setName("John")
        .setSurname("Cena")
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
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          builder.setName("John")
              .setSurname("Doe")
              .build();
        });
  }

}
