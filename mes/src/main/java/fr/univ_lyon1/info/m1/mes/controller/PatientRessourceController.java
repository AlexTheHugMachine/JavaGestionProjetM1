package fr.univ_lyon1.info.m1.mes.controller;

import java.util.ArrayList;
import java.util.Collection;

import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;

import fr.univ_lyon1.info.m1.mes.builders.Patient.PatientBuilder;
import fr.univ_lyon1.info.m1.mes.daos.PatientDAO;
import fr.univ_lyon1.info.m1.mes.daos.PrescriptionDAO;
import fr.univ_lyon1.info.m1.mes.dto.patient.PatientRequestDto;
import fr.univ_lyon1.info.m1.mes.model.Patient.Patient;
import fr.univ_lyon1.info.m1.mes.model.Patient.operations.PatientRessource;

public class PatientRessourceController {

    private final PatientRessource patientRessource;

    public PatientRessourceController(PatientDAO patientDAO, PatientBuilder patientBuilder) {
        patientRessource = new PatientRessource(patientDAO, patientBuilder);
    }

    public void createPatient(final PatientRequestDto patient) throws NameAlreadyBoundException {
        patientRessource.create(patient);
    }

    public Patient getPatient(final String ssid) throws NameNotFoundException {
        return patientRessource.readOne(ssid);
    }

    public Collection<Patient> getPatients() {
        return patientRessource.readAll();
    }

    public void updatePatient(final PatientRequestDto patient) {
        patientRessource.update(patient);
    }

    public void removePatient(final PatientRequestDto patient) throws NameNotFoundException {
        patientRessource.delete(patient);
    }

    public void removePatientById(final String ssid) throws NameNotFoundException {
        patientRessource.deleteById(ssid);
    }
}
