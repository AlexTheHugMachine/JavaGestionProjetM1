package fr.univ_lyon1.info.m1.mes.controller;

import java.util.List;

import fr.univ_lyon1.info.m1.mes.daos.PrescriptionDAO;
import fr.univ_lyon1.info.m1.mes.model.Prescription.Prescription;

// Handle all the action and load all pass all the data to the view.
public class HealthProfessionnalController {
    private PrescriptionDAO prescriptionDAO;

    public HealthProfessionnalController(PrescriptionDAO prescriptionDAO) {
        this.prescriptionDAO = prescriptionDAO;
    }
    public List<Prescription> getPrescriptions(
            final PrescriptionDAO prescriptionDAO, final String idPatient) {
        return prescriptionDAO.findPatientPrescription(idPatient);
    }

    public void addPrescription(
            final PrescriptionDAO prescriptionDAO, final Prescription prescription)
            throws NameAlreadyBoundException {
        prescriptionDAO.add(prescription);
    }
}
