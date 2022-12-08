package fr.univ_lyon1.info.m1.mes.model.Patient.operations;

import java.util.List;

import javax.naming.InvalidNameException;
import javax.naming.NameNotFoundException;

import fr.univ_lyon1.info.m1.mes.model.Patient.Patient;
import fr.univ_lyon1.info.m1.mes.model.Prescription.Prescription;
import fr.univ_lyon1.info.m1.mes.daos.PatientDAO;
import fr.univ_lyon1.info.m1.mes.daos.PrescriptionDAO;
import fr.univ_lyon1.info.m1.mes.dto.patient.PatientRequestDto;
import fr.univ_lyon1.info.m1.mes.utils.ArgumentChecker;

/**
 * Réalise toutes les opérations métier (les actions que peuvent entreprendre
 * les patients dans l'application). <br>
 *
 * Cette classe est créée et utilisée par le contrôleur délégué
 * <code>PatientBusinessController</code> qui lui injecte les DAOs dont elle a
 * besoin.
 */
public class PatientBusiness {
  private final PrescriptionDAO prescriptionDao;
  private final PatientDAO patientDAO;

  /**
   *
   * @param patient
   * @return
   * @throws NameNotFoundException Si on ne trouve pas de prescription
   *                               correspondant à ce patient.
   */
  public List<Prescription> getPrescriptionsPatient(final Patient patient)
      throws NameNotFoundException {
    try {
      return prescriptionDao.findByPatientId(patient.getSSID());
    } catch (NameNotFoundException e) {
      throw new NameNotFoundException("No prescriptions have been found.");
    }
  }

  public Boolean removePrescription(final String idPrescription, final String idPatient)
      throws InvalidNameException, NameNotFoundException, IllegalAccessException {
    ArgumentChecker.checkStringNotNullOrEmpty(idPrescription);
    try {
      // Check that idPatient is equal to prescription.getIdPatient or throw Illegal.
      Prescription prescription = prescriptionDao.findOne(idPrescription);
      if (prescription.getIdPatient().equals(idPatient)) {
        prescriptionDao.deleteById(idPrescription);
        return true;
      } else {
        throw new IllegalAccessException("This prescription is not assign to you.");
      }
    } catch (NameNotFoundException e) {
      throw new NameNotFoundException("No prescription found for this id.");
    }

  }

  public PatientBusiness(final PrescriptionDAO pDao, final PatientDAO patientDAO) {
    this.prescriptionDao = pDao;
    this.patientDAO = patientDAO;
  }
}
