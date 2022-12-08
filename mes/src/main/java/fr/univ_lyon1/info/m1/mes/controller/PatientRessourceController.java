package fr.univ_lyon1.info.m1.mes.controller;

import java.util.ArrayList;

import fr.univ_lyon1.info.m1.mes.builders.Patient.PatientBuilder;
import fr.univ_lyon1.info.m1.mes.daos.PatientDAO;
import fr.univ_lyon1.info.m1.mes.daos.PrescriptionDAO;
import fr.univ_lyon1.info.m1.mes.model.Patient.Patient;
import fr.univ_lyon1.info.m1.mes.model.Patient.operations.PatientRessource;

public class PatientRessourceController {

    private final PatientRessource patientRessource;

    public PatientRessourceController(PatientDAO patientDAO, PatientBuilder patientBuilder) {
        this.patientRessource = new PatientRessource(patientDAO, patientBuilder);
    }

    public void createPatient(Patient patient) throws NameAlreadyBoundException {
        patientRessource.createPatient(patient);
    } 
}
