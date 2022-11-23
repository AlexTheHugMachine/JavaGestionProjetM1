package fr.univ_lyon1.info.m1.mes.controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.naming.NameAlreadyBoundException;

import fr.univ_lyon1.info.m1.mes.model.MESFileReader;
import fr.univ_lyon1.info.m1.mes.builders.HealthProfessionnal.HealthProfessionalBuilder;
import fr.univ_lyon1.info.m1.mes.builders.Patient.PatientBuilder;
import fr.univ_lyon1.info.m1.mes.constants.Constants;
import fr.univ_lyon1.info.m1.mes.model.Patient.Patient;
import fr.univ_lyon1.info.m1.mes.daos.HealthProfessionalDAO;
import fr.univ_lyon1.info.m1.mes.daos.PatientDAO;
import fr.univ_lyon1.info.m1.mes.directors.PatientsDirectorTest;

/**
 * Master controler of the app.
 * Problème de responsabilité ?
 */
public class MESController {

  private final PatientBuilder patientBuilder;
  private final HealthProfessionalBuilder hpBuilder;
  private final PatientDAO patientDAO;
  private final HealthProfessionalDAO healthProfessionalDAO;

  public MESController(
      final PatientBuilder patientBuilder,
      final HealthProfessionalBuilder healthProfessionalBuilder,
      final PatientDAO patientDAO,
      final HealthProfessionalDAO healthProfessionalDAO) {
    this.hpBuilder = healthProfessionalBuilder;
    this.patientBuilder = patientBuilder;
    this.patientDAO = patientDAO;
    this.healthProfessionalDAO = healthProfessionalDAO;
  }

  // Read file
  // Build List of T element
  private void initializeLocalDatabase(final PatientBuilder builder, final String filename)
      throws NameAlreadyBoundException {
    this.initializePatientDAO(filename, builder);
    //this.initializeHealthProfessionnalDAO(filename, builder);
  }

  private void initializePatientDAO(final String filename, final PatientBuilder builder)
      throws NameAlreadyBoundException {
    ArrayList<String[]> list;
    try {
      list = MESFileReader.readFile(Constants.getLocalPath() + filename);
      list.forEach(row -> {

        //patientDAO.add(patient);
      });
    } catch (FileNotFoundException
        | NullPointerException
        | NoSuchElementException
        | IllegalStateException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public HealthProfessionalDAO getHealthProfessionalDAO() {
    return healthProfessionalDAO;
  }

  public PatientDAO getPatientDAO() {
    return patientDAO;
  }
}
