package fr.univ_lyon1.info.m1.mes.directors;

import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HPSpeciality;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HealthProfessional;
import fr.univ_lyon1.info.m1.mes.builders.HealthProfessionnal.HealthProfessionalBuilder;

public class HealthProfessionalsDirector {
  public HealthProfessional constructGeneraliste(final HealthProfessionalBuilder builder,
      final String name, final String surname) {
    return builder.setSpeciality(HPSpeciality.GENERALISTE)
        .setName(name)
        .setSurname(surname)
        .build();
  }

  public HealthProfessional constructDentiste(final HealthProfessionalBuilder builder,
      final String name, final String surname) {
    return builder.setSpeciality(HPSpeciality.DENTISTE)
        .setName(name)
        .setSurname(surname)
        .build();
  }

  public HealthProfessional constructChirurgien(final HealthProfessionalBuilder builder,
      final String name, final String surname) {
    return builder.setSpeciality(HPSpeciality.CHIRURGIEN)
        .setName(name)
        .setSurname(surname)
        .build();
  }
}
