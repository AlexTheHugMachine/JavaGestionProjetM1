package fr.univ_lyon1.info.m1.mes.model.Patient;

import java.io.Serializable;
import java.util.Set;

import javax.naming.InvalidNameException;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;

import fr.univ_lyon1.info.m1.mes.builders.Patient.PatientBuilder;
import fr.univ_lyon1.info.m1.mes.daos.PatientDAO;
import fr.univ_lyon1.info.m1.mes.utils.ArgumentChecker;

/**
 * Réalise les opérations "simples" (CRUD) de gestion des ressources de type
 * <code>Patient</code>.<br>
 * * Cette classe est créée et utilisée par le contrôleur de ressources
 * <code>PatientController</code> qui lui injecte le DAO et le builder pour
 * créer des patients.
 */
public class PatientRessource {
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
    String[] check = { name, surname, ssID, adress, city };
    ArgumentChecker.checkManyString(check);

    Patient patient = builder.setName(name)
        .setSurname(surname)
        .setSSID(ssID)
        .setAdress(adress)
        .setCity(city)
        .build();

    patientDao.add(patient);
    return patient.getId();
  }

  /**
   * Crée un patient et le place dans le DAO.
   *
   * @param name    Nom du patient à créer.
   * @param surname Nom de Famille du Patient à créer.
   * @param ssID    Numéro de sécurité sociale du Patient.
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
  public String create(final String name, final String surname, final String ssID)
      throws IllegalArgumentException,
      InvalidNameException,
      NameNotFoundException,
      NameAlreadyBoundException {
    String[] check = { name, surname, ssID };
    ArgumentChecker.checkManyString(check);

    Patient patient = builder.setName(name)
        .setSurname(surname)
        .setSSID(ssID)
        .build();

    patientDao.add(patient);
    return patient.getId();
  }

  /**
   * Renvoie les IDs de tous les patients présents dans le DAO.
   *
   * @return L'ensemble des IDs sous forme d'un
   *         <code>Set&lt;Serializable&gt;</code>
   */
  public Set<Serializable> readAll() {
    return patientDao.getAllIds();
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
    ArgumentChecker.checkString(key);
    return patientDao.findOne(Integer.valueOf(key));
  }

  /**
   * Met à jour un patient en fonction des paramètres envoyés.<br>
   * Si l'un des paramètres est nul ou vide, le champ correspondant n'est pas mis
   * à jour.
   *
   * @param key     L'id de l'objet <code>Patient</code> à mettre à jour
   * @param name    Le nouveau Nom à modifier.
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
      final String name, final String surname, final String adress, final String city)
      throws IllegalArgumentException,
      InvalidNameException,
      NameNotFoundException {
    ArgumentChecker.checkString(key);
    Patient patient = readOne(key);
    // Probably badly wrote but give us the opportunity to set null property and change
    // Only certains attributes.
    if (name != null && !name.equals("")) {
      patient.setName(name);
    }
    if (surname != null && !surname.equals("")) {
      patient.setSurname(surname);
    }
    if (adress != null && !adress.equals("")) {
      patient.setAdress(adress);
    }
    if (city != null && !city.equals("")) {
      patient.setCity(city);
    }
  }

  /**
   * Supprime un salon dans le DAO.
   *
   * @param key Le login du salon à supprimer
   * @throws IllegalArgumentException Si l'id du patient est null ou vide
   * @throws InvalidNameException     Si l'id du patient n'est pas un Integer
   *                                  correctement formé
   * @throws NameNotFoundException    Si l'id du patient ne correspond à aucune
   *                                  entrée dans le DAO
   */
  public void delete(final String key)
      throws IllegalArgumentException,
      InvalidNameException,
      NameNotFoundException {
    ArgumentChecker.checkString(key);
    patientDao.deleteById(Integer.valueOf(key));
  }
}
