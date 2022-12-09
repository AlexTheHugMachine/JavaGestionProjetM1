package fr.univ_lyon1.info.m1.mes.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.univ_lyon1.info.m1.mes.builders.Patient.PatientBuilder;
import fr.univ_lyon1.info.m1.mes.controller.PatientRessourceController;
import fr.univ_lyon1.info.m1.mes.daos.PatientDAO;
import fr.univ_lyon1.info.m1.mes.dto.patient.PatientRequestDto;
import fr.univ_lyon1.info.m1.mes.model.Patient.Patient;

public class PatientRessourceControllerTest {

    private PatientRessourceController patientRessourceController;
    private Patient john;
    private Patient doe;
    private Patient jack;
    private Patient james;
    private Patient eric;
    @BeforeEach
    void setup() {
        PatientDAO patientDAO = new PatientDAO();
        PatientBuilder builder = new PatientBuilder();
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
        patientRessourceController = new PatientRessourceController(patientDAO, builder);
    }

    @Test
    void getPatientgetProperlyPatients() {
        try {
            assertEquals(john, patientRessourceController.getPatient("6968686787598"));
            assertEquals(doe, patientRessourceController.getPatient("7912327085687"));
            assertEquals(jack, patientRessourceController.getPatient("0975310954209"));
            assertEquals(james, patientRessourceController.getPatient("1678966979912"));
            assertEquals(eric, patientRessourceController.getPatient("0862183792365"));
        } catch (NameNotFoundException e) {
            fail("Should not throw NameNotFoundException");
        }
    }

    @Test
    void getPatientThrowsNameNotFoundExceptionWhenTheSSIDisWrong() {
        try {
            patientRessourceController.getPatient("123456789");
            fail("Should not found the patient.");
        } catch (NameNotFoundException e) {
            assertEquals("No patient found.", e.getMessage());
        }
    }

    @Test
    void getPatientsReturnsAllPatients() {
        assertEquals(5, patientRessourceController.getPatients().size());
    }

    @Test
    void createPatientProperlyCreatesPatient() {
        PatientRequestDto newPatient = new PatientRequestDto(
            "Maurice", "LaSaucisse", "1678988889912", "", "");
        String ssid;
        try {
            ssid = patientRessourceController.createPatient(newPatient);
            try {
                Patient patient = patientRessourceController.getPatient(ssid);
                assertEquals("1678988889912", ssid);
                assertEquals(newPatient.getName(), patient.getName());
                assertEquals(newPatient.getSurname(), patient.getSurname());
                assertEquals(newPatient.getSsID(), patient.getSsID());
              } catch (NameNotFoundException e) {
                fail("The health professional was not created properly");
              }
        } catch (NameAlreadyBoundException e1) {
            fail("The patient does not exist yet.");
        }
    }

    @Test
    void createPatientThrowsNameAlreadyBoundExceptionWhenSSIDAlreadyExists() {
        PatientRequestDto newPatient = new PatientRequestDto(
            "Eric", "Zemmour", "0862183792365", "", "");
        try {
            patientRessourceController.createPatient(newPatient);
            fail("Should not create the patient.");
        } catch (NameAlreadyBoundException e) {
            assertEquals("This patient already exist.", e.getMessage());
        }
    }

    @Test
    void updatePatientProperlyUpdatesPatient() {
        String ssidOfEric = eric.getSsID();
        PatientRequestDto patientRequest = new PatientRequestDto(
            "Maurice", "LaSaucisse", "0862183792365", "", "");
        patientRessourceController.updatePatient(patientRequest);
        Patient patient;
        try {
            patient = patientRessourceController.getPatient(ssidOfEric);
            assertEquals(patientRequest.getName(), patient.getName());
            assertEquals(patientRequest.getSurname(), patient.getSurname());
            assertEquals(patientRequest.getSsID(), patient.getSsID());
        } catch (NameNotFoundException e) {
            fail("The patient should be found.");
        }
    }

    @Test
    void updatePatientThrowsNameNotFoundExceptionWhenSSIDDoesNotExist() {
        PatientRequestDto patientRequest = new PatientRequestDto(
            "Maurice", "LaSaucisse", "123456789", "", "");
        try {
            patientRessourceController.updatePatient(patientRequest);
            fail("Should not update the patient.");
        } catch (IllegalArgumentException e) {
            assertEquals("Les informations du patient sont invalides.", e.getMessage());
        }
    }

    @Test
    void removePatientByIdProperlyRemovePatient() {
        String ssidOfEric = eric.getSsID();
        try {
            patientRessourceController.removePatientById(ssidOfEric);
        } catch (NameNotFoundException e1) {
            fail("The patient should be found.");
        }
        try {
            patientRessourceController.getPatient(ssidOfEric);
            fail("Should not found the patient.");
        } catch (NameNotFoundException e) {
            assertEquals("No patient found.", e.getMessage());
        }
    }

    @Test
    void removePatientByIdThrowsNameNotFoundExceptionWhenSSIDDoesNotExist() {
        try {
            patientRessourceController.removePatientById("123456789");
            fail("Should not remove the patient.");
        } catch (NameNotFoundException e) {
            assertEquals("This patient does not exist.", e.getMessage());
        }
    }

    @Test
    void removePatientProperlyRemovePatient() {
        PatientRequestDto ericDto = new PatientRequestDto(
            "Eric", "Zemmour", "0862183792365", "", "");
        try {
            patientRessourceController.removePatient(ericDto);
        } catch (NameNotFoundException e1) {
            fail("The patient should be found.");
        }
        try {
            patientRessourceController.getPatient(eric.getSsID());
            fail("Should not found the patient.");
        } catch (NameNotFoundException e) {
            assertEquals("No patient found.", e.getMessage());
        }
    }

    @Test
    void removePatientThrowsNameNotFoundExceptionWhenSSIDDoesNotExist() {
        PatientRequestDto ericDto = new PatientRequestDto(
            "Eric", "Zemmour", "123456789", "", "");
        try {
            patientRessourceController.removePatient(ericDto);
            fail("Should not remove the patient.");
        } catch (NameNotFoundException e) {
            assertEquals("This patient does not exist.", e.getMessage());
        }
    }
}
