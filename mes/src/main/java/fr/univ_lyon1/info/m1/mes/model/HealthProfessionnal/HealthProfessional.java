package fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal;

import java.util.ArrayList;
import java.util.List;

import fr.univ_lyon1.info.m1.mes.utils.ArgumentChecker;
import fr.univ_lyon1.info.m1.mes.utils.Validator;

public class HealthProfessional {
    private String name;
    private String surname;
    private final String rpps;
    private final HPSpeciality speciality;

    public HealthProfessional(final String name, final String surname, final String rpps,
            final HPSpeciality speciality)
            throws IllegalArgumentException {
        checkHealthProfessionnalInput(name, surname, rpps, speciality);
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

    public HPSpeciality getSpeciality() {
        return speciality;
    }

    private void checkHealthProfessionnalInput(final String name, final String surname,
            final String rpps,
            final HPSpeciality spe) throws IllegalArgumentException {
        List<String> list = new ArrayList<String>(3);
        list.add(name);
        list.add(surname);
        list.add(rpps);

        String errorMessage = "Les informations du professionnel de santé sont invalides.";
        try {
            ArgumentChecker.checkManyStringNotNullOrEmpty(list);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage() + errorMessage);
        }
        if (!(Validator.isLetter(name)
                && Validator.isLetter(surname)
                && Validator.isNumberOfLength(rpps, 11)
                && spe != null)) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
