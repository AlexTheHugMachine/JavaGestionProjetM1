package fr.univ_lyon1.info.m1.mes;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

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
import fr.univ_lyon1.info.m1.mes.utils.FileHandler;
import fr.univ_lyon1.info.m1.mes.utils.TimeUtil;
import fr.univ_lyon1.info.m1.mes.view.JfxView;
import javafx.stage.Stage;

public class AppStartup extends AbstractAppStartup {
  private String date;
  private MESController mes;
  private PatientDAO patientDAO;
  private HealthProfessionalDAO healthProfessionalDAO;
  private PrescriptionDAO prescriptionDAO;
  private PatientBuilder patientBuilder;
  private HealthProfessionalBuilder healthProfessionalBuilder;
  private Queue<String> errorLogQueue;

  @Override
  void preLoad()
      throws FileNotFoundException,
      NullPointerException,
      NoSuchElementException,
      IllegalStateException {
    this.date = TimeUtil.getDate("yyyyMMddHHmmss");
    constructObjects();
    loadLocalData();
    this.errorLogQueue = new LinkedList<String>();
  }

  @Override
  void initializeJavafx(final Stage stage) {
    // Create the main scene of the application.
    new JfxView(mes, stage, 600, 600);
  }

  @Override
  void loadConfiguration() {
  }

  @Override
  void loadDynamicComponents() {
  }

  @Override
  void handleStartupException(final String message, final Exception e) {
    // TODO Auto-generated method stub
    // Save log to a file for example.
    String logMessageToAppend = String.format(
        "%n Error Message : %s %n ====== Trace ====== %n %s",
        message,
        e.getMessage());
    this.errorLogQueue.add(logMessageToAppend);
  }

  private void constructObjects() {
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
  }

  private void loadLocalData() {
    // Initialise les DAOs
    createPatientJob();
    createHealthProfessionalJob();
    createPrescritpionJob();
  }

  private void createPatientJob() {
    Runnable jobPatient = () -> {
      try {
        List<Patient> patients = (new Initializer<Patient>()).initializeDAOs(
            Constants.getPatientPath(), Patient.class);
        for (Patient patient : patients) {
          patientDAO.add(patient);
        }
      } catch (FileNotFoundException | NameAlreadyBoundException e) {
        handleStartupException("Something went wrong during reading of Patient Files", e);
      }
    };
    Thread threadPatient = new Thread(jobPatient);
    threadPatient.start();
  }

  private void createHealthProfessionalJob() {
    Runnable jobHP = () -> {
      try {
        List<HealthProfessional> hps = (new Initializer<HealthProfessional>())
            .initializeDAOs(Constants.getHpPath(), HealthProfessional.class);
        for (HealthProfessional healthProfessional : hps) {
          healthProfessionalDAO.add(healthProfessional);
        }
      } catch (FileNotFoundException | NameAlreadyBoundException e) {
        handleStartupException("Something went wrong during reading of HP Files", e);
      }
    };
    Thread threadHP = new Thread(jobHP);
    threadHP.start();
  }

  private void createPrescritpionJob() {
    Runnable jobPrescription = () -> {
      try {
        List<Prescription> prescriptions = (new Initializer<Prescription>())
            .initializeDAOs(Constants.getPrescriptionPath(), Prescription.class);
        for (Prescription prescription : prescriptions) {
          prescriptionDAO.add(prescription);
        }
      } catch (FileNotFoundException | NameAlreadyBoundException e) {
        handleStartupException("Something went wrong during reading of Prescription Files", e);
      }
    };
    Thread threadPrescription = new Thread(jobPrescription);
    threadPrescription.start();
  }

  public void writeErrorQueueToLogFile() {
    String errorMessage = "";
    // Récupère tous les élélemnts stockés dans la pile.
    while (this.errorLogQueue.size() != 0) {
      // Concat l'élément et le supprime.
      errorMessage.concat(this.errorLogQueue.poll());
    }
    // Print dans la console.
    System.out.println("Log : " + errorMessage);
    // Ecris dans le fichier.log
    String errorFilename = String.format("errors-%s.log", this.date);
    try {
      FileHandler.writeContentToFile(
          errorMessage,
          Constants.getLocalPath(),
          errorFilename);
    } catch (IOException e) {
      System.out.println("Error while writing log file.");
    }
  }
}
