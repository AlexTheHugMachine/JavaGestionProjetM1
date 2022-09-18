package fr.univ_lyon1.info.m1.mes.model.Prescription;

import fr.univ_lyon1.info.m1.mes.utils.UIDGenerator;

public class Prescription {
    private final String idPrescription;
    private final String idPatient;
    private final String idHealthProfessionnal;
    private final String content;

    public Prescription(final String content, final String idHP, final String idPatient) {
        this.content = content;
        this.idPrescription = UIDGenerator.generate();
        this.idHealthProfessionnal = idHP;
        this.idPatient = idPatient;
    }

    public String getContent() {
        return this.content;
    }

    public String getIdHealthProfessionnal() {
        return this.idHealthProfessionnal;
    }
    public String getIdPrescription() {
        return this.idPrescription;
    }

    public String getIdHealthProfessional() {
        return this.idHealthProfessionnal;
    }

    public String getIdPatient() {
        return this.idPatient;
    }
}
