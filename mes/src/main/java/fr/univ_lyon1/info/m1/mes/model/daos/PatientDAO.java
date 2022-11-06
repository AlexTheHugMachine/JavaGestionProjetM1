package fr.univ_lyon1.info.m1.mes.model.daos;

import java.io.Serializable;

import fr.univ_lyon1.info.m1.mes.model.Patient.Patient;

public class PatientDAO extends AbstractMapDao<Patient> {
  @Override
  protected Serializable getKeyForElement(final Patient element) {
    return element.getId();
  }
}
