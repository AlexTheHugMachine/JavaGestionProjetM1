package fr.univ_lyon1.info.m1.mes.model.HealthProfessional;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;



import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HPSpeciality;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HealthProfessional;

public class HealthProfessionalTest {
    private HealthProfessional hp;

    @BeforeEach
    public void setUp() {
        hp = new HealthProfessional(
                "Smith", "Richard", "12334108513", HPSpeciality.GENERALISTE);
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

        assertThat(name, is("Smith"));
        assertThat(surname, is("Richard"));
    }

    @Test
    @DisplayName("Tests if the professionnal's RPPS is not null or empty and match expected value")
    public void healthProfessionnalRPPSsNotNullAndEmpty() {
        // Given HP
        // When
        String hpRPPS = hp.getRPPS();
        // Then
        assertNotEquals(hpRPPS, null);
        assertNotEquals(hpRPPS, "");
        assertEquals("12334108513", hpRPPS);
    }

    @Test
    @DisplayName("Tests if the professionnal's name is valid")
    public void checkThatHandleIncorrectHealthProfessionnalName() {
        // Given
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new HealthProfessional(
                    "876567827adsqsd", "George", "92397541032", HPSpeciality.GENERALISTE);
        });
        // When
        String expectedMessage = "Les informations du professionnel de santé sont invalides.";
        String actualMessage = exception.getMessage();
        // Then
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Tests if the professionnal's name is valid")
    public void checkThatHandleIncorrectHealthProfessionnaSurname() {
        // Given
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new HealthProfessional(
                    "Enzo", "7623183dsqdqs%", "92397541032", HPSpeciality.GENERALISTE);
        });
        // When
        String expectedMessage = "Les informations du professionnel de santé sont invalides.";
        String actualMessage = exception.getMessage();
        // Then
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void constructHPThrowExceptionWhenNameEmptyOrNull() {
        // Given
        Exception exceptionEmpty = assertThrows(IllegalArgumentException.class, () -> {
            new HealthProfessional(
                    "", "CECILLON", "92397541032", HPSpeciality.GENERALISTE);
        });
        Exception exceptionNull = assertThrows(IllegalArgumentException.class, () -> {
            new HealthProfessional(
                    null, "CECILLON", "92397541032", HPSpeciality.GENERALISTE);
        });

        String argumentCheckerMessageNull = "ArgumentChecker Failed Null element. ";
        String argumentCheckerMessageEmpty = "ArgumentChecker Failed Empty element. ";
        // When
        String expectedMessageEmpty =
        argumentCheckerMessageEmpty + "Les informations du professionnel de santé sont invalides.";
        String expectedMessageNull = argumentCheckerMessageNull
                + "Les informations du professionnel de santé sont invalides.";
        String actualMessageEmpty = exceptionEmpty.getMessage();
        String actualMessageNull = exceptionNull.getMessage();
        // Then
        assertAll(
                () -> assertEquals(expectedMessageEmpty, actualMessageEmpty),
                () -> assertEquals(expectedMessageNull, actualMessageNull));
    }

    @Test
    void constructHPThrowExceptionWhenSurnameEmptyOrNull() {
        // Given
        Exception exceptionEmpty = assertThrows(IllegalArgumentException.class, () -> {
            new HealthProfessional(
                    "Enzo", "", "92397541032", HPSpeciality.GENERALISTE);
        });
        Exception exceptionNull = assertThrows(IllegalArgumentException.class, () -> {
            new HealthProfessional(
                    "Enzo", null, "92397541032", HPSpeciality.GENERALISTE);
        });

        String argumentCheckerMessageNull = "ArgumentChecker Failed Null element. ";
        String argumentCheckerMessageEmpty = "ArgumentChecker Failed Empty element. ";
        // When
        String expectedMessageEmpty = argumentCheckerMessageEmpty
                + "Les informations du professionnel de santé sont invalides.";
        String expectedMessageNull = argumentCheckerMessageNull
                + "Les informations du professionnel de santé sont invalides.";
        String actualMessageEmpty = exceptionEmpty.getMessage();
        String actualMessageNull = exceptionNull.getMessage();
        // Then
        assertAll(
                () -> assertEquals(expectedMessageEmpty, actualMessageEmpty),
                () -> assertEquals(expectedMessageNull, actualMessageNull));
    }

    @Test
    void constructHPThrowExceptionWhenRPPSNullOrEmpty() {
        // Given
        Exception exceptionEmpty = assertThrows(IllegalArgumentException.class, () -> {
            new HealthProfessional(
                    "Enzo", "CECILLON", "", HPSpeciality.GENERALISTE);
        });
        Exception exceptionNull = assertThrows(IllegalArgumentException.class, () -> {
            new HealthProfessional(
                    "Enzo", "CECILLON", null, HPSpeciality.GENERALISTE);
        });

        String argumentCheckerMessageNull = "ArgumentChecker Failed Null element. ";
        String argumentCheckerMessageEmpty = "ArgumentChecker Failed Empty element. ";
        // When
        String expectedMessageEmpty = argumentCheckerMessageEmpty
                + "Les informations du professionnel de santé sont invalides.";
        String expectedMessageNull = argumentCheckerMessageNull
                + "Les informations du professionnel de santé sont invalides.";
        String actualMessageEmpty = exceptionEmpty.getMessage();
        String actualMessageNull = exceptionNull.getMessage();
        // Then
        assertAll(
                () -> assertEquals(expectedMessageEmpty, actualMessageEmpty),
                () -> assertEquals(expectedMessageNull, actualMessageNull));
    }

    @Test
    @DisplayName("Test that we correctly handle an incorrect definition of the RPPS.")
    public void checkThatWeHandleIncorrectRPPSDefinition() {
        // Given 13 length number
        Exception lengthException = assertThrows(IllegalArgumentException.class, () -> {
            new HealthProfessional("Enzo", "CECILLON", "0000000000000", HPSpeciality.HOMEOPATHE);
        });
        // 11 length number but with letters
        Exception letterException = assertThrows(IllegalArgumentException.class, () -> {
            new HealthProfessional("John", "Doe", "99Hdq751a71", HPSpeciality.HOMEOPATHE);
        });
        // When
        String expectedMessage = "Les informations du professionnel de santé sont invalides.";
        String actualMessageForLengthException = lengthException.getMessage();
        String actualMessagForLetterException = letterException.getMessage();

        assertEquals(expectedMessage, actualMessagForLetterException);
        assertEquals(expectedMessage, actualMessageForLengthException);
    }

}
