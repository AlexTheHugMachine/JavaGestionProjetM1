package fr.univ_lyon1.info.m1.mes.model.builders.HealthProfessionnal;

import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HPSpeciality;

public interface Builder<T extends Builder<T>> {
  T setSpeciality(HPSpeciality speciality);

  T setName(String name);

  T setSurname(String surname);

  T getThis();

}
