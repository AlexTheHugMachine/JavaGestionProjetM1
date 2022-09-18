package fr.univ_lyon1.info.m1.mes.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MES {
    private final List<HealthProfessional> healthProfessionals = new ArrayList<>();

    private final Map<String, Patient> registry = new HashMap<>();

    public Patient getPatient(final String ssID) {
        return registry.get(ssID);
    }

    public Patient createPatient(final String name, final String ssID) {
        final Patient p = new Patient(name, ssID);
        registry.put(ssID, p);
        return p;
    }

    public void addHealthProfessional(final HealthProfessional hp) {
        healthProfessionals.add(hp);
    };

    public List<Patient> getPatients() {
        return new ArrayList<>(registry.values());
    }

    public List<HealthProfessional> getHealthProfessional() {
        return healthProfessionals;
    }

    public void createExampleConfiguration() {
        final Patient a = createPatient("Alice Foo", "299010212345678");
        final Patient b = createPatient("Bob Bar", "199010212345678");
        createPatient("Charles Boz", "102020212345678");
        new Masseur("Dr. Zemmour", this);
        final HealthProfessional w = new HealthProfessional("Dr. Who", this);
        final HealthProfessional s = new Dentist("Dr. Strange", this);
        new Homeopath("Dr. Hahnemann", this);
        a.addPrescription(w, "One apple a day");
        a.addPrescription(w, "Sport twice a week");
        b.addPrescription(w, "Whatever placebo, you're not sick");
        b.addPrescription(s, "Snake oil");

    }
}
