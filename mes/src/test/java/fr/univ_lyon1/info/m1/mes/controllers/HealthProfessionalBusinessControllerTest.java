package fr.univ_lyon1.info.m1.mes.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.naming.InvalidNameException;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.univ_lyon1.info.m1.mes.controller.HealthProfessionnalBusinessController;
import fr.univ_lyon1.info.m1.mes.daos.HealthProfessionalDAO;
import fr.univ_lyon1.info.m1.mes.daos.PatientDAO;
import fr.univ_lyon1.info.m1.mes.daos.PrescriptionDAO;
import fr.univ_lyon1.info.m1.mes.dto.patient.PatientRequestDto;
import fr.univ_lyon1.info.m1.mes.dto.prescription.PrescriptionRequestDto;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HPSpeciality;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HealthProfessional;
import fr.univ_lyon1.info.m1.mes.model.Patient.Patient;
import fr.univ_lyon1.info.m1.mes.model.Prescription.Prescription;

public class HealthProfessionalBusinessControllerTest {
    
    private Patient john;
    private Patient jack;

    private HealthProfessional lebron;

    private Prescription doliprane500;
    private Prescription doliprane1000;
    private Prescription paracetamol;
    private Prescription eatFruit;

    private HealthProfessionnalBusinessController hpBusinessController; 
    @BeforeEach
    void setUp() {
        PatientDAO patientDAO = new PatientDAO();
        PrescriptionDAO prescriptionDAO = new PrescriptionDAO();
        HealthProfessionalDAO hpDAO = new HealthProfessionalDAO();    
        john = new Patient("John", "Wick", "6968686787598", "", "");
        jack = new Patient("Jack", "Sparrow", "0975310954209", "", "");
        lebron = new HealthProfessional("Lebron", "James", "12345678919", HPSpeciality.CHIRURGIEN);   
        doliprane500 = new Prescription("Doliprane", "500mg", "12345678901", "6968686787598");
        paracetamol = new Prescription("Paracetamol", "1000mg", "12345678901", "6968686787598");
        doliprane1000 = new Prescription("Doliprane", "1000mg", "12345678901", "0975310954209");
        eatFruit = new Prescription("Eat Fruit", "", "12345678901", "0975310954209"); 
        try {
            patientDAO.add(john);
            patientDAO.add(jack);
            prescriptionDAO.add(doliprane500);
            prescriptionDAO.add(paracetamol);
            prescriptionDAO.add(doliprane1000);
            prescriptionDAO.add(eatFruit);
            hpDAO.add(lebron);
        } catch (NameAlreadyBoundException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } 
        hpBusinessController = new HealthProfessionnalBusinessController(
            hpDAO,
            patientDAO,
            prescriptionDAO);
    }

    @Test
    void getPatientBySSIDProperly() throws NameNotFoundException, InvalidNameException {
        Patient patient = hpBusinessController.getPatientBySSID("6968686787598");
        assertEquals(john, patient);
    }

    @Test
    void getPatientBySSIDThrowsNameNotFoundExceptionWhenSSIDisInvalid() {
        try {
            hpBusinessController.getPatientBySSID("6968686787599");
        } catch (NameNotFoundException e) {
            assertEquals("Patient not found.", e.getMessage());
        } catch (InvalidNameException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getPatientInfosProperly() throws NameNotFoundException, InvalidNameException {
        Patient patient = hpBusinessController.getPatientInfos("6968686787598");
        assertEquals(john, patient);
    }

    @Test
    void getPatientInfosThrowsNameNotFoundExceptionWhenSSIDisInvalid() {
        try {
            hpBusinessController.getPatientInfos("6968686787599");
        } catch (NameNotFoundException e) {
            assertEquals("No patient found.", e.getMessage());
        } catch (InvalidNameException e) {
            e.printStackTrace();
        }
    }

    /*@Test
    void addPrescriptionProperlyAddPrescritptionToPatient() throws NameNotFoundException, InvalidNameException {
        PrescriptionRequestDto prescriptionDto = new PrescriptionRequestDto(
            "Doliprane", 
            "500mg", 
            "12345678902",
            "12345678901", 
            "6968686787598");
        hpBusinessController.addPrescription(prescriptionDto);
        Patient patient = hpBusinessController.getPatientInfos("6968686787598");
        assertEquals(3, patient.getPrescriptions().size());
    }*/

    @Test
    void addPrescriptionThrowsNameNotFoundExceptionWhenSSIDisInvalid() throws NameAlreadyBoundException {
        PrescriptionRequestDto prescriptionDto = new PrescriptionRequestDto(
            "Doliprane", 
            "500mg", 
            "12345678902",
            "12345678901", 
            "0975310954209");
        try {
            hpBusinessController.addPrescription(prescriptionDto);
        } catch (NameNotFoundException e) {
            assertEquals("No patient or health professional found.", e.getMessage());
        } catch (InvalidNameException e) {
            e.printStackTrace();
        }
    }

    /*@Test
    void removePrescriptionProperlyRemove() throws NameNotFoundException, InvalidNameException {
        hpBusinessController.removePrescription(doliprane500.getId());
        Patient patient = hpBusinessController.getPatientInfos("6968686787598");
        assertEquals(1, patient.getPrescriptions().size());
    }*/

    @Test
    void createPatientProperlyCreateAPatient() throws NameAlreadyBoundException, NameNotFoundException, InvalidNameException {
        PatientRequestDto patientDto = new PatientRequestDto(
            "Maurice", "LaSaucisse", "6968686788888", "", "");
        hpBusinessController.createPatient(patientDto);
        Patient patient2 = hpBusinessController.getPatientBySSID("6968686788888");
        assertEquals(patientDto.getSSID(), patient2.getSSID());
    }

    @Test   
    void createPatientThrowsNameAlreadyBoundExceptionWhenSSIDisAlreadyUsed() {
        PatientRequestDto patientDto = new PatientRequestDto(
            "Maurice", "LaSaucisse", "6968686787598", "", "");
        try {
            hpBusinessController.createPatient(patientDto);
        } catch (NameAlreadyBoundException e) {
            assertEquals("Patient already exists.", e.getMessage());
        }
    }
}
