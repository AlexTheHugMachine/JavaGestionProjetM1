package fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.operations;

import java.util.ArrayList;
import java.util.List;

import javax.naming.InvalidNameException;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;

import fr.univ_lyon1.info.m1.mes.model.Patient.Patient;
import fr.univ_lyon1.info.m1.mes.model.Prescription.Prescription;
import fr.univ_lyon1.info.m1.mes.daos.HealthProfessionalDAO;
import fr.univ_lyon1.info.m1.mes.daos.PatientDAO;
import fr.univ_lyon1.info.m1.mes.daos.PrescriptionDAO;
import fr.univ_lyon1.info.m1.mes.utils.ArgumentChecker;

/**
 * Réalise les opérations métier définies pour un Professionnel de santé. <br>
 * Cette classe est créer et utilisée par le contrôleur délégué
 * <code>HealthProfessionnalBusinessController</code> qui lui injecte les DAOs
 * dont elle a besoin.
 */
public class HealthProfessionnalBusiness {
  private final PatientDAO patientDAO;
  private final PrescriptionDAO prescriptionDAO;
  private final HealthProfessionalDAO hpDAO;

  public Patient getPatientBySSID(final String patientSSID)
      throws NameNotFoundException, InvalidNameException {
    ArgumentChecker.checkStringNotNullOrEmpty(patientSSID);
    try {
      return patientDAO.findOne(patientSSID);
    } catch (NameNotFoundException e) {
      throw new NameNotFoundException("No patient found.");
    }
  }

  public List<Prescription> getPrescriptionsPatient(final String patientSSID)
      throws NameNotFoundException {
    try {
      ArgumentChecker.checkStringNotNullOrEmpty(patientSSID);
      return prescriptionDAO.findByPatientId(patientSSID);
    } catch (IllegalArgumentException e) {
      throw e;
    }
  }

  public List<Prescription> getPrescriptionsGivenByHP(
      final String hpRPPS,
      final String patientSSID)
      throws NameNotFoundException, IllegalArgumentException {
    List<String> check = new ArrayList<String>(2);
    check.add(hpRPPS);
    check.add(patientSSID);

    ArgumentChecker.checkManyStringNotNullOrEmpty(check);
    return prescriptionDAO.findByHpIdGivenThePatientId(patientSSID, hpRPPS);
  }

  public boolean addPrescription(final Prescription prescription)
      throws NameNotFoundException,
      InvalidNameException,
      NameAlreadyBoundException {
    try {
      hpDAO.findOne(prescription.getIdHealthProfessional());
      patientDAO.findOne(prescription.getIdPatient());
      prescriptionDAO.add(prescription);
      return true;
    } catch (NameNotFoundException e) {
      throw new NameNotFoundException(
          "No patient or health professional found.");
    } catch (NameAlreadyBoundException e) {
      throw new NameAlreadyBoundException(
          "Prescription already exists.");
    }
  }

  public boolean removePrescription(final String idPrescription)
      throws NameNotFoundException,
      InvalidNameException {
    ArgumentChecker.checkStringNotNullOrEmpty(idPrescription);
    prescriptionDAO.deleteById(idPrescription);
    return true;
  }

  // TODO : voir si on ne met pas l'update pour l'Observer ici :D .
  public boolean createPatient(final Patient patient) throws NameAlreadyBoundException {
    try {
      patientDAO.add(patient);
    } catch (NameAlreadyBoundException e) {
      throw new NameAlreadyBoundException("Patient already exists.");
    }
    return true;
  }

  public HealthProfessionnalBusiness(final PatientDAO pDao, final PrescriptionDAO prescriptionDAO,
      final HealthProfessionalDAO hpDAO) {
    this.patientDAO = pDao;
    this.prescriptionDAO = prescriptionDAO;
    this.hpDAO = hpDAO;
  }
}
