package fr.univ_lyon1.info.m1.mes.model.Patient;

import java.util.ArrayList;
import java.util.List;

import fr.univ_lyon1.info.m1.mes.utils.ArgumentChecker;
import fr.univ_lyon1.info.m1.mes.utils.Validator;

public class Patient {
    private String name;
    private String surname;
    private final String ssID;
    private String adress;
    private String city;

    public Patient(final String name, final String surname, final String ssID,
            final String adress, final String city)
            throws IllegalArgumentException {
        checkPatientInputs(name, surname, ssID);
        this.name = name;
        this.surname = surname;
        this.ssID = ssID;
        this.adress = adress;
        this.city = city;
    }

    private void checkPatientInputs(final String name, final String surname, final String ssID) {
        List<String> list = new ArrayList<String>(3);
        list.add(name);
        list.add(surname);
        list.add(ssID);
        String errorMessage = "Les informations du patient sont invalides.";
        try {
            ArgumentChecker.checkManyStringNotNullOrEmpty(list);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage() + errorMessage);
        }

        if (!(Validator.isLetter(name)
                && Validator.isLetter(surname)
                && Validator.isNumberOfLength(ssID, 13))) {
            throw new IllegalArgumentException(errorMessage);
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

    public String getAdress() {
        return adress;
    }

    public String getCity() {
        return city;
    }
}
