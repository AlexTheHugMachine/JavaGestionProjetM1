package fr.univ_lyon1.info.m1.mes.daos;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HPSpeciality;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HealthProfessional;

public class HealthProfessionalDAOTest {
    private HealthProfessionalDAO hpDAO;

    private HealthProfessional john;
    private HealthProfessional doe;
    private HealthProfessional jack;
    private HealthProfessional james;
    private HealthProfessional eric;

    @BeforeEach
    public void setUp() {
        hpDAO = new HealthProfessionalDAO();

        john = new HealthProfessional("John", "Wick", "69686867875", HPSpeciality.GENERALISTE);
        doe = new HealthProfessional("John", "Doe", "79123270856", HPSpeciality.CHIRURGIEN);
        jack = new HealthProfessional("Jack", "Sparrow", "09753109542", HPSpeciality.DENTISTE);
        james = new HealthProfessional("James", "Lebron", "16789669799", HPSpeciality.GENERALISTE);
        eric = new HealthProfessional("Eric", "Zemmour", "08621837923", HPSpeciality.CHIRURGIEN);

        try {
            hpDAO.add(john);
            hpDAO.add(doe);
            hpDAO.add(jack);
            hpDAO.add(james);
            hpDAO.add(eric);
        } catch (NameAlreadyBoundException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Assert that DAOs properly add HP and handle if it already exists")
    public void add() {
        try {
            assertNotNull(hpDAO.add(new HealthProfessional("foo", "bar",
                    "86862638131", HPSpeciality.GENERALISTE)));
        } catch (NameAlreadyBoundException e) {
            fail("Erreur dans l'ajout du professionel de santé " + e.getMessage());
        }
        try {
            hpDAO.add(new HealthProfessional("foo", "bar",
                    "86862638131", HPSpeciality.GENERALISTE));
            fail("Should throw NameAlreadyBoundException");
        } catch (NameAlreadyBoundException e) {
            assert (true);
        }
    }

    @Test
    @DisplayName("Assert that DAOs properly delete a HP and handle if it does not persist")
    public void delete() {
        try {
            hpDAO.delete(eric);
        } catch (NameNotFoundException e) {
            fail("HP not existing " + e.getMessage());
        }
        try {
            hpDAO.findOne("08621837923");
        } catch (NameNotFoundException e) {
            assert (true);
        }
    }

    @Test
    @DisplayName("Check that we throw a NameNotFoundException when deleting a non existing hp")
    void throwNameNotFoundWhendeletingNonExistingHPWithObject() {
        HealthProfessional nonExistingHP = new HealthProfessional(
                "Enzo",
                "CECILLON",
                "12345678909",
                HPSpeciality.GENERALISTE);
        assertThrows(NameNotFoundException.class, () -> {
            hpDAO.delete(nonExistingHP);
        });
    }

    @Test
    @DisplayName("Assert that HPDAOs properly delete an HP with his ID")
    public void deleteById() {
        try {
            hpDAO.deleteById(eric.getRPPS());
        } catch (NameNotFoundException e) {
            fail("HP not existing " + e.getMessage());
        }
        try {
            hpDAO.findOne("08621837923");
        } catch (NameNotFoundException e) {
            assert (true);
        }
    }

    @Test
    @DisplayName("Throw NameNotFoundException when deleting with non existing id")
    void throwNameNotFoundWhendeletingNonExistingHPBySpecifyingId() {
        assertThrows(NameNotFoundException.class, () -> {
            hpDAO.deleteById("32134789812");
        });
    }

    @Test
    @DisplayName("Throw InvalidNameException when we want to delete with a non String id")
    void throwInvalidNameExceptionWhenPassingAnIntToDeleteByIdDAO() {
        Integer castErrorInteger = 696868678;
        assertThrows(NameNotFoundException.class, () -> {
            hpDAO.deleteById(castErrorInteger);
        });
    }

    @Test
    @DisplayName("Assert that DAOs properly update the HP informations")
    public void update() {
        HealthProfessional toto = new HealthProfessional("toto", "titi",
                eric.getRPPS(), HPSpeciality.GENERALISTE);
        hpDAO.update(eric.getRPPS(), toto);
        HealthProfessional newEric;
        try {
            newEric = hpDAO.findOne(toto.getRPPS());
            assertAll(
                    () -> assertEquals(newEric.getName(), "toto"),
                    () -> assertEquals(newEric.getSurname(), "titi"),
                    () -> assertEquals(newEric.getRPPS(), "08621837923"),
                    () -> assertEquals(newEric.getSpeciality(), HPSpeciality.GENERALISTE));
        } catch (NameNotFoundException e) {
            fail("Fail to retrieve the updated patient");
        }
    }

    @Test
    void searchForHPAndReturnItsIdStoredInDAO() {
        assertEquals(john.getRPPS(), hpDAO.getId(john));
    }

    @Test
    void retrieveAllHPIdStoredInDAO() {
        // Given
        Set<Serializable> everyRPPSStoredInDAO = hpDAO.getAllIds();
        Set<Serializable> expectedListOfIds = new HashSet<>();
        expectedListOfIds.add(john.getRPPS());
        expectedListOfIds.add(doe.getRPPS());
        expectedListOfIds.add(jack.getRPPS());
        expectedListOfIds.add(james.getRPPS());
        expectedListOfIds.add(eric.getRPPS());
        assertEquals(expectedListOfIds, everyRPPSStoredInDAO);
    }

    @Test
    @DisplayName("Assert that DAOs don't update the HP informations if RPPS aren't the same.")
    public void checkThatWeDontUpdateWhenRPPSArentTheSame() {
        HealthProfessional titi = new HealthProfessional(
                "titi", "titi", "09753109542", HPSpeciality.GENERALISTE);
        Exception message = assertThrows(IllegalArgumentException.class, () -> {
            hpDAO.update(eric.getRPPS(), titi);
        });
        String expectedMessage = "RPPS are not the same.";
        String actualMessage = message.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Assert that DAOs properly retrieve the right HP with his id and handle errors")
    public void findOne() {
        try {
            assertEquals(hpDAO.findOne(eric.getRPPS()), eric);
            assertNotEquals(hpDAO.findOne(eric.getRPPS()), john);
            assertNotEquals(hpDAO.findOne(john.getRPPS()), eric);

        } catch (NameNotFoundException e) {
            fail("Can't access or retrieve HP " + e.getMessage());
        }
        try {
            hpDAO.findOne("133274329");
            fail("Should throw NameNotFoundException because id does not exist");
        } catch (NameNotFoundException e) {
            assert (true);
        }
    }

    @Test
    @DisplayName("Check that we retrieve every HP stored in the DAO")
    void finaAll() {
        Collection<HealthProfessional> expectedCollectionOfHP = new ArrayList<HealthProfessional>();
        expectedCollectionOfHP.add(john);
        expectedCollectionOfHP.add(doe);
        expectedCollectionOfHP.add(jack);
        expectedCollectionOfHP.add(james);
        expectedCollectionOfHP.add(eric);
        assertThat(hpDAO.findAll(),
                Matchers.containsInAnyOrder(expectedCollectionOfHP.toArray()));
    }

    @Test
    @DisplayName("Assert that DAOs return a list of HP given a Name")
    void findByName() {
        // Given
        List<HealthProfessional> expectedList = new ArrayList<HealthProfessional>();
        expectedList.add(doe);
        expectedList.add(john);

        try {
            List<HealthProfessional> actualList = hpDAO.findByName("John");
            assertEquals(actualList.size(), expectedList.size());
            assertIterableEquals(actualList, expectedList);
            assertIterableEquals(hpDAO.findByName("JohN"), expectedList);
            assertThat(actualList, not(hasItem(eric)));
        } catch (NameNotFoundException e) {
            fail("We didn't find the expectedList given a specific name");
        }
        try {
            hpDAO.findByName("Hello");
            fail("Should throw NameNotFoundException");
        } catch (NameNotFoundException ignored) {
            assert (true);
        }
    }

    @Test
    @DisplayName("Assert that given a List of HPs RPPS the DAO return the right HPs")
    void findManyHealthProfessionalByThereRPPS() {
        // Given
        Collection<Serializable> listOfRpps = new ArrayList<Serializable>();
        listOfRpps.add("79123270856");
        listOfRpps.add("08621837923");
        listOfRpps.add("09753109542");

        List<HealthProfessional> expectedList = new ArrayList<HealthProfessional>();
        expectedList.add(doe);
        expectedList.add(eric);
        expectedList.add(jack);
        // When
        try {
            Collection<HealthProfessional> actualList = hpDAO.findByIds(listOfRpps);
            // Then
            assertEquals(actualList.size(), expectedList.size());
            assertIterableEquals(expectedList, actualList);
        } catch (NameNotFoundException | IllegalArgumentException e) {
            fail("We didn't find all the HP stored inside the database.");
        }
    }

    @Test
    @DisplayName("Throw an IllegalArgumentException when calling findByIds with null list")
    void throwIllegalArgumentExceptionWhenPassingANullOrEmptyList() {
        // Given a null list
        List<Serializable> nullList = null;
        List<Serializable> emptyList = new ArrayList<Serializable>();
        assertThrows(IllegalArgumentException.class, () -> hpDAO.findByIds(nullList));
        assertThrows(IllegalArgumentException.class, () -> hpDAO.findByIds(emptyList));
    }

    @Test
    void checkThatWeThrowCorrectExceptionWhenWeDontFindAnyHP() {
        Collection<Serializable> incorrectList = new ArrayList<Serializable>();
        incorrectList.add("7U2815788878");
        incorrectList.add("787");

        Exception message = assertThrows(NameNotFoundException.class,
                () -> hpDAO.findByIds(incorrectList));

        String expectedMessage = "One or multiples items aren't present.";
        String actualMessage = message.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void checkThatWeThrowAnIllegalArgumentExceptionWhenPassingNullList() {
        Collection<Serializable> incorrectList = new ArrayList<Serializable>();
        Exception message = assertThrows(IllegalArgumentException.class,
                () -> hpDAO.findByIds(incorrectList));

        String expectedMessage = "List of ids is empty.";
        String actualMessage = message.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }
}
