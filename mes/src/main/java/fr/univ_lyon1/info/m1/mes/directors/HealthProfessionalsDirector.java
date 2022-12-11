package fr.univ_lyon1.info.m1.mes.directors;

import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HPSpeciality;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HealthProfessional;
import fr.univ_lyon1.info.m1.mes.builders.HealthProfessionnal.HealthProfessionalBuilder;

public interface HealthProfessionalsDirector {
  static HealthProfessional constructGeneraliste(final HealthProfessionalBuilder builder,
      final String name, final String surname, final String rpps) {
    return builder.setSpeciality(HPSpeciality.GENERALISTE)
        .setName(name)
        .setSurname(surname)
        .setRPPS(rpps)
        .build();
  }

  static HealthProfessional constructDentiste(final HealthProfessionalBuilder builder,
      final String name, final String surname, final String rpps) {
    return builder.setSpeciality(HPSpeciality.DENTISTE)
        .setName(name)
        .setSurname(surname)
        .setRPPS(rpps)
        .build();
  }

  static HealthProfessional constructChirurgien(final HealthProfessionalBuilder builder,
      final String name, final String surname, final String rpps) {
    return builder.setSpeciality(HPSpeciality.CHIRURGIEN)
        .setName(name)
        .setSurname(surname)
        .setRPPS(rpps)
        .build();
  }

  static HealthProfessional constructHealthProfessional(final HealthProfessionalBuilder builder,
      final String name, final String surname, final String rpps, final HPSpeciality speciality) {
    return builder
        .setSpeciality(speciality)
        .setName(name)
        .setSurname(surname)
        .setRPPS(rpps)
        .build();
  }
}
