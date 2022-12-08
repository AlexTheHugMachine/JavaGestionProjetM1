package fr.univ_lyon1.info.m1.mes.controller;

import java.util.ArrayList;

import fr.univ_lyon1.info.m1.mes.builders.Patient.PatientBuilder;
import fr.univ_lyon1.info.m1.mes.daos.PatientDAO;
import fr.univ_lyon1.info.m1.mes.daos.PrescriptionDAO;
import fr.univ_lyon1.info.m1.mes.model.Patient.Patient;

public class PatientController {

    public PatientController(PatientDAO patientDAO, PatientBuilder patientBuilder) {
    }

    public PatientController(PatientDAO patientDAO, PrescriptionDAO prescriptionDAO, PatientBuilder patientBuilder) {
    }

    public void removePatient(String patientId) {
    }

    public Patient getPatient(String patientId) {
        return null;
    }

    public ArrayList<Patient> getPatients() {
        return null;
    }

    public void createDto(String name, String surname, String ssid, String adress, String city) {
    }

    public void updatePatient(String patientId, String name, String surname, String ssid, String adress, String city) {
    }

    public void removePrescription(String prescriptionId) {
    }

}
