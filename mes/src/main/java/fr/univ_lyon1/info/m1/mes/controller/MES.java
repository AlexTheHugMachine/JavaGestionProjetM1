package fr.univ_lyon1.info.m1.mes.controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.naming.NameAlreadyBoundException;

import fr.univ_lyon1.info.m1.mes.model.MESFileReader;
import fr.univ_lyon1.info.m1.mes.model.builders.Patient.PatientBuilder;
import fr.univ_lyon1.info.m1.mes.model.Patient.Patient;
import fr.univ_lyon1.info.m1.mes.model.daos.HealthProfessionalDAO;
import fr.univ_lyon1.info.m1.mes.model.daos.PatientDAO;

/**
 * Master controler of the app.
 * Problème de responsabilité ?
 */
public class MES {

  private final PatientDAO patientDAO;
  private final HealthProfessionalDAO healthProfessionalDAO;
  private final 
  private final String ressourcePath;

  public MES() {
    patientDAO = new PatientDAO();
    healthProfessionalDAO = new HealthProfessionalDAO();
    ressourcePath = "./src/public/data/";
    initializePatientDatabase();
  }

  // Read file
  // Build List of T element
  private void initializePatientDatabase(Builder builder) {
    try {
      ArrayList<String[]> patientList = this.getListData("PatientList.txt");
      addAllPatientToPatientDAO(patientList);
    } catch (
        FileNotFoundException
        | NullPointerException
        | NoSuchElementException
        | IllegalStateException
        | NameAlreadyBoundException e) {
      e.printStackTrace();
      throw new InternalError();
    }
  }

  private ArrayList<String[]> getListData(final String file)
      throws FileNotFoundException,
      NullPointerException,
      NoSuchElementException,
      IllegalStateException {
    return MESFileReader.readFile(ressourcePath + file);
  }

  private void addListToCorrespondingDAO(final ArrayList<String[]> list, Builder builder) {
    list.forEach(row -> {
      Patient patient = builder.setName(row[0])
          .setSurname(row[1])
          .setSSID(row[2])
          .setAdress(row[3])
          .setCity(row[4])
          .build();
      patientDAO.add(patient);
    });
  }

  public HealthProfessionalDAO getHealthProfessionalDAO() {
    return healthProfessionalDAO;
  }

  public PatientDAO getPatientDAO() {
    return patientDAO;
  }

  // Responsabilités:
  // Initialisation des DAOs. (lecture des fichiers, builders, DAO.add)

  // Spec :
  // On s'occupe d'initialiser tous les utilisateurs de l'application.
  // On créer les DAOs pour les Patients et les HP. (pas de Prescription)
  // Les DAOs vont utiliser les builders suivant pour initialiser notre base de
  // données.
  // - BuildHP
  // - BuildPatient
  // Les données que l'on va utiliser pour les builders seront celle que l'on doit
  // lire
  // dans les fichiers avec la classe MESFileReader.

  // La vue devra prendre en paramètre notre controleur.

  // Mes doit il créer les sous controleurs ?
  // On doit faire en sorte que ce controller set les DAOs de façon globale

}
