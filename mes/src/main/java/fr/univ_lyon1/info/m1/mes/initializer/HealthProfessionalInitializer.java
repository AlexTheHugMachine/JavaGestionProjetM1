package fr.univ_lyon1.info.m1.mes.initializer;

import fr.univ_lyon1.info.m1.mes.builders.HealthProfessionnal.HealthProfessionalBuilder;
import fr.univ_lyon1.info.m1.mes.directors.HealthProfessionalsDirector;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HPSpeciality;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HealthProfessional;

public class HealthProfessionalInitializer
    extends Initializer<HealthProfessional, HealthProfessionalBuilder> {

  @Override
  protected HealthProfessional build(final HealthProfessionalBuilder builder, final String[] row) {
    return HealthProfessionalsDirector.constructHealthProfessional(builder,
        row[0],
        row[1],
        HPSpeciality.valueOf(row[2]));
  }

}
