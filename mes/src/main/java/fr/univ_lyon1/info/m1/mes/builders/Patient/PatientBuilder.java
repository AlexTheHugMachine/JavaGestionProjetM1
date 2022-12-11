package fr.univ_lyon1.info.m1.mes.builders.Patient;

import fr.univ_lyon1.info.m1.mes.builders.Builder;
import fr.univ_lyon1.info.m1.mes.model.Patient.Patient;

public class PatientBuilder implements Builder<PatientBuilder, Patient> {
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

  @Override
  public void reset() {
    this.name = null;
    this.surname = null;
    this.ssID = null;
    this.adress = null;
    this.city = null;
  }

  public PatientBuilder setName(final String name) {
    this.name = name;
    return getThis();
  }

  public PatientBuilder setSurname(final String surname) {
    this.surname = surname;
    return getThis();
  }

  public PatientBuilder setSSID(final String ssID) {
    this.ssID = ssID;
    return getThis();
  }

  public PatientBuilder setAdress(final String address) {
    this.adress = address;
    return getThis();
  }

  public PatientBuilder setCity(final String city) {
    this.city = city;
    return getThis();
  }

  @Override
  public Patient build() {
    Patient patientFinal;
    if ((adress == null || adress.isEmpty()) || (city == null || city.isEmpty())) {
      patientFinal = new Patient(name, surname, ssID, "", "");
    } else {
      patientFinal = new Patient(name, surname, ssID, adress, city);
    }
    this.reset();
    return patientFinal;
  }
}
