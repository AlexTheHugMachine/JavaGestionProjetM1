package fr.univ_lyon1.info.m1.mes.controller;

import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.Collection;

import javax.naming.InvalidNameException;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;

import fr.univ_lyon1.info.m1.mes.builders.HealthProfessionnal.HealthProfessionalBuilder;
import fr.univ_lyon1.info.m1.mes.builders.Patient.PatientBuilder;
import fr.univ_lyon1.info.m1.mes.daos.HealthProfessionalDAO;
import fr.univ_lyon1.info.m1.mes.daos.PatientDAO;
import fr.univ_lyon1.info.m1.mes.daos.PrescriptionDAO;
import fr.univ_lyon1.info.m1.mes.dto.healthprofessional.HealthProfessionalRequestDto;
import fr.univ_lyon1.info.m1.mes.dto.patient.PatientRequestDto;
import fr.univ_lyon1.info.m1.mes.dto.prescription.PrescriptionRequestDto;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HPSpeciality;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HealthProfessional;
import fr.univ_lyon1.info.m1.mes.model.Patient.Patient;
import fr.univ_lyon1.info.m1.mes.model.Patient.operations.PatientBusiness;
import javafx.print.Collation;

/**
 * Master controler of the app.
 * Problème de responsabilité ?
 */
public class MESController {

  private final PatientRessourceController patientRessourceController;
  private final HealthProfessionalRessourceController healthProfessionalRessourceController;
  private final PatientsBusinessController patientBusinessController;
  private final HealthProfessionnalBusinessController healthProfessionalBusinessController;

  public MESController(
    final PatientDAO patientDAO,
    final HealthProfessionalDAO healthProfessionalDAO,
    final PrescriptionDAO prescriptionDAO,
    final PatientBuilder patientBuilder,
    final HealthProfessionalBuilder healthProfessionalBuilder) {
      this.patientRessourceController = new PatientRessourceController(patientDAO, patientBuilder);
      this.healthProfessionalRessourceController = new 
        HealthProfessionalRessourceController(healthProfessionalDAO, healthProfessionalBuilder);
      this.patientBusinessController = new PatientsBusinessController(patientDAO, prescriptionDAO);
      this.healthProfessionalBusinessController = new 
        HealthProfessionnalBusinessController(healthProfessionalDAO, patientDAO, prescriptionDAO);
    }

  public void addPatientFromHP(final String name, final String surname, final String ssid,
      final String adress, final String city) throws NameAlreadyBoundException {
    PatientRequestDto patientRequestDto = new PatientRequestDto(name, surname, ssid, adress, city);
    healthProfessionalBusinessController.createPatient(patientRequestDto);
  }

  public void addHealthProfessional(final String name, final String surname, final String rpps,
      final String speciality) throws NameAlreadyBoundException {
    HealthProfessionalRequestDto healthProfessionalRequestDto = new HealthProfessionalRequestDto(
      name, surname, rpps, speciality);
    healthProfessionalRessourceController.createHealthProfessional(healthProfessionalRequestDto);
  }

  public void addPrescription(final String content, final String quantite,
      final String idPrescription, final String idHP, final String idPatient) throws 
        NameNotFoundException, NameAlreadyBoundException, InvalidNameException {
    PrescriptionRequestDto prescriptionRequestDto = new PrescriptionRequestDto(content, quantite, 
    idPrescription, idHP, idPatient);
    healthProfessionalBusinessController.addPrescription(prescriptionRequestDto);
  }

  public void removeHealthProfessional(final String name, final String surname, final String rpps,
  final String speciality) throws NameNotFoundException {
    HealthProfessionalRequestDto healthProfessionalRequestDto = new HealthProfessionalRequestDto(
      name, surname, rpps, speciality);
    healthProfessionalRessourceController.removeHealthProfessional(healthProfessionalRequestDto);
  }

  public void removeHealthProfessionalById(final String healthProfessionalId) throws 
    NameNotFoundException {
    healthProfessionalRessourceController.removeHealthProfessionalById(healthProfessionalId);
  }

  public void removePrescriptionFromHP(final String prescriptionId) throws 
      NameNotFoundException, InvalidNameException {
    healthProfessionalBusinessController.removePrescription(prescriptionId);
  }

  public void updateHealthProfessional(final String name, final String surname, final String rpps,
      final String speciality) {
    HealthProfessionalRequestDto healthProfessionalRequestDto = new HealthProfessionalRequestDto(
      name, surname, rpps, speciality);
    healthProfessionalRessourceController.updateHealthProfessional(healthProfessionalRequestDto);
  }

  public void getHealthProfessional(final String healthProfessionalId) throws NameNotFoundException {
    healthProfessionalRessourceController.getHealthProfessional(healthProfessionalId);
  }

  public void getHealthProfessionals() {
    healthProfessionalRessourceController.getHealthProfessionals();
  }

  public void getPatientBySSID(final String ssid) throws NameNotFoundException, InvalidNameException {
    healthProfessionalBusinessController.getPatientBySSID(ssid);
  }

  public void getPatientInfos(final String patientId) throws NameNotFoundException, 
    InvalidNameException {
    healthProfessionalBusinessController.getPatientInfos(patientId);
  }
}
