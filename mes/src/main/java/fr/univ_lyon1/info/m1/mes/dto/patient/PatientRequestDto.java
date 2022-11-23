package fr.univ_lyon1.info.m1.mes.dto.patient;

public class PatientRequestDto {
  private String name;
  private String surname;
  private final String ssID;
  private String adress;
  private String city;

  public PatientRequestDto(
      final String name,
      final String surname,
      final String ssID,
      final String adress, final String city) {
    this.name = name;
    this.surname = surname;
    this.ssID = ssID;
    this.adress = adress;
    this.city = city;
  }

  public String getName() {
    return name;
  }

  public String getSSID() {
    return ssID;
  }

  public String getSurname() {
    return surname;
  }

  public String getAdress() {
    return adress;
  }

  public String getCity() {
    return city;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public void setSurname(final String surname) {
    this.surname = surname;
  }

  public void setAdress(final String adress) {
    this.adress = adress;
  }

  public void setCity(final String city) {
    this.city = city;
  }
}
