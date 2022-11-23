package fr.univ_lyon1.info.m1.mes.model.Prescription;

import java.util.ArrayList;
import java.util.List;

import fr.univ_lyon1.info.m1.mes.utils.ArgumentChecker;
import fr.univ_lyon1.info.m1.mes.utils.UIDGenerator;
import fr.univ_lyon1.info.m1.mes.utils.Validator;

public class Prescription {
    private final String idPrescription;
    private final String idPatient;
    private final String idHealthProfessionnal;
    private final String content;
    private final String quantite;

    public Prescription(
            final String content,
            final String quantite,
            final String idHP, final String idPatient) {
        checkPrescriptionParams(content, idHP, idPatient);
        this.content = content;
        this.quantite = quantite;
        this.idPrescription = UIDGenerator.generate();
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

    private void checkPrescriptionParams(
            final String content,
            final String idHp,
            final String idPatient) {
        List<String> paramsToCheck = new ArrayList<String>();
        paramsToCheck.add(content);
        paramsToCheck.add(idHp);
        paramsToCheck.add(idPatient);
        String errorMessage = "Les informations de Prescription sont invalides.";
        try {
            ArgumentChecker.checkManyStringNotNullOrEmpty(paramsToCheck);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage() + errorMessage);
        }

        if (!(Validator.onlyLettersAndSpaces(content)
                && Validator.isNumberOfLength(idHp, 11)
                && Validator.isNumberOfLength(idPatient, 13))) {
            throw new IllegalArgumentException(errorMessage);
        }

    }
}
