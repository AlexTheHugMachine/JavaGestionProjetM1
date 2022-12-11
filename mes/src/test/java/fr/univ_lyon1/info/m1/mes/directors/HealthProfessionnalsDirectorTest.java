package fr.univ_lyon1.info.m1.mes.directors;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.univ_lyon1.info.m1.mes.builders.HealthProfessionnal.HealthProfessionalBuilder;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HPSpeciality;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HealthProfessional;

public class HealthProfessionnalsDirectorTest {

  private HealthProfessionalBuilder builder;

  @BeforeEach
  void setUp() {
    builder = new HealthProfessionalBuilder();
  }

  @Test
  void buildAndReturnADentiste() {
    HealthProfessional dentiste = HealthProfessionalsDirector
        .constructDentiste(builder, "Jack", "Narrow", "16095429835");

    assertAll("Check that HP has rights info and correspond to the dentiste speciality",
        () -> assertEquals("Jack", dentiste.getName()),
        () -> assertEquals("Narrow", dentiste.getSurname()),
        () -> assertEquals("16095429835", dentiste.getRPPS()),
        () -> assertEquals(HPSpeciality.DENTISTE, dentiste.getSpeciality()));
  }

  @Test
  void buildAndReturnAGeneraliste() {
    HealthProfessional generaliste = HealthProfessionalsDirector
        .constructGeneraliste(builder, "Enzo", "CECILLON", "12341265439");

    assertAll("Check that HP has rights info and correspond to the generaliste speciality",
        () -> assertEquals("Enzo", generaliste.getName()),
        () -> assertEquals("CECILLON", generaliste.getSurname()),
        () -> assertEquals("12341265439", generaliste.getRPPS()),
        () -> assertEquals(HPSpeciality.GENERALISTE, generaliste.getSpeciality()));
  }

  @Test
  void buildAndReturnAChirurgien() {
    HealthProfessional chirugien = HealthProfessionalsDirector
        .constructChirurgien(builder, "John", "DOE", "12345678909");

    assertAll("Check that HP has rights info and correspond to the chirugien speciality",
        () -> assertEquals("John", chirugien.getName()),
        () -> assertEquals("DOE", chirugien.getSurname()),
        () -> assertEquals("12345678909", chirugien.getRPPS()),
        () -> assertEquals(HPSpeciality.CHIRURGIEN, chirugien.getSpeciality()));
  }

  @Test
  void buildAndReturnAnHPBasedOnHPSpecialityPassed() {
    HealthProfessional homeopath = HealthProfessionalsDirector
        .constructHealthProfessional(
            builder,
            "Henry",
            "Cavill",
            "12345678909",
            HPSpeciality.HOMEOPATHE);
    assertAll("Check that HP has rights info and correspond to the chirugien speciality",
        () -> assertEquals("Henry", homeopath.getName()),
        () -> assertEquals("Cavill", homeopath.getSurname()),
        () -> assertEquals("12345678909", homeopath.getRPPS()),
        () -> assertEquals(HPSpeciality.HOMEOPATHE, homeopath.getSpeciality()));
  }
}
