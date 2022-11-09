package fr.univ_lyon1.info.m1.mes.daos;

import java.io.Serializable;

import javax.naming.NameNotFoundException;

import fr.univ_lyon1.info.m1.mes.model.Patient.Patient;

public class PatientDAO extends AbstractMapDao<Patient> {
  @Override
  protected Serializable getKeyForElement(final Patient element) {
    return element.getId();
  }

  public Patient findPatientBySSID(final String ssID) throws NameNotFoundException {
    Patient result = null;
    for (Patient patient : this.getCollection().values()) {
      if (patient.getSSID().equals(ssID)) {
        result = patient;
      }
    }
    if (result == null) {
      throw new NameNotFoundException();
    }
    return result;
  }
}
