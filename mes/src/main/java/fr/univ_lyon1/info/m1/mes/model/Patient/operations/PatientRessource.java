package fr.univ_lyon1.info.m1.mes.model.Patient.operations;

import java.io.Serializable;
import java.util.Collection;

import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;

import fr.univ_lyon1.info.m1.mes.builders.Patient.PatientBuilder;
import fr.univ_lyon1.info.m1.mes.daos.PatientDAO;
import fr.univ_lyon1.info.m1.mes.dto.patient.PatientRequestDto;
import fr.univ_lyon1.info.m1.mes.model.Patient.Patient;
import fr.univ_lyon1.info.m1.mes.ressources.RessourceInterface;
import fr.univ_lyon1.info.m1.mes.utils.ArgumentChecker;
import fr.univ_lyon1.info.m1.mes.utils.Validator;

/**
 * Réalise les opérations "simples" (CRUD) de gestion des ressources de type
 * <code>Patient</code>.<br>
 * Cette classe est créée et utilisée par le contrôleur de ressources
 * <code>PatientController</code> qui lui injecte le DAO et le builder pour
 * créer des patients.
 */
public class PatientRessource implements RessourceInterface<PatientRequestDto, Patient> {
  private final PatientDAO patientDao;
  private final PatientBuilder builder;

  /**
   * Constructeur avec une injection du DAO nécessaire aux opérations.
   *
   * @param patientDao le DAO des Patients.
   */
  public PatientRessource(final PatientDAO patientDao, final PatientBuilder builder) {
    this.patientDao = patientDao;
    this.builder = builder;
  }

  @Override
  public String create(final PatientRequestDto element)
      throws NameAlreadyBoundException {
    String name = element.getName();
    String surname = element.getSurname();
    String ssID = element.getSSID();
    String adress = element.getAdress();
    String city = element.getCity();

    Patient patient = builder.setName(name)
        .setSurname(surname)
        .setSSID(ssID)
        .setAdress(adress)
        .setCity(city)
        .build();

    patientDao.add(patient);
    return patient.getSSID();
  }

  @Override
  public Patient readOne(final Serializable key)
      throws NameNotFoundException {
    try {
      ArgumentChecker.checkStringNotNullOrEmpty(key.toString());
      return patientDao.findOne(key);
    } catch (NameNotFoundException e) {
      throw new NameNotFoundException("No patient found.");
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("The key is null or empty.");
    }
  }

  @Override
  public Collection<Patient> readAll() {
    return patientDao.findAll();
  }

  @Override
  public Boolean update(final PatientRequestDto element) {
    String name = element.getName();
    String surname = element.getSurname();
    String ssID = element.getSSID();
    String adress = element.getAdress();
    String city = element.getCity();

    Patient patient = builder.setName(name)
        .setSurname(surname)
        .setSSID(ssID)
        .setAdress(adress)
        .setCity(city)
        .build();

    try {
      patientDao.update(ssID, patient);
      return true;
    } catch (IllegalArgumentException e) {
      throw e;
    }
  }

  @Override
  public Boolean deleteById(final Serializable key)
      throws NameNotFoundException, IllegalArgumentException {
    try {
      ArgumentChecker.checkStringNotNullOrEmpty(key.toString());
      patientDao.deleteById(key);
      return true;
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("The id provided is null or empty");
    } catch (NameNotFoundException e) {
      throw new NameNotFoundException("This patient does not exist.");
    }
  }

  @Override
  public void delete(final PatientRequestDto patient)
      throws NameNotFoundException {
    try {
      Patient storedPatient = patientDao.findOne(patient.getSSID());
      patientDao.delete(storedPatient);
    } catch (NameNotFoundException | NullPointerException e) {
      throw new NameNotFoundException("This patient does not exist.");
    }
  }
}
