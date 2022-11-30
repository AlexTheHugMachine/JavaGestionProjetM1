package fr.univ_lyon1.info.m1.mes.daos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InvalidNameException;
import javax.naming.NameNotFoundException;

import fr.univ_lyon1.info.m1.mes.model.Patient.Patient;
import fr.univ_lyon1.info.m1.mes.utils.ArgumentChecker;

public class PatientDAO extends AbstractMapDao<Patient> {
  @Override
  protected Serializable getKeyForElement(final Patient element) {
    return element.getSSID();
  }

  @Override
  public void update(final Serializable key, final Patient element)
      throws IllegalArgumentException, InvalidNameException {
    try {
      Patient patientStored = findOne(element.getSSID());
      if (patientStored.getSSID().equals(element.getSSID())) {
        super.update(element.getSSID(), element);
      }
    } catch (NameNotFoundException e) {
      throw new IllegalArgumentException("SSID must be the same.");
    }
  }

  public List<Patient> findByName(final String name)
      throws NameNotFoundException, IllegalArgumentException {
    ArgumentChecker.checkStringNotNullOrEmpty(name);
    List<Patient> list = new ArrayList<Patient>();
    this.getCollection().forEach((uid, patient) -> {
      String upperCaseName = patient.getName().toUpperCase();
      if (upperCaseName.equals(name.toUpperCase())) {
        list.add(patient);
      }
    });
    if (list.isEmpty()) {
      throw new NameNotFoundException();
    }
    return list;
  }

}
