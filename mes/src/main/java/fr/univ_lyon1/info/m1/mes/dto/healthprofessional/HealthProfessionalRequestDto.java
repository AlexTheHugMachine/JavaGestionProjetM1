package fr.univ_lyon1.info.m1.mes.dto.healthprofessional;

/**
 * Représentation des données d'un Professionnel de santé que l'on veut
 * manipuler pour des opérations métiers, des modifiications de la données elle
 * même etc.
 *
 * Si l'application est amené à être utilisé sur un site on pourra simplement
 * passé en paramètre cette classe les infos du professionnel de santé et tout
 * sera géré en arrière boutique.
 */
public class HealthProfessionalRequestDto {
  private String name;
  private String surname;
  private final String rpps;
  private String speciality;

  public HealthProfessionalRequestDto(
    final String name,
    final String surname,
    final String rpps,
    final String speciality) {
        this.name = name;
        this.surname = surname;
        this.rpps = rpps;
        this.speciality = speciality;
    }

  public String getName() {
    return name;
  }

  public String getSurname() {
    return surname;
  }

  public String getRPPS() {
    return this.rpps;
  }

  public String getSpeciality() {
    return speciality;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public void setSurname(final String surname) {
    this.surname = surname;
  }

  public void setSpeciality(final String speciality) {
    this.speciality = speciality;
  }
}
