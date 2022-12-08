package fr.univ_lyon1.info.m1.mes;

import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

import javax.naming.NameAlreadyBoundException;

import fr.univ_lyon1.info.m1.mes.builders.HealthProfessionnal.HealthProfessionalBuilder;
import fr.univ_lyon1.info.m1.mes.builders.Patient.PatientBuilder;
import fr.univ_lyon1.info.m1.mes.constants.Constants;
import fr.univ_lyon1.info.m1.mes.controller.MESController;
import fr.univ_lyon1.info.m1.mes.daos.HealthProfessionalDAO;
import fr.univ_lyon1.info.m1.mes.daos.PatientDAO;
import fr.univ_lyon1.info.m1.mes.daos.PrescriptionDAO;
import fr.univ_lyon1.info.m1.mes.initializer.Initializer;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HealthProfessional;
import fr.univ_lyon1.info.m1.mes.model.Patient.Patient;
import fr.univ_lyon1.info.m1.mes.model.Prescription.Prescription;
import fr.univ_lyon1.info.m1.mes.view.JfxView;
import javafx.stage.Stage;

public class AppStartup extends AbstractAppStartup {
  private MESController mes;
  private PatientDAO patientDAO;
  private HealthProfessionalDAO healthProfessionalDAO;
  private PrescriptionDAO prescriptionDAO;
  private PatientBuilder patientBuilder;
  private HealthProfessionalBuilder healthProfessionalBuilder;

  @Override
  void preLoad()
      throws FileNotFoundException,
      NullPointerException,
      NoSuchElementException,
      IllegalStateException {

    this.patientDAO = new PatientDAO();
    this.healthProfessionalDAO = new HealthProfessionalDAO();
    this.prescriptionDAO = new PrescriptionDAO();
    this.patientBuilder = new PatientBuilder();
    this.healthProfessionalBuilder = new HealthProfessionalBuilder();
    this.mes = new MESController(
        patientDAO,
        healthProfessionalDAO,
        prescriptionDAO,
        patientBuilder,
        healthProfessionalBuilder);
    loadLocalData();
  }

  @Override
  void initializeJavafx(final Stage stage) {
    // Create the main scene of the application.
    new JfxView(mes, stage, 600, 600);
  }

  @Override
  void loadConfiguration() {
    // TODO Auto-generated method stub

  }

  @Override
  void loadDynamicComponents() {
    // TODO Auto-generated method stub

  }

  @Override
  void handleStartupException(final String message, final Exception e) {
    // TODO Auto-generated method stub
    // Save log to a file for example.
  }

  private void loadLocalData() {
    // Initialise les DAOs
    Runnable jobPatient = () -> {
      try {
        (new Initializer<Patient, PatientDAO>()).initializeDAOs(
            Constants.getPatientPath(), patientDAO, Patient.class);
      } catch (FileNotFoundException | NameAlreadyBoundException e) {
        e.printStackTrace();
      }
    };
    Thread threadPatient = new Thread(jobPatient);
    threadPatient.start();

    Runnable jobHP = () -> {
      try {
        (new Initializer<HealthProfessional, HealthProfessionalDAO>())
            .initializeDAOs(Constants.getHpPath(), healthProfessionalDAO, HealthProfessional.class);
      } catch (FileNotFoundException | NameAlreadyBoundException e) {
        e.printStackTrace();
      }
    };
    Thread threadHP = new Thread(jobHP);
    threadHP.start();

    Runnable jobPrescription = () -> {
      try {
        (new Initializer<Prescription, PrescriptionDAO>())
            .initializeDAOs(Constants.getPrescriptionPath(), prescriptionDAO, Prescription.class);
      } catch (FileNotFoundException | NameAlreadyBoundException e) {
        e.printStackTrace();
      }
    };
    Thread threadPrescription = new Thread(jobPrescription);
    threadPrescription.start();
  }
}
