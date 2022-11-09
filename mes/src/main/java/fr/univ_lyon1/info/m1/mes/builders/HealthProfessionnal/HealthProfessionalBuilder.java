package fr.univ_lyon1.info.m1.mes.builders.HealthProfessionnal;

import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HPSpeciality;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HealthProfessional;

public class HealthProfessionalBuilder implements Builder<HealthProfessionalBuilder> {
  private String name;
  private String surname;
  private HPSpeciality speciality;

  @Override
  public HealthProfessionalBuilder getThis() {
    return this;
  }

  private void reset() {
    name = null;
    surname = null;
    speciality = null;
  }

  @Override
  public HealthProfessionalBuilder setSpeciality(final HPSpeciality speciality) {
    this.speciality = speciality;
    return getThis();
  }

  @Override
  public HealthProfessionalBuilder setName(final String name) {
    this.name = name;
    return getThis();
  }

  @Override
  public HealthProfessionalBuilder setSurname(final String surname) {
    this.surname = surname;
    return getThis();
  }

  public HealthProfessional build() {
    HealthProfessional finalProduct = new HealthProfessional(name, surname, speciality);
    reset();
    return finalProduct;
  }
}
