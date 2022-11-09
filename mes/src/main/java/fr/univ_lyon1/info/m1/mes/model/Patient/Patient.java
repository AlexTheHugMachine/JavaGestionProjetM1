package fr.univ_lyon1.info.m1.mes.model.Patient;

import java.util.ArrayList;

import fr.univ_lyon1.info.m1.mes.utils.UIDGenerator;

// TODO : Faire la doc pour la classe et les fonctions.
public class Patient {
    private final String id;
    private String name;
    private String surname;
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

    public void setName(final String name) {
        this.name = name;
    }

    public String getSSID() {
        return ssID;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(final String surname) {
        this.surname = surname;
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
