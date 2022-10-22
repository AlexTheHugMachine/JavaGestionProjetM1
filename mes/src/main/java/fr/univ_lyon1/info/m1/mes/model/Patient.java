package fr.univ_lyon1.info.m1.mes.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Patient {
    private final List<Prescription> prescriptions = new ArrayList<>();
    private final String name;
    private final String ssID;

    public List<Prescription> getPrescriptions(final HealthProfessional hp) {
        return prescriptions.stream()
                .filter(p -> p.getHealthProfessional() == hp)
                .collect(Collectors.toList());
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public Patient(final String name, final String ssID) {
        final boolean checkThatParametersAreValid =
            name.matches("[a-zA-Z]+") && ssID.matches("[0-9]+");
        if (!checkThatParametersAreValid) {
            throw new IllegalArgumentException(
                    "Le nom doit être en lettre et le numéro de sécurité sociale en chiffre.");
        }
        this.name = name;
        this.ssID = ssID;
    }

    public void addPrescription(final HealthProfessional hp, final String content) {
        prescriptions.add(new Prescription(hp, content));
    }

    public void addPrescription(final Prescription prescription) {
        prescriptions.add(prescription);
    }

    public void removePrescription(final Prescription p) {
        prescriptions.remove(p);
    }

    public String getName() {
        return name;
    }

    public String getSSID() {
        return ssID;
    }
}
