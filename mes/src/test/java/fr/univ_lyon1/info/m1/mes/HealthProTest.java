package fr.univ_lyon1.info.m1.mes;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

import fr.univ_lyon1.info.m1.mes.model.HealthProfessional;
import fr.univ_lyon1.info.m1.mes.model.MES;
import fr.univ_lyon1.info.m1.mes.model.Patient;
import fr.univ_lyon1.info.m1.mes.model.Prescription;

public class HealthProTest {
    MES model = new MES();
    HealthProfessional hp = new HealthProfessional("Dr. Smith", model);

    @Test
    /**
     * Test that the name given to an HealthProfessionnal during the creation of the
     * object is correct.
     */
    public void HealthProfessionalName() {
        // When
        String name = hp.getName();

        // Then
        assertThat(name, is("Dr. Smith"));
    }

    public void checkThatHandleIncorrectHealthProfessionnalName() {
        // Given
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            HealthProfessional hp2 = new HealthProfessional("876567827adsqsd", model);
        });
        // When
        String expectedMessage = "Le nom du professionnel de santé n'est pas valide.";
        String actualMessage = exception.getMessage();
        // Then
        assertTrue(actualMessage.contains(expectedMessage));
    }

   @Test
    /**
     * Test addPrescription, and demonstrate advanced Hamcrest assertions.
     */
    public void GetPrescriptionTest() {
        // Given
        Patient p = model.createPatient("Alice", "20123456789012");
        p.addPrescription(hp, "Do some sport");

        // When
        List<Prescription> prescriptions = hp.getPrescriptions("20123456789012");

        // Then
        assertThat(prescriptions, hasItem(
                hasProperty("content", equalTo("Do some sport"))));
    }

    @Test
    /**
     * Not-so-relevant test, mostly another example of advanced assertion. More
     * relevant things to test: play with several Patients, check that a
     * prescription made for one patient doesn't apply to the other, etc.
     */
    public void GetNotPrescriptionTest() {
        // Given
        Patient p = model.createPatient("Alice", "20123456789012");
        p.addPrescription(hp, "Eat fruits");

        // When
        List<Prescription> prescriptions = hp.getPrescriptions("20123456789012");

        // Then
        assertThat(prescriptions, not(
                hasItem(
                        hasProperty("content", equalTo("Do some sport")))));
    }

    @Test
    /**
     * Checks that a prescription made for one patient doesn't apply to another
     * patient
     */
    public void GetPrescriptionFromSinglePatientTest() {
        // Given
        Patient patientWithPrescription = model.createPatient("Alice", "20123456789012");
        model.createPatient("John", "102020212345678");
        patientWithPrescription.addPrescription(hp, "Eat fruits");

        // When
        List<Prescription> prescriptions = hp.getPrescriptions("102020212345678");

        // Then
        assertThat(prescriptions, not(
                hasItem(
                        hasProperty("content", equalTo("Eat fruits")))));
    }

    @Test
    /**
     * Test that an healthProfessional can retrieve a patient
     */
    public void GetPatientTest() {
        // Given
        Patient p = model.createPatient("Alice", "20123456789012");
        // When
        Patient p2 = hp.getPatient("20123456789012");
        // Then
        assertThat(p2, is(p));
    }
}
