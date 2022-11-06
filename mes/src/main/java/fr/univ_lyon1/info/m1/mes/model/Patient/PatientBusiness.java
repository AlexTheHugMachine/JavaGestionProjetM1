package fr.univ_lyon1.info.m1.mes.model.Patient;

import java.util.List;

import javax.naming.InvalidNameException;
import javax.naming.NameNotFoundException;

import fr.univ_lyon1.info.m1.mes.model.Prescription.Prescription;
import fr.univ_lyon1.info.m1.mes.model.daos.PatientDAO;
import fr.univ_lyon1.info.m1.mes.model.daos.PrescriptionDAO;
import fr.univ_lyon1.info.m1.mes.utils.ArgumentChecker;

/**
 * Réalise toutes les opérations métier (les actions que peuvent entreprendre
 * les patients dans l'application).
 * - Un patient doit pouvoir consulter les prescriptions qui lui ont étés
 * données.
 * - Un patient doit pouvoir supprimer lui même des prescriptions. ✅
 * - Un patient doit pouvoir changer son Adresse et sa Ville. ✅
 * - Un patient doit pouvoir se connecter. (TODO)
 * - Un patient doit pouvoir se déconnecter. (TODO)
 *
 * Cette classe est créée et utilisée par le contrôleur délégué
 * <code>PatientBusinessController</code> qui lui injecte les DAOs dont elle a
 * besoin.
 */
public class PatientBusiness {
  private final PrescriptionDAO prescriptionDao;
  // private final PatientDAO patientDao;

  public List<Prescription> getPrescriptionsPatient(final Patient patient) {
    return prescriptionDao.findByPatient(patient.getId());
  }

  public boolean removePrescription(final String idPrescription)
      throws NameNotFoundException, InvalidNameException {
    ArgumentChecker.checkString(idPrescription);
    prescriptionDao.deleteById(idPrescription);
    return true;
  }

  public boolean changeLocationInformations(final Patient patient,
      final String adress, final String city) {
    ArgumentChecker.checkString(city);
    patient.setAdress(adress);
    patient.setCity(city);
    return true;
  }

  PatientBusiness(final PrescriptionDAO pDao, final PatientDAO paDao) {
    this.prescriptionDao = pDao;
    // this.patientDao = paDao;
  }
}
