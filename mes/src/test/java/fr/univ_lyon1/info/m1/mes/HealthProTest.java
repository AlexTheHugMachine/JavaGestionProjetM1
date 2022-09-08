package fr.univ_lyon1.info.m1.mes;

import static org.hamcrest.Matchers.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

import fr.univ_lyon1.info.m1.mes.model.HealthProfessional;
import fr.univ_lyon1.info.m1.mes.model.MES;
import fr.univ_lyon1.info.m1.mes.model.Patient;
import fr.univ_lyon1.info.m1.mes.model.Prescription;

public class HealthProTest {
    MES model = new MES();

    @Test
    /**
     * A simple test, purposely broken so that students can see what happens for
     * test failures.
     */
    public void HealthProfessionalName() {
        // Given
        HealthProfessional hp = new HealthProfessional("Dr. Smith", model);

        // When
        String name = hp.getName();

        // Then
        assertThat(name, is("Dr. Smith"));
    }

    @Test
    /**
     * Test addPrescription, and demonstrate advanced Hamcrest assertions.
     */
    public void GetPrescriptionTest() {
        // Given
        HealthProfessional hp = new HealthProfessional("Dr. Smith", model);
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
        HealthProfessional hp = new HealthProfessional("Dr. Smith", model);
        Patient p = model.createPatient("Alice", "20123456789012");
        p.addPrescription(hp, "Eat fruits");

        // When
        List<Prescription> prescriptions = hp.getPrescriptions("20123456789012");

        // Then
        assertThat(prescriptions, not(
            hasItem(
                hasProperty("content", equalTo("Do some sport")))));
    }

}
