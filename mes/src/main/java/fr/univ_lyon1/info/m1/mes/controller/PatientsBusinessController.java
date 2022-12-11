package fr.univ_lyon1.info.m1.mes.controller;

import java.util.List;

import javax.naming.InvalidNameException;
import javax.naming.NameNotFoundException;

import fr.univ_lyon1.info.m1.mes.model.Patient.operations.PatientBusiness;
import fr.univ_lyon1.info.m1.mes.model.Prescription.Prescription;
import fr.univ_lyon1.info.m1.mes.daos.PrescriptionDAO;
import fr.univ_lyon1.info.m1.mes.dto.patient.PatientRequestDto;

/**
 * Contrôleur délégué aux CU concernant les opérations métier sur les patients.
 * Concrètement : <br>
 * - Un patient doit pouvoir consulter les prescriptions qui lui ont étés
 * données.✅ <br>
 * - Un patient doit pouvoir supprimer lui même des prescriptions. ✅ <br>
 * - Un patient doit pouvoir se connecter.
 * - Un patient doit pouvoir se déconnecter.
 */
public class PatientsBusinessController {
  private PatientBusiness patientBusiness;

  public PatientsBusinessController(
      final PrescriptionDAO prescriptionDAO) {
    patientBusiness = new PatientBusiness(prescriptionDAO);
  }

  public List<Prescription> getPrescriptionsPatient(final PatientRequestDto patientDto)
      throws NameNotFoundException {
    return patientBusiness.getPrescriptionsPatient(patientDto.getSsID());
  }

  public boolean removePrescription(final String idPrescription, final String idPatient)
      throws NameNotFoundException, InvalidNameException, IllegalAccessException {
    return patientBusiness.removePrescription(idPrescription, idPatient);
  }
}
