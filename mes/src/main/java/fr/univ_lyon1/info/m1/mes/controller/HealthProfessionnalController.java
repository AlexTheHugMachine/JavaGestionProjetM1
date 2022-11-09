package fr.univ_lyon1.info.m1.mes.controller;

import fr.univ_lyon1.info.m1.mes.model.daos.PrescriptionDAO;

// Handle all the action and load all pass all the data to the view.
public class HealthProfessionnal {
    private PrescriptionDAO prescriptionDAO;

    public HealthProfessionnal(PrescriptionDAO prescriptionDAO) {
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
