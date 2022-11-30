package fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.operations;

import java.util.ArrayList;
import java.util.List;

import javax.naming.InvalidNameException;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;

import fr.univ_lyon1.info.m1.mes.model.Patient.Patient;
import fr.univ_lyon1.info.m1.mes.model.Prescription.Prescription;
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

  public Patient getPatientBySSID(final String patientSSID)
      throws NameNotFoundException, InvalidNameException {
    ArgumentChecker.checkStringNotNullOrEmpty(patientSSID);
    return patientDAO.findOne(patientSSID);
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

  // TODO : Réfléchir sur si on doit renvoyer le patient ou un dto pour avoir
  // Une représentation précise de ces infos.
  public Patient getPatientInfos(final String idPatient)
      throws NameNotFoundException,
      InvalidNameException {
    ArgumentChecker.checkStringNotNullOrEmpty(idPatient);
    return patientDAO.findOne(idPatient);
  }

  // TODO : Checker dans le BusinessController que prescription n'est pas null.
  public boolean addprescription(final Prescription prescription)
      throws NameNotFoundException,
      InvalidNameException,
      NameAlreadyBoundException {
    prescriptionDAO.add(prescription);
    return true;
  }

  public boolean removePrescription(final String idPrescription)
      throws NameNotFoundException,
      InvalidNameException {
    ArgumentChecker.checkStringNotNullOrEmpty(idPrescription);
    prescriptionDAO.deleteById(idPrescription);
    return true;
  }

  // TODO : voir si on ne met pas l'update pour l'Observer ici :D .
  public boolean createPatient(final Patient patient)
      throws NameAlreadyBoundException {
    patientDAO.add(patient);
    return true;
  }

  public HealthProfessionnalBusiness(final PatientDAO pDao, final PrescriptionDAO prescriptionDAO) {
    this.patientDAO = pDao;
    this.prescriptionDAO = prescriptionDAO;
  }
}
