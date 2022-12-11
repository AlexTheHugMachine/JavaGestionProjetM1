package fr.univ_lyon1.info.m1.mes.dto.prescription;

public class PrescriptionRequestDto {
  private String idPrescription;
  private String idPatient;
  private String idHealthProfessionnal;
  private String content;
  private String quantite;

  public PrescriptionRequestDto(
      final String content,
      final String quantite,
      final String idPrescription,
      final String idHP,
      final String idPatient) {
    this.content = content;
    this.quantite = quantite;
    this.idPrescription = idPrescription;
    this.idHealthProfessionnal = idHP;
    this.idPatient = idPatient;
  }

  public String getContent() {
    return this.content;
  }

  public String getQuantite() {
    return this.quantite;
  }

  public String getId() {
    return this.idPrescription;
  }

  public String getIdHealthProfessional() {
    return this.idHealthProfessionnal;
  }

  public String getIdPatient() {
    return this.idPatient;
  }

  public void setContent(final String content) {
    this.content = content;
  }

  public void setQuantite(final String quantite) {
    this.quantite = quantite;
  }

  public void setId(final String id) {
    this.idPrescription = id;
  }

  public void setIdHealthProfessional(final String idHP) {
    this.idHealthProfessionnal = idHP;
  }

  public void setIdPatient(final String idPatient) {
    this.idPatient = idPatient;
  }

  PrescriptionRequestDto() {
  }
}
