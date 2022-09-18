package fr.univ_lyon1.info.m1.mes.model.Patient;

import java.util.List;

import fr.univ_lyon1.info.m1.mes.model.Prescription.Prescription;
import fr.univ_lyon1.info.m1.mes.model.daos.PrescriptionDAO;
import fr.univ_lyon1.info.m1.mes.utils.UIDGenerator;

public class Patient {
    private List<Prescription> prescriptions;
    private final String id;
    private final String name;
    private final String surname;
    private final String ssID;
    private String adress;
    private String city;

    public Patient(final String name, final String surname, final String ssID)
            throws IllegalArgumentException {
        checkPatientInput(name, surname, ssID);
        this.id = UIDGenerator.generate();
        this.name = name;
        this.surname = surname;
        this.ssID = ssID;
        this.prescriptions = null;
    }

    public Patient(final String name, final String surname, final String ssID,
            final String adress, final String city)
            throws IllegalArgumentException {
        checkPatientInput(name, surname, ssID);
        this.id = UIDGenerator.generate();
        this.name = name;
        this.surname = surname;
        this.ssID = ssID;
        this.adress = adress;
        this.city = city;
        this.prescriptions = null;
    }

    public void updatePrescriptionsList(final List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    /*
     * public List<Prescription> getPrescriptionsGivenByHP(final PrescriptionDAO
     * prescriptionDAO, final String idHP) {
     * return prescriptionDAO.findPrescriptionsGivenByHp(idHP);
     * }
     */

    public List<Prescription> getPrescriptions(final PrescriptionDAO prescriptionDAO) {
        this.prescriptions = prescriptionDAO.findPatientPrescription(id);
        return this.prescriptions;
    }

    private void checkPatientInput(final String name, final String surname, final String ssID) {
        if (!(name.matches("[a-zA-Z]+")
                && surname.matches("[a-zA-Z]+")
                && ssID.matches("^[0-9]{13}$"))) {
            throw new IllegalArgumentException(
                    "Le nom doit être en lettre et le numéro de sécurité sociale avec 13 chiffres."
            );
        }
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

    public String getId() {
        return id;
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
}