package fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal;

import fr.univ_lyon1.info.m1.mes.utils.UIDGenerator;

public class HealthProfessional {
    private final String id;
    private String name;
    private String surname;
    private final HPSpeciality speciality;

    public HealthProfessional(final String name, final String surname,
            final HPSpeciality speciality)
            throws IllegalArgumentException {
        checkHealthProfessionnalInput(name, surname, speciality);
        this.id = UIDGenerator.generate();
        this.name = name;
        this.surname = surname;
        this.speciality = speciality;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getId() {
        return id;
    }

    public HPSpeciality getSpeciality() {
        return speciality;
    }

    private void checkHealthProfessionnalInput(final String name, final String surname, final HPSpeciality spe) {
        if (!(name.matches("[a-zA-Z]+") && surname.matches("[a-zA-Z]+") && spe != null)) {
            throw new IllegalArgumentException(
                    "Le nom du professionnel de santé n'est pas valide.");
        }
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setSurname(final String surname) {
        this.surname = surname;
    }
}
