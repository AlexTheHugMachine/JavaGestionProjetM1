package fr.univ_lyon1.info.m1.mes.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import javax.naming.InvalidNameException;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.univ_lyon1.info.m1.mes.builders.HealthProfessionnal.HealthProfessionalBuilder;
import fr.univ_lyon1.info.m1.mes.builders.Patient.PatientBuilder;
import fr.univ_lyon1.info.m1.mes.controller.MESController;
import fr.univ_lyon1.info.m1.mes.daos.HealthProfessionalDAO;
import fr.univ_lyon1.info.m1.mes.daos.PatientDAO;
import fr.univ_lyon1.info.m1.mes.daos.PrescriptionDAO;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HPSpeciality;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HealthProfessional;
import fr.univ_lyon1.info.m1.mes.model.Patient.Patient;
import fr.univ_lyon1.info.m1.mes.model.Prescription.Prescription;

public class MESControllerTest {

    private MESController mesController;

    private Prescription doliprane500;
    private Prescription doliprane1000;
    private Prescription paracetamol;
    private Prescription eatFruit;
    private Patient john;
    private Patient doe;
    private Patient jack;
    private Patient james;
    private Patient eric;
    private HealthProfessional lebron;

    @BeforeEach
    void setUp() {
        // In the real app we would pass this prescription like that we wouldn't create
        // Another one because it wouldn't make sense.
        PrescriptionDAO prescriptionDAO = new PrescriptionDAO();
        PatientDAO patientDAO = new PatientDAO();
        HealthProfessionalDAO hpDAO = new HealthProfessionalDAO();
        HealthProfessionalBuilder hpBuilder = new HealthProfessionalBuilder();
        PatientBuilder patientBuilder = new PatientBuilder();

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

        lebron = new HealthProfessional("Lebron", "James", "12345678919", HPSpeciality.CHIRURGIEN);
        try {
            hpDAO.add(lebron);
        } catch (NameAlreadyBoundException e) {
            e.printStackTrace();
        }

        mesController = new MESController(
                patientDAO,
                hpDAO,
                prescriptionDAO,
                patientBuilder,
                hpBuilder);
    }

    @Test
    void getPatientProperlyGetThePatient() {
        try {
            assertEquals(john, mesController.getPatient("6968686787598"));
            assertEquals(doe, mesController.getPatient("7912327085687"));
            assertEquals(jack, mesController.getPatient("0975310954209"));
            assertEquals(james, mesController.getPatient("1678966979912"));
            assertEquals(eric, mesController.getPatient("0862183792365"));
        } catch (NameNotFoundException e) {
            fail("Patient was added in the setUp section so it should not throw this error.");
        }
    }

    @Test
    void getPatientThrowExceptionWhenPatientNotFound() {
        try {
            mesController.getPatient("12345678901");
        } catch (NameNotFoundException e) {
            assertEquals("No patient found.", e.getMessage());
        }
    }

    @Test
    void getPatientBySSIDProperlyGetThePatient() {
        try {
            assertEquals(john, mesController.getPatientBySSID("6968686787598"));
        } catch (NameNotFoundException | InvalidNameException e) {
            fail("Meh");
        }
    }

    @Test
    void getPatientBySSIDThrowExceptionWhenPatientNotFound() {
        try {
            mesController.getPatientBySSID("12345678901");
        } catch (NameNotFoundException e) {
            assertEquals("No patient found.", e.getMessage());
        } catch (InvalidNameException e) {
            fail("Should not throw this exception.");
        }
    }

    @Test
    void getPatientInfosProperlyGetInfoOfThePatient() {
        try {
            assertEquals(john, mesController.getPatientInfos("6968686787598"));
        } catch (NameNotFoundException | InvalidNameException e) {
            fail("Patient was actually ");
        }
    }

    @Test
    void getPatientInfosThrowExceptionWhenPatientNotFound() {
        try {
            mesController.getPatientInfos("12345678901");
        } catch (NameNotFoundException e) {
            assertEquals("No patient found.", e.getMessage());
        } catch (InvalidNameException e) {
            fail("Should not throw this exception.");
        }
    }

    @Test
    void getPatientsReturnsAllPatients() {
        assertEquals(5, mesController.getPatients().size());
    }

    @Test
    void getHealthProfessionalProperlyGetTheHealthProfessional() {
        try {
            assertEquals(lebron, mesController.getHealthProfessional("12345678919"));
        } catch (NameNotFoundException e) {
            fail("The HealthProfessional was added during setup so it should not throw this.");
        }
    }

    @Test
    void getHealthProfessionalThrowExceptionWhenHealthProfessionalNotFound() {
        try {
            mesController.getHealthProfessional("12345678901");
        } catch (NameNotFoundException e) {
            assertEquals("No health professional found.", e.getMessage());
        }
    }

    @Test
    void getHealthProfessionalsReturnsAllHealthProfessionals() {
        assertEquals(1, mesController.getHealthProfessionals().size());
    }

    @Test
    void getPrescriptionFromAPatientProperlyGetTheListOfPrescriptions() {
        try {
            assertEquals(2, mesController.getPrescriptionFromAPatient(
                    "John", "Wick", "6968686787598", "", "").size());
        } catch (NameNotFoundException e) {
            fail("Should not throw this exception, these prescriptions have been added.");
        }
    }

    @Test
    void getPrescriptionFromAPatientThrowExceptionWhenPatientSSIDisWrong() {
        try {
            mesController.getPrescriptionFromAPatient(
                    "John", "Wick", "12345678901", "", "");
        } catch (IllegalArgumentException e) {
            assertEquals("Les informations du patient sont invalides.", e.getMessage());
        } catch (NameNotFoundException e) {
            fail("Should not throw this exception because the SSID is not valid.");
        }
    }

    @Test
    void getPrescriptionFromAPatientThrowExceptionWhenPatientNotFound() {
        try {
            mesController.getPrescriptionFromAPatient(
                    "John", "Wick", "6968686787599", "", "");
        } catch (NameNotFoundException e) {
            assertEquals("No prescriptions have been found.", e.getMessage());
        }
    }
}
