package fr.univ_lyon1.info.m1.mes.dto.patient;

import java.util.List;

import fr.univ_lyon1.info.m1.mes.model.Prescription.Prescription;

public class PatientResponseDto {

  private String name;
  private String surname;
  private String ssid;
  private String adress;
  private String city;
  private List<Prescription> prescriptions;

  PatientResponseDto(
      final String name,
      final String surname,
      final String ssid,
      final String adress,
      final String city,
      final List<Prescription> prescriptions) {
    this.name = name;
    this.surname = surname;
    this.ssid = ssid;
    this.adress = adress;
    this.city = city;
    this.prescriptions = prescriptions;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(final String surname) {
    this.surname = surname;
  }

  public String getSsid() {
    return ssid;
  }

  public void setSsid(final String ssid) {
    this.ssid = ssid;
  }

  public String getAdress() {
    return adress;
  }

  public void setAdress(final String adress) {
    this.adress = adress;
  }

  public String getCity() {
    return city;
  }

  public void setCity(final String city) {
    this.city = city;
  }

  public List<Prescription> getPrescriptions() {
    return prescriptions;
  }

  public void setPrescription(final List<Prescription> prescriptions) {
    this.prescriptions = prescriptions;
  }

}
