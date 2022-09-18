package fr.univ_lyon1.info.m1.mes.model.daos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fr.univ_lyon1.info.m1.mes.model.Prescription.Prescription;

public class PrescriptionDAO extends AbstractMapDao<Prescription> {

  @Override
  protected Serializable getKeyForElement(final Prescription element) {
    return element.getIdPrescription();
  }

  public List<Prescription> findPrescriptionsGivenByHp(final String idPatient, final String idHp) {
    List<Prescription> prescriptions = new ArrayList<Prescription>();
    this.getCollection().values().forEach(p -> {
      if (p.getIdHealthProfessional() == idHp && p.getIdPatient() == idPatient) {
        prescriptions.add(p);
      }
    });
    return prescriptions;
  }

  public List<Prescription> findPatientPrescription(final String idPatient) {
    List<Prescription> prescriptions = new ArrayList<Prescription>();
    this.getCollection().values().forEach(p -> {
      if (p.getIdPatient() == idPatient) {
        prescriptions.add(p);
      }
    });
    return prescriptions;
  }
}
