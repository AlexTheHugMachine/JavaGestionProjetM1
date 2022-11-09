package fr.univ_lyon1.info.m1.mes.builders.Patient;

import fr.univ_lyon1.info.m1.mes.model.Patient.Patient;

public class PatientBuilder implements Builder<PatientBuilder> {
  private String name;
  private String surname;
  private String ssID;
  private String adress;
  private String city;

  public PatientBuilder() {
    this.reset();
  }
 @Override
  public PatientBuilder getThis() {
    return this;
  }

  public void reset() {
    this.name = null;
    this.surname = null;
    this.ssID = null;
    this.adress = null;
    this.city = null;
  }

  @Override
  public PatientBuilder setName(final String name) {
    this.name = name;
    return getThis();
  }

  @Override
  public PatientBuilder setSurname(final String surname) {
    this.surname = surname;
    return getThis();
  }

  @Override
  public PatientBuilder setSSID(final String ssID) {
    this.ssID = ssID;
    return getThis();
  }

  @Override
  public PatientBuilder setAdress(final String address) {
    this.adress = address;
    return getThis();
  }

  @Override
  public PatientBuilder setCity(final String city) {
    this.city = city;
    return getThis();
  }

  public Patient build() {
    Patient patientFinal;
    if (adress == null && city == null) {
      patientFinal = new Patient(name, surname, ssID);
    } else {
      patientFinal = new Patient(name, surname, ssID, adress, city);
    }
    this.reset();
    return patientFinal;
  }
}
