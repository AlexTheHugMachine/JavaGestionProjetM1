package fr.univ_lyon1.info.m1.mes.controllers;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.Collection;

import javax.naming.InvalidNameException;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;

import org.hamcrest.Matchers;
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
    void getPatientsReturnsAllPatients() {
        assertEquals(5, mesController.getPatients().size());
    }

    @Test
    void getHealthProfessionalProperlyGetTheHealthProfessional() {
        try {
            assertEquals(lebron, mesController.getHealthProfessionalById("12345678919"));
        } catch (NameNotFoundException e) {
            fail("The HealthProfessional was added during setup so it should not throw this.");
        }
    }

    @Test
    void getHealthProfessionalThrowExceptionWhenHealthProfessionalNotFound() {
        try {
            mesController.getHealthProfessionalById("12345678901");
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
            Collection<Prescription> listOfPrescriptions = mesController
                    .getPrescriptionFromAPatient("6968686787598");
            Collection<Prescription> expectedList = new ArrayList<Prescription>();
            expectedList.add(eatFruit);
            expectedList.add(doliprane500);
            assertAll(
                    () -> assertEquals(2, listOfPrescriptions.size()),
                    () -> assertThat(listOfPrescriptions,
                            Matchers.containsInAnyOrder(expectedList.toArray())));

        } catch (NameNotFoundException e) {
            fail("Should not throw this exception, these prescriptions have been added.");
        }
    }

    @Test
    void getPrescriptionFromAPatientThrowExceptionWhenPatientSSIDisWrong() {
        try {
            mesController.getPrescriptionFromAPatient("12345678901");
        } catch (NameNotFoundException e) {
            assertTrue(true, "SSID is not valid and shouldn't be able to exist.");
        }
    }

    @Test
    void getPrescriptionFromAPatientThrowExceptionWhenPatientNotFound() {
        try {
            mesController.getPrescriptionFromAPatient("6968686787599");
        } catch (NameNotFoundException e) {
            assertEquals("No prescriptions have been found.", e.getMessage());
        }
    }

    @Test
    void updatePatientProperlyUpdateThePatient() {
        String ssidOfEric = eric.getSsID();
        mesController.updatePatient(
                "Maurice", "LaSaucisse", "0862183792365", "", "");
        Patient patient;
        try {
            patient = mesController.getPatient(ssidOfEric);
            assertEquals("Maurice", patient.getName());
            assertEquals("LaSaucisse", patient.getSurname());
            assertEquals("0862183792365", patient.getSsID());
        } catch (NameNotFoundException e) {
            fail("The patient should be found.");
        }
    }

    @Test
    void updatePatientThrowExceptionWhenPatientSSIDisWrong() {
        try {
            mesController.updatePatient(
                    "Maurice", "LaSaucisse", "12345678901", "", "");
        } catch (IllegalArgumentException e) {
            assertEquals("Les informations du patient sont invalides.", e.getMessage());
        }
    }

    @Test
    void updateHealthProfessionalProperlyUpdateHP() {
        String ssidOfLebron = lebron.getRPPS();
        mesController.updateHealthProfessional(
                "Magic", "James", "12345678919", HPSpeciality.CHIRURGIEN.toString());
        HealthProfessional hp;
        try {
            hp = mesController.getHealthProfessionalById(ssidOfLebron);
            assertEquals("Magic", hp.getName());
            assertEquals("12345678919", hp.getRPPS());
        } catch (NameNotFoundException e) {
            fail("The health professional should be found.");
        }
    }

    @Test
    void updateHealthProfessionalThrowExceptionWhenHPSSIDisWrong() {
        try {
            mesController.updateHealthProfessional(
                    "Magic", "James", "12345678901", HPSpeciality.CHIRURGIEN.toString());
        } catch (IllegalArgumentException e) {
            assertEquals(
                    "Les informations du professionnel de santé sont invalides.", e.getMessage());
        }
    }

    @Test
    void removeHealthProfessionalByIdProperlyRemoveTheHP() {
        try {
            mesController.removeHealthProfessionalById("12345678919");
        } catch (NameNotFoundException e1) {
            fail("The health professional should be found.");
        }
        try {
            mesController.getHealthProfessionalById("12345678919");
        } catch (NameNotFoundException e) {
            assertEquals("No health professional found.", e.getMessage());
        }
    }

    @Test
    void removeHealthProfessionalByIdThrowExceptionWhenHPNotFound() {
        try {
            mesController.removeHealthProfessionalById("12345678901");
        } catch (NameNotFoundException e) {
            assertEquals("This Health Professinal does not exist.", e.getMessage());
        }
    }

    @Test
    void removeHealthProfessionalProperlyRemoveTheHP() {
        try {
            mesController.removeHealthProfessional(
                    "Lebron", "James", "12345678919", HPSpeciality.CHIRURGIEN.toString());
        } catch (NameNotFoundException e1) {
            fail("The health professional should be found.");
        }
        try {
            mesController.getHealthProfessionalById("12345678919");
        } catch (NameNotFoundException e) {
            assertEquals("No health professional found.", e.getMessage());
        }
    }

    @Test
    void removeHealthProfessionalThrowExceptionWhenHPNotFound() {
        try {
            mesController.removeHealthProfessional(
                    "Lebron", "James", "12345678901", HPSpeciality.CHIRURGIEN.toString());
        } catch (NameNotFoundException e) {
            assertEquals("This Health Professional does not exist.", e.getMessage());
        }
    }

    @Test
    void removePatientByIdProperlyRemoveThePatient() {
        try {
            mesController.removePatientById("6968686787598");
        } catch (NameNotFoundException e1) {
            fail("The patient should be found.");
        }
        try {
            mesController.getPatient("6968686787598");
        } catch (NameNotFoundException e) {
            assertEquals("No patient found.", e.getMessage());
        }
    }

    @Test
    void removePatientByIdThrowExceptionWhenPatientNotFound() {
        try {
            mesController.removePatientById("12345678901");
        } catch (NameNotFoundException e) {
            assertEquals("This patient does not exist.", e.getMessage());
        }
    }

    @Test
    void removePatientProperlyRemoveThePatient() {
        try {
            mesController.removePatient(
                    "John", "Wick", "6968686787598", "", "");
        } catch (NameNotFoundException e1) {
            fail("The patient should be found.");
        }
        try {
            mesController.getPatient("6968686787598");
        } catch (NameNotFoundException e) {
            assertEquals("No patient found.", e.getMessage());
        }
    }

    @Test
    void removePatientThrowExceptionWhenPatientNotFound() {
        try {
            mesController.removePatient(
                    "John", "Wick", "12345678901", "", "");
        } catch (NameNotFoundException e) {
            assertEquals("This patient does not exist.", e.getMessage());
        }
    }

    @Test
    void removePrescriptionFromHPProperlyRemoveThePrescription() {
        try {
            assertTrue(mesController.removePrescriptionFromHP(doliprane500.getId()));
        } catch (NameNotFoundException e1) {
            fail("The health professional should be found.");
        } catch (InvalidNameException e) {
            fail("The prescription should be found.");
        }
    }

    @Test
    void removePrescriptionFromHPThrowExceptionWhenHPNotFound() {
        try {
            mesController.removePrescriptionFromHP("12345678901");
        } catch (NameNotFoundException e) {
            assertEquals("No ressource found.", e.getMessage());
        } catch (InvalidNameException e) {
            fail("The prescription should be found.");
        }
    }

    @Test
    void removePrescriptionFromPatientProperlyRemoveThePrescription() {
        try {
            assertTrue(
                    mesController.removePrescriptionFromPatient(
                            doliprane500.getId(), "6968686787598"));
        } catch (NameNotFoundException e1) {
            fail("The patient should be found.");
        } catch (InvalidNameException e) {
            fail("The prescription should be found.");
        } catch (IllegalAccessException e) {
            fail("The patient should be found.");
        }
    }

    @Test
    void removePrescriptionFromPatientThrowExceptionWhenPatientSSIDisNotTheGoodOne() {
        try {
            mesController.removePrescriptionFromPatient(doliprane500.getId(), "1234567890987");
        } catch (NameNotFoundException e) {
            fail("Should be an IllegalAccessException.");
        } catch (InvalidNameException e) {
            fail("Should be an IllegalAccessException.");
        } catch (IllegalAccessException e) {
            assertEquals("This prescription is not assign to you.", e.getMessage());
        }
    }

    @Test
    void addPatientFromHPproperlyAddThePatient() {
        try {
            mesController.addPatientFromHP(
                    "Maurice", "LaSaucisse", "6968686788888", "", "");
        } catch (NameAlreadyBoundException e1) {
            fail("The health professional should be found.");
        }
        Patient patient;
        try {
            patient = mesController.getPatient("6968686788888");
            assertEquals("Maurice", patient.getName());
            assertEquals("6968686788888", patient.getSsID());
        } catch (NameNotFoundException e) {
            fail("Should not throw this exception, this patient has been added.");
        }
    }

    @Test
    void addPatientFromHPThrowExceptionWhenPatientAlreadyExist() {
        try {
            mesController.addPatientFromHP(
                    "John", "Wick", "6968686787598", "", "");
        } catch (NameAlreadyBoundException e) {
            assertEquals("Patient already exists.", e.getMessage());
        }
    }

    @Test
    void addPatientFromPatientProperlyAddThePatient() {
        try {
            mesController.addPatientFromPatient(
                    "Maurice", "LaSaucisse", "6968686788888", "", "");
        } catch (NameAlreadyBoundException e1) {
            fail("The health professional should be found.");
        }
        Patient patient;
        try {
            patient = mesController.getPatient("6968686788888");
            assertEquals("Maurice", patient.getName());
            assertEquals("6968686788888", patient.getSsID());
        } catch (NameNotFoundException e) {
            fail("Should not throw this exception, this patient has been added.");
        }
    }

    @Test
    void addPatientFromPatientThrowExceptionWhenPatientAlreadyExist() {
        try {
            mesController.addPatientFromPatient(
                    "John", "Wick", "6968686787598", "", "");
        } catch (NameAlreadyBoundException e) {
            assertEquals("This ressource already exist.", e.getMessage());
        }
    }

    @Test
    void addHealthProfessionalProperlyAddTheHP() {
        try {
            mesController.addHealthProfessional(
                    "Maurice", "LaSaucisse", "12345678901", HPSpeciality.CHIRURGIEN.toString());
        } catch (NameAlreadyBoundException e1) {
            fail("The health professional should be found.");
        }
        HealthProfessional hp;
        try {
            hp = mesController.getHealthProfessionalById("12345678901");
            assertEquals("Maurice", hp.getName());
            assertEquals("12345678901", hp.getRPPS());
        } catch (NameNotFoundException e) {
            fail("Should not throw this exception, this health professional has been added.");
        }
    }

    @Test
    void addHealthProfessionalThrowExceptionWhenHPAlreadyExist() {
        try {
            mesController.addHealthProfessional(
                    "Lebron", "James", "12345678901", HPSpeciality.CHIRURGIEN.toString());
        } catch (NameAlreadyBoundException e) {
            assertEquals("This health professional already exist.", e.getMessage());
        }
    }
}
