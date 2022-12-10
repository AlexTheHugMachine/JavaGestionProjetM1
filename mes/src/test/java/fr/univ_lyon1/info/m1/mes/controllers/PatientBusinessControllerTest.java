package fr.univ_lyon1.info.m1.mes.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import javax.naming.InvalidNameException;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.univ_lyon1.info.m1.mes.controller.PatientsBusinessController;
import fr.univ_lyon1.info.m1.mes.daos.PatientDAO;
import fr.univ_lyon1.info.m1.mes.daos.PrescriptionDAO;
import fr.univ_lyon1.info.m1.mes.dto.patient.PatientRequestDto;
import fr.univ_lyon1.info.m1.mes.model.Patient.Patient;
import fr.univ_lyon1.info.m1.mes.model.Prescription.Prescription;

public class PatientBusinessControllerTest {

    private PatientsBusinessController patientBusinessController;

    private Prescription doliprane500;
    private Prescription doliprane1000;
    private Prescription paracetamol;
    private Prescription eatFruit;
    private Patient john;
    private Patient doe;
    private Patient jack;
    private Patient james;
    private Patient eric;

    @BeforeEach
    void setUp() {
        // In the real app we would pass this prescription like that we wouldn't create
        // Another one because it wouldn't make sense.
        PrescriptionDAO prescriptionDAO = new PrescriptionDAO();
        doliprane500 = new Prescription("Doliprane", "500g", "12345678901", "6968686787598");
        doliprane1000 = new Prescription("Doliprane", "1000g", "12345678901", "1234567890987");
        paracetamol = new Prescription("Paracetamol", "1000mg", "12345678901", "1234567890987");
        eatFruit = new Prescription("Fruit", "1000g", "12345678909", "6968686787598");
        try {
            prescriptionDAO.add(doliprane500);
            prescriptionDAO.add(doliprane1000);
            prescriptionDAO.add(paracetamol);
            prescriptionDAO.add(eatFruit);
        } catch (NameAlreadyBoundException e) {
            e.printStackTrace();
        }
        PatientDAO patientDAO = new PatientDAO();
        john = new Patient("John", "Wick", "6968686787598", "", "");
        doe = new Patient("John", "Doe", "7912327085687", "", "");
        jack = new Patient("Jack", "Sparrow", "0975310954209", "", "");
        james = new Patient("James", "Lebron", "1678966979912", "", "");
        eric = new Patient("Eric", "Zemmour", "0862183792365", "", "");
        try {
            patientDAO.add(john);
            patientDAO.add(doe);
            patientDAO.add(jack);
            patientDAO.add(james);
            patientDAO.add(eric);
        } catch (NameAlreadyBoundException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        patientBusinessController = new PatientsBusinessController(prescriptionDAO, patientDAO);
    }

    @Test
    void getPrescriptionsPatientReturnTheListOfPrescriptionsOfAPatient() {
        PatientRequestDto patientRequestDto = new PatientRequestDto(
                "John", "Wick", "6968686787598", "", "");
        try {
            List<Prescription> prescriptions;
            prescriptions = patientBusinessController.getPrescriptionsPatient(patientRequestDto);
            assertEquals(2, prescriptions.size());
            assertTrue(prescriptions.contains(doliprane500));
            assertTrue(prescriptions.contains(eatFruit));
        } catch (NameNotFoundException e) {
            fail("The patient has prescriptions.");
        }
    }

    @Test
    void getPrescriptionsPatientReturnAnEmptyListIfThePatientHasNoPrescription() {
        PatientRequestDto patientRequestDto = new PatientRequestDto(
                "John", "Doe", "7912327085687", "", "");
        try {
            List<Prescription> prescriptions;
            prescriptions = patientBusinessController.getPrescriptionsPatient(patientRequestDto);
        } catch (NameNotFoundException e) {
            assertEquals("No prescriptions have been found.", e.getMessage());
        }
    }

    @Test
    void removePrescriptionPatientRemoveThePrescriptionFromThePatient() {
        PatientRequestDto patientRequestDto = new PatientRequestDto(
                "John", "Wick", "6968686787598", "", "");
        try {
            patientBusinessController.removePrescription(doliprane500.getId(), john.getSsID());
        } catch (NameNotFoundException | InvalidNameException | IllegalAccessException e) {
            fail("The prescription should be found.");
        }
        List<Prescription> prescriptions;
        try {
            prescriptions = patientBusinessController.getPrescriptionsPatient(patientRequestDto);
            assertEquals(1, prescriptions.size());
            assertTrue(prescriptions.contains(eatFruit));
        } catch (NameNotFoundException e) {
            fail("The patient has prescriptions.");
        }
    }

    @Test
    void removePrescriptionPatientThrowAnExceptionIfThePrescriptionDoesntExist() {
        try {
            patientBusinessController.removePrescription("12345678901", john.getSsID());
        } catch (NameNotFoundException e) {
            assertEquals("No prescription found for this id.", e.getMessage());
        } catch (InvalidNameException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
