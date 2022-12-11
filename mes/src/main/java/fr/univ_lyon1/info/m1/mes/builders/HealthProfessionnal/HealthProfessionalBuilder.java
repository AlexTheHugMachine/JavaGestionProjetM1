package fr.univ_lyon1.info.m1.mes.builders.HealthProfessionnal;

import fr.univ_lyon1.info.m1.mes.builders.Builder;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HPSpeciality;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HealthProfessional;

public class HealthProfessionalBuilder
    implements Builder<HealthProfessionalBuilder, HealthProfessional> {
  private String name;
  private String surname;
  private String rpps;
  private HPSpeciality speciality;

  @Override
  public HealthProfessionalBuilder getThis() {
    return this;
  }

  @Override
  public void reset() {
    this.name = null;
    this.surname = null;
    this.rpps = null;
    this.speciality = null;
  }

  public HealthProfessionalBuilder setName(final String name) {
    this.name = name;
    return getThis();
  }

  public HealthProfessionalBuilder setSurname(final String surname) {
    this.surname = surname;
    return getThis();
  }

  public HealthProfessionalBuilder setRPPS(final String rpps) {
    this.rpps = rpps;
    return getThis();
  }

  public HealthProfessionalBuilder setSpeciality(final HPSpeciality speciality) {
    this.speciality = speciality;
    return getThis();
  }

  @Override
  public HealthProfessional build() {

    HealthProfessional finalProduct = new HealthProfessional(name, surname, rpps, speciality);
    reset();
    return finalProduct;
  }
}
