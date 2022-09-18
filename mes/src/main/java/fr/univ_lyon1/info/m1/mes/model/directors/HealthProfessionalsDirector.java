package fr.univ_lyon1.info.m1.mes.model.directors;

import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HPSpeciality;
import fr.univ_lyon1.info.m1.mes.model.builders.HealthProfessionnal.Builder;

public class HealthProfessionalsDirector {
  public void constructGeneraliste(final Builder builder) {
    builder.setSpeciality(HPSpeciality.GENERALISTE);
    builder.setName(null);
    builder.setSurname(null);
  }

  public void constructDentiste(final Builder builder) {
    builder.setSpeciality(HPSpeciality.DENTISTE);
    builder.setName(null);
    builder.setSurname(null);
  }

  public void constructChirurgien(final Builder builder) {
    builder.setSpeciality(HPSpeciality.CHIRURGIEN);
    builder.setName(null);
    builder.setSurname(null);
  }
}
