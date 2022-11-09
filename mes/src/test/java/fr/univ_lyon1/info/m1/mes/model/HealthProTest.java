package fr.univ_lyon1.info.m1.mes.model;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;

import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HPSpeciality;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HealthProfessional;
import fr.univ_lyon1.info.m1.mes.model.Patient.Patient;
import fr.univ_lyon1.info.m1.mes.model.Prescription.Prescription;
import fr.univ_lyon1.info.m1.mes.daos.PatientDAO;
import fr.univ_lyon1.info.m1.mes.daos.PrescriptionDAO;

public class HealthProTest {
    private HealthProfessional hp;
    private PrescriptionDAO prescriptionDAO;
    private PatientDAO patientDAO;

    @BeforeEach
    public void setUp() {
        hp = new HealthProfessional(
                "Dr. Smith", "Richard", HPSpeciality.GENERALISTE);
        prescriptionDAO = new PrescriptionDAO();
        patientDAO = new PatientDAO();
    }

    /**
     * Test that the name given to an HealthProfessionnal during the creation of the
     * object is correct.
     */
    @Test
    @DisplayName("Tests if the name and surname of the professionnal are correct")
    public void healthProfessionalCorrectNameAndSurname() {

        String name = hp.getName();
        String surname = hp.getSurname();

        assertThat(name, is("Dr. Smith"));
        assertThat(surname, is("Richard"));
    }

    @Test
    @DisplayName("Tests if the professionnal's ID is not null or empty")
    public void healthProfessionnalUIDIsNotNullAndEmpty() {
        // Given HP
        // When
        String hpUID = hp.getId();
        // Then
        assertNotEquals(hpUID, null);
        assertNotEquals(hpUID, "");
    }

    @Test
    @DisplayName("Tests if the professionnal's name is valid")
    public void checkThatHandleIncorrectHealthProfessionnalName() {
        // Given
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new HealthProfessional(
                    "876567827adsqsd", "George", HPSpeciality.GENERALISTE);
        });
        // When
        String expectedMessage = "Le nom du professionnel de santé n'est pas valide.";
        String actualMessage = exception.getMessage();
        // Then
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Tests if the patient has a prescription that is added with his ID")
    public void addPrescriptionToAPatientUsingHisId() throws NameAlreadyBoundException {
        // Given
        Patient p = new Patient("Enzo", "CECILLON", "2200767612837");
        prescriptionDAO.add(new Prescription("Do some sport", hp.getId(), p.getId()));
        // When
        List<Prescription> prescriptions = hp.getPrescriptions(prescriptionDAO, p.getId());
        // Then
        assertThat(prescriptions, hasItem(
                hasProperty("content", equalTo("Do some sport"))));
    }

    @Test
    @DisplayName("Tests if the added prescription is linked to the right patient")
    public void checkThatAPrescriptionIsGivenToTheRightPatient() throws NameAlreadyBoundException {
        // Given
        Patient p = new Patient("Alice", "WONDERLAND", "20123456789012");
        Patient p2 = new Patient("Enzo", "CECILLON", "20123456789012");

        prescriptionDAO.add(new Prescription("Do some sport", hp.getId(), p.getId()));

        // When
        List<Prescription> prescriptions = hp.getPrescriptions(prescriptionDAO, p2.getId());
        List<Prescription> prescriptions2 = hp.getPrescriptions(prescriptionDAO, p.getId());
        // Then
        assertThat(prescriptions, not(
                hasItem(
                        hasProperty("content", equalTo("Do some sport")))));
        assertThat(prescriptions2, hasItem(hasProperty("content", equalTo("Do some sport"))));
    }

    // TODO : Il y a probabelement un problèeme au niveau de la logique actuel, la
    // question serait
    // Si un professionnel de santé veut ajouter une nouvelle prescription et un
    // patient la récup est-ce que ces
    // Actions doivent nécessairement être dans le model ou elles peuvent être gérer
    // au niveau de leur controleur
    // respectif ?

    @Test
    @DisplayName("Tests if a patient can be retrieved")
    public void checkThatAnHPCanRetrieveAPatient()
            throws NameNotFoundException, NameAlreadyBoundException {
        // Given
        Patient originalPatient = new Patient("Alice", "WONDERLAND", "2489237484");
        patientDAO.add(originalPatient);
        // When
        Patient referenceToTheOriginalPatient = patientDAO.findOne(originalPatient.getId());
        // Then
        assertThat(referenceToTheOriginalPatient, is(originalPatient));
    }
}
