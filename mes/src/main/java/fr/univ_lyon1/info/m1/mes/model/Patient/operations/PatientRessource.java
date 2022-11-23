package fr.univ_lyon1.info.m1.mes.model.Patient.operations;

import java.io.Serializable;
import java.util.List;

import javax.naming.InvalidNameException;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;

import fr.univ_lyon1.info.m1.mes.builders.Patient.PatientBuilder;
import fr.univ_lyon1.info.m1.mes.daos.PatientDAO;
import fr.univ_lyon1.info.m1.mes.dto.patient.PatientRequestDto;
import fr.univ_lyon1.info.m1.mes.model.Patient.Patient;
import fr.univ_lyon1.info.m1.mes.ressources.RessourceInterface;
import fr.univ_lyon1.info.m1.mes.utils.ArgumentChecker;

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

  /**
   * Crée un patient et le place dans le DAO.
   *
   * @param name    Nom du patient à créer.
   * @param surname Nom de Famille du Patient à créer.
   * @param ssID    Numéro de sécurité sociale du Patient.
   * @param adress  Adresse du Patient.
   * @param city    Ville où réside le patient.
   * @return L'id du salon créé
   * @throws InvalidNameException      Ne doit pas arriver car les clés du DAO
   *                                   patient sont des strings (potentiellement
   *                                   levée par
   *                                   <code>patientDao.readOne()</code>)
   * @throws IllegalArgumentException  Si les params sont null ou vides.
   * @throws NameNotFoundException     Si l'id du patient ne correspond à
   *                                   aucune entrée dans le DAO patient.
   * @throws NameAlreadyBoundException Si le patient existe déjà
   */
  public String create(final String name, final String surname, final String ssID,
      final String adress, final String city)
      throws IllegalArgumentException,
      InvalidNameException,
      NameAlreadyBoundException {
    Patient patient = builder.setName(name)
        .setSurname(surname)
        .setSSID(ssID)
        .setAdress(adress)
        .setCity(city)
        .build();

    patientDao.add(patient);
    return patient.getSSID();
  }

  /**
   * Renvoie un salon s'il est présent dans le DAO.
   *
   * @param key L'id du salon demandé
   * @return L'instance de <code>Salon</code> correspondant au login
   * @throws IllegalArgumentException Si l'id du patient est null ou vide
   * @throws InvalidNameException     Si l'id du patient n'est pas un Integer
   *                                  correctement formé
   * @throws NameNotFoundException    Si l'id du patient ne correspond à aucune
   *                                  entrée dans le DAO
   */
  public Patient readOne(final String key)
      throws IllegalArgumentException,
      InvalidNameException,
      NameNotFoundException {
    ArgumentChecker.checkStringNotNullOrEmpty(key);
    return patientDao.findOne(Integer.valueOf(key));
  }

  /**
   * Met à jour un patient en fonction des paramètres envoyés.<br>
   * Si l'un des paramètres est nul ou vide, le champ correspondant n'est pas mis
   * à jour.
   *
   * @param key     L'id de l'objet <code>Patient</code> à mettre à jour
   * @param surname Le nouveau nom de famille à modifier.
   * @param adress  L'adresse à modifier.
   * @param city    La ville à modifier.
   * @throws IllegalArgumentException Si l'id du patient est null ou vide
   * @throws InvalidNameException     Si l'id du patient n'est pas un Integer
   *                                  correctement formé
   * @throws NameNotFoundException    Si l'id du patient ne correspond à aucune
   *                                  entrée dans le DAO
   */
  public void update(final String key,
      final String surname, final String adress, final String city)
      throws IllegalArgumentException,
      InvalidNameException,
      NameNotFoundException {
    ArgumentChecker.checkStringNotNullOrEmpty(key);
    Patient oldPatient = patientDao.findOne(key);
    Patient newPatient = builder
        .setName(oldPatient.getName())
        .setSurname(surname)
        .setAdress(adress)
        .setCity(city)
        .build();

    patientDao.update(oldPatient.getSSID(), newPatient);
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
    ArgumentChecker.checkStringNotNullOrEmpty(key.toString());
    try {
			return patientDao.findOne(key);
		} catch (NameNotFoundException e) {
			throw new NameNotFoundException("No patient found.");
		} catch (InvalidNameException e) {
      System.out.println(e.getStackTrace());
      return null;
    }
  }

  @Override
  public List<Patient> readAll() {
    return (List<Patient>) patientDao.findAll();
  }

  @Override
  public void update(final PatientRequestDto element) {
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
    } catch (InvalidNameException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  @Override
  public void deleteById(final Serializable key)
      throws NameNotFoundException {
    try {
      ArgumentChecker.checkStringNotNullOrEmpty(key.toString());
      patientDao.deleteById(key);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("The id provided is null or empty");
    } catch (NameNotFoundException e) {
      throw new NameNotFoundException("This patient does not exist.");
    } catch (InvalidNameException e) {
      System.out.println("Internal error: " + e.getStackTrace());
    }
  }

  @Override
  public void delete(final PatientRequestDto patient)
      throws NameNotFoundException {
    try {
      Patient storedPatient = patientDao.findOne(patient.getSSID());
      patientDao.delete(storedPatient);
    } catch (NameNotFoundException | InvalidNameException e) {
      throw new NameNotFoundException("This patient does not exist.");
    }
  }
}
