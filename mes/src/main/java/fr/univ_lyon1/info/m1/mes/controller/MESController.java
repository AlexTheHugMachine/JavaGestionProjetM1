package fr.univ_lyon1.info.m1.mes.controller;

import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.Collection;

import javax.naming.NameNotFoundException;

import fr.univ_lyon1.info.m1.mes.builders.HealthProfessionnal.HealthProfessionalBuilder;
import fr.univ_lyon1.info.m1.mes.builders.Patient.PatientBuilder;
import fr.univ_lyon1.info.m1.mes.daos.HealthProfessionalDAO;
import fr.univ_lyon1.info.m1.mes.daos.PatientDAO;
import fr.univ_lyon1.info.m1.mes.daos.PrescriptionDAO;
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

  private final PatientController patientController;
  private final HealthProfessionalController healthProfessionalController;
  private final PatientsBusinessController patientBusinessController;
  private final HealthProfessionnalBusinessController healthProfessionalBusinessController;

  public MESController(
    final PatientDAO patientDAO,
    final HealthProfessionalDAO healthProfessionalDAO,
    final PrescriptionDAO prescriptionDAO,
    final PatientBuilder patientBuilder,
    final HealthProfessionalBuilder healthProfessionalBuilder) {
      this.patientController = new PatientController(patientDAO, prescriptionDAO,  patientBuilder);
      this.healthProfessionalController = new 
        HealthProfessionalController(healthProfessionalDAO, healthProfessionalBuilder);
      this.patientBusinessController = new PatientsBusinessController(patientDAO, prescriptionDAO);
      this.healthProfessionalBusinessController = new 
        HealthProfessionnalBusinessController(healthProfessionalDAO, prescriptionDAO, patientDAO);
    }

  /*public MESController(
      final PatientController patientController,
      final HealthProfessionalController healthProfessionalController,
      final PrescriptionController prescriptionController) {
    this.patientController = patientController;
    this.healthProfessionalController = healthProfessionalController;
    this.prescriptionController = prescriptionController;
  }*/

  public void addPatientFromHP(final String name, final String surname, final String ssid,
      final String adress, final String city) {
    healthProfessionalController.createDtoPatient(name, surname, ssid, adress, city);
  }

  public void addHealthProfessional(final String name, final String surname, final String rpps,
      final String speciality) {
    healthProfessionalController.createDtoHealthProfessional(name, surname, rpps, speciality);
  }

  public void addPrescription(final String content, final String quantite,
      final String idPrescription, final String idHP, final String idPatient) {
    PrescriptionRequestDto prescriptionRequestDto = new PrescriptionRequestDto(content, quantite, 
    idPrescription, idHP, idPatient);
    healthProfessionalBusinessController.addPrescription(prescriptionRequestDto);
  }

  public void removePatient(final String patientId) {
    patientController.removePatient(patientId);
  }

  public void removeHealthProfessional(final String healthProfessionalId) {
    healthProfessionalController.removeHealthProfessional(healthProfessionalId);
  }

  public void removePrescriptionFromHP(final String prescriptionId) {
    healthProfessionalController.removePrescription(prescriptionId);
  }

  public void removePrescriptionFromPatient(final String prescriptionId) {
    patientController.removePrescription(prescriptionId);
  }

  public Patient getgetPrescriptionsPatientFromPatient(final String patientId) {
    return patientController.getPatient(patientId);
  }

  public ArrayList<Patient> getPatients() {
    return patientController.getPatients();
  }

  public HealthProfessional getHealthProfessional(final String healthProfessionalId) throws NameNotFoundException {
    return healthProfessionalController.getHealthProfessional(healthProfessionalId);
  }

  public Collection<HealthProfessional> getHealthProfessionals() {
    return healthProfessionalController.getHealthProfessionals();
  }

  public void updatePatient(final String patientId, final String name, final String surname,
      final String ssid, final String adress, final String city) {
    patientController.updatePatient(patientId, name, surname, ssid, adress, city);
  }

  public void updateHealthProfessional(final String healthProfessionalId, final String name,
      final String surname, final String rpps, final HPSpeciality speciality) {
    healthProfessionalController.updateHealthProfessional(healthProfessionalId, name, surname,
        rpps, speciality);
  }

  public void updatePrescription(final String prescriptionId, final String content,
      final String quantite, final String idPrescription, 
      final String idHP, final String idPatient) {
    prescriptionController.updatePrescription(prescriptionId, content, quantite, idPrescription,
        idHP, idPatient);
  }

  public HealthProfessionalController getHealthProfessionalController() {
    return healthProfessionalController;
  }

  public PatientController getPatientController() {
    return patientController;
  }

  public PrescriptionController getPrescriptionController() {
    return prescriptionController;
  }
}
