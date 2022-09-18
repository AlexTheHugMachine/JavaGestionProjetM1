package fr.univ_lyon1.info.m1.mes.model;

import java.util.List;

public class HealthProfessional {
    private final String name;
    private final MES mes;

    public HealthProfessional(final String name, final MES mes) {
        this.name = name;
        this.mes = mes;
        mes.addHealthProfessional(this);
    }

    public String getName() {
        return name;
    }

    public Patient getPatient(final String ssID) {
        return mes.getPatient(ssID);
    }

    public List<Prescription> getPrescriptions(final String ssID) {
        return mes.getPatient(ssID).getPrescriptions(this);
    }
}
