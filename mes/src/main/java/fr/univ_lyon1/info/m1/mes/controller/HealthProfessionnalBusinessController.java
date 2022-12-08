package fr.univ_lyon1.info.m1.mes.controller;

import javax.naming.InvalidNameException;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;

import fr.univ_lyon1.info.m1.mes.model.Prescription.Prescription;
import fr.univ_lyon1.info.m1.mes.daos.HealthProfessionalDAO;
import fr.univ_lyon1.info.m1.mes.daos.PatientDAO;
import fr.univ_lyon1.info.m1.mes.daos.PrescriptionDAO;
import fr.univ_lyon1.info.m1.mes.dto.patient.PatientRequestDto;
import fr.univ_lyon1.info.m1.mes.dto.prescription.PrescriptionRequestDto;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.operations.HealthProfessionnalBusiness;
import fr.univ_lyon1.info.m1.mes.model.Patient.Patient;

/**
 * Contrôleur délégué aux CU concernant les opérations métier sur les patiens.
 * Concrètement :
 * - Un Professionnel de santé doit pouvoir chercher un patient avec son num
 * de sécurité sociale. ✅ <br>
 * - Un Professionnel de santé doit pouvoir voir les infos d'un patient.✅ <br>
 * - Un Professionnel de santé doit pouvoir Ajouter une Prescription pour un
 * Patient. ✅ <br>
 * - Un Professionnel de santé doit pouvoir Supprimer une Prescription d'un
 * Patient.✅ <br>
 * - Un professionnel de santé doit pouvoir créer un Patient.✅ <br>
 */
public class HealthProfessionnalBusinessController {
  private HealthProfessionnalBusiness hpBusiness;

  public HealthProfessionnalBusinessController(
      final HealthProfessionalDAO hpDAO,
      final PatientDAO patientDAO,
      final PrescriptionDAO prescriptionDAO) {
    hpBusiness = new HealthProfessionnalBusiness(patientDAO,
        prescriptionDAO, hpDAO);
  }

  public Patient getPatientBySSID(final String patientSSID)
      throws NameNotFoundException, InvalidNameException {
    return hpBusiness.getPatientBySSID(patientSSID);
  }

  public Patient getPatientInfos(final String idPatient)
      throws NameNotFoundException, InvalidNameException {
    return hpBusiness.getPatientBySSID(idPatient);
  }

  public boolean addPrescription(final PrescriptionRequestDto prescriptionDto)
      throws NameNotFoundException, NameAlreadyBoundException, InvalidNameException {
    Prescription prescription = new Prescription(
      prescriptionDto.getContent(),
      prescriptionDto.getQuantite(),
      prescriptionDto.getIdHealthProfessional(),
      prescriptionDto.getIdPatient());
    return hpBusiness.addPrescription(prescription);
  }

  public boolean removePrescription(final String idPrescription)
      throws NameNotFoundException, InvalidNameException {
    return hpBusiness.removePrescription(idPrescription);
  }

  public boolean createPatient(final PatientRequestDto patientDto)
      throws NameAlreadyBoundException {
    Patient patient = new Patient(
      patientDto.getName(),
      patientDto.getSurname(),
      patientDto.getSsID(),
      patientDto.getAdress(),
      patientDto.getCity());
    return hpBusiness.createPatient(patient);
  }
}
