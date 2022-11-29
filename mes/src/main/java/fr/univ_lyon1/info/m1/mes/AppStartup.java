package fr.univ_lyon1.info.m1.mes;

import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

import fr.univ_lyon1.info.m1.mes.builders.HealthProfessionnal.HealthProfessionalBuilder;
import fr.univ_lyon1.info.m1.mes.builders.Patient.PatientBuilder;
import fr.univ_lyon1.info.m1.mes.daos.HealthProfessionalDAO;
import fr.univ_lyon1.info.m1.mes.daos.PatientDAO;
import fr.univ_lyon1.info.m1.mes.daos.PrescriptionDAO;
import fr.univ_lyon1.info.m1.mes.initializer.HealthProfessionalInitializer;
import fr.univ_lyon1.info.m1.mes.initializer.PatientInitializer;
import fr.univ_lyon1.info.m1.mes.initializer.PrescriptionInitializer;

public class AppStartup extends AbstractAppStartup {
  private HealthProfessionalDAO hpDAO;
  private PatientDAO patientDAO;
  private PrescriptionDAO prescriptionDAO;
  private PatientBuilder patientBuilder;
  private HealthProfessionalBuilder hpBuilder;

  @Override
  void preLoad()
      throws FileNotFoundException,
      NullPointerException,
      NoSuchElementException,
      IllegalStateException {
    this.prescriptionDAO = new PrescriptionDAO();
    this.patientBuilder = new PatientBuilder();
    this.hpBuilder = new HealthProfessionalBuilder();
    this.patientDAO = new PatientDAO();
    this.hpDAO = new HealthProfessionalDAO();

    initializeDataWithLocalFiles();
    // Créer l'objet MES avec tous les objects.
  }

  @Override
  void initializeJavafx() {
    // TODO Auto-generated method stub

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

  }

  private void initializeDataWithLocalFiles() {
    // Initialise les DAOs
    try {
      new PatientInitializer().initDAOs(
          "PatientList.txt",
          this.patientBuilder,
          this.patientDAO);
    } catch (FileNotFoundException | NullPointerException | NoSuchElementException 
    | IllegalStateException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    try {
      new HealthProfessionalInitializer().initDAOs(
          "HealthProfessionnalList.txt",
          this.hpBuilder,
          this.hpDAO);
    } catch (FileNotFoundException | NullPointerException | NoSuchElementException 
    | IllegalStateException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    try {
      new PrescriptionInitializer().initDAOs(
          "PrescriptionList.txt",
          this.prescriptionDAO,
          this.prescriptionDAO);
    } catch (FileNotFoundException | NullPointerException | NoSuchElementException 
    | IllegalStateException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}
