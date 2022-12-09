package fr.univ_lyon1.info.m1.mes.controller;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

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
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HealthProfessional;
import fr.univ_lyon1.info.m1.mes.model.Patient.Patient;
import fr.univ_lyon1.info.m1.mes.model.Prescription.Prescription;

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
      this.patientBusinessController = new PatientsBusinessController(prescriptionDAO, patientDAO);
      this.healthProfessionalBusinessController = new 
        HealthProfessionnalBusinessController(healthProfessionalDAO, patientDAO, prescriptionDAO);
    }

  public boolean addPatientFromHP(final String name, final String surname, final String ssid,
      final String adress, final String city) throws NameAlreadyBoundException {
    PatientRequestDto patientRequestDto = new PatientRequestDto(name, surname, ssid, adress, city);
    return healthProfessionalBusinessController.createPatient(patientRequestDto);
  }

  public String addPatientFromPatient(final String name, final String surname, final String ssid,
      final String adress, final String city) throws NameAlreadyBoundException {
    PatientRequestDto patientRequestDto = new PatientRequestDto(name, surname, ssid, adress, city);
    return patientRessourceController.createPatient(patientRequestDto);
  }

  public Serializable addHealthProfessional(final String name, final String surname, final String rpps,
      final String speciality) throws NameAlreadyBoundException {
    HealthProfessionalRequestDto healthProfessionalRequestDto = new HealthProfessionalRequestDto(
      name, surname, rpps, speciality);
    return healthProfessionalRessourceController.createHealthProfessional(healthProfessionalRequestDto);
  }

  public boolean addPrescription(final String content, final String quantite,
      final String idPrescription, final String idHP, final String idPatient) throws 
        NameNotFoundException, NameAlreadyBoundException, InvalidNameException {
    PrescriptionRequestDto prescriptionRequestDto = new PrescriptionRequestDto(
      content, quantite, idPrescription, idHP, idPatient);
    return healthProfessionalBusinessController.addPrescription(prescriptionRequestDto);
  }

  public void removeHealthProfessional(final String name, final String surname, final String rpps,
  final String speciality) throws NameNotFoundException {
    HealthProfessionalRequestDto healthProfessionalRequestDto = new HealthProfessionalRequestDto(
      name, surname, rpps, speciality);
    healthProfessionalRessourceController.removeHealthProfessional(healthProfessionalRequestDto);
  }

  public boolean removeHealthProfessionalById(final String healthProfessionalId) throws 
    NameNotFoundException {
    return healthProfessionalRessourceController.removeHealthProfessionalById(healthProfessionalId);
  }

  public void removePatient(final String name, final String surname, final String ssid,
      final String adress, final String city) throws NameNotFoundException {
    PatientRequestDto patientRequestDto = new PatientRequestDto(name, surname, ssid, adress, city);
    patientRessourceController.removePatient(patientRequestDto);
  }

  public boolean removePatientById(final String patientId) throws NameNotFoundException {
    return patientRessourceController.removePatientById(patientId);
  }

  public boolean removePrescriptionFromHP(final String prescriptionId) throws 
      NameNotFoundException, InvalidNameException {
    return healthProfessionalBusinessController.removePrescription(prescriptionId);
  }

  public boolean removePrescriptionFromPatient(final String prescriptionId, final String patientId) throws 
    NameNotFoundException, InvalidNameException, IllegalAccessException {
    return patientBusinessController.removePrescription(prescriptionId, patientId);
  }

  public boolean updateHealthProfessional(final String name, final String surname, final String rpps,
      final String speciality) {
    HealthProfessionalRequestDto healthProfessionalRequestDto = new HealthProfessionalRequestDto(
      name, surname, rpps, speciality);
    return healthProfessionalRessourceController.updateHealthProfessional(healthProfessionalRequestDto);
  }

  public boolean updatePatient(final String name, final String surname, final String ssid,
      final String adress, final String city) {
    PatientRequestDto patientRequestDto = new PatientRequestDto(name, surname, ssid, adress, city);
    return patientRessourceController.updatePatient(patientRequestDto);
  }

  public HealthProfessional getHealthProfessional(final String healthProfessionalId) throws NameNotFoundException {
    return healthProfessionalRessourceController.getHealthProfessional(healthProfessionalId);
  }

  public Collection<HealthProfessional> getHealthProfessionals() {
    return healthProfessionalRessourceController.getHealthProfessionals();
  }

  public Patient getPatientBySSID(final String ssid) throws NameNotFoundException, InvalidNameException {
    return healthProfessionalBusinessController.getPatientBySSID(ssid);
  }

  public Patient getPatientInfos(final String patientId) throws NameNotFoundException, 
    InvalidNameException {
    return healthProfessionalBusinessController.getPatientInfos(patientId);
  }

  public Patient getPatient(final String patientId) throws NameNotFoundException {
    return patientRessourceController.getPatient(patientId);
  }

  public Collection<Patient> getPatients() {
    return patientRessourceController.getPatients();
  }

  public List<Prescription> getPrescriptionFromAPatient(final String name, final String surname, final String ssid,
  final String adress, final String city) throws NameNotFoundException {
    PatientRequestDto patientRequestDto = new PatientRequestDto(name, surname, ssid, adress, city);
    return patientBusinessController.getPrescriptionsPatient(patientRequestDto);
  }
}
