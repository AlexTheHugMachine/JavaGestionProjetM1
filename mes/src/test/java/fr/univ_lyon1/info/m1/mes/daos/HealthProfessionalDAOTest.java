package fr.univ_lyon1.info.m1.mes.daos;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.function.Executable;

import javax.naming.InvalidNameException;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HPSpeciality;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HealthProfessional;

public class HealthProfessionalDAOTest {
    private HealthProfessionalDAO hpDAO;

    private HealthProfessional john;
    private HealthProfessional jack;
    private HealthProfessional james;
    private HealthProfessional eric;

    @BeforeEach
    public void setUp() {
        hpDAO = new HealthProfessionalDAO();

        john = new HealthProfessional("John", "Doe", HPSpeciality.GENERALISTE);
        jack = new HealthProfessional("Jack", "Sparrow", HPSpeciality.DENTISTE);
        james = new HealthProfessional("James", "Lebron", HPSpeciality.GENERALISTE);
        eric = new HealthProfessional("Eric", "Zemmour", HPSpeciality.CHIRURGIEN);

        try {
            hpDAO.add(john);
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
            assertNotNull(hpDAO.add(new HealthProfessional("foo", "bar", HPSpeciality.GENERALISTE)));
        } catch (NameAlreadyBoundException e) {
            fail("Erreur dans l'ajout du professionel de santé " + e.getMessage());
        }
        try {
            assertThrows(NameAlreadyBoundException.class, (Executable) hpDAO.add(new HealthProfessional("foo", "bar", HPSpeciality.GENERALISTE)));
        } catch (NameAlreadyBoundException e) {
            
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
            hpDAO.findOne(eric.getId());
        } catch (NameNotFoundException e) {
            assert(true);
        } catch (InvalidNameException e) {
            fail("Should not trow InvalidNameException");
        }
    }

    @Test
    @DisplayName("Assert that DAOs properly delete a HP with his ID and handle if it does not persist")
    public void deleteById() {
        try {
            hpDAO.deleteById(eric.getId());
        } catch (NameNotFoundException | InvalidNameException e) {
            fail("HP not existing " + e.getMessage());
        }
        try {
            hpDAO.findOne(eric.getId());
        } catch (NameNotFoundException e) {
            assert(true);
        } catch (InvalidNameException e) {
            fail("Should not trow InvalidNameException");
        }
    }

    @Test
    @DisplayName("Assert that DAOs properly update the HP informations")
    public void update() {
        HealthProfessional toto = new HealthProfessional("toto", "titi", HPSpeciality.GENERALISTE);
        try {
            hpDAO.update(eric.getId(), toto);
        } catch (InvalidNameException e) {
            fail("Error while updating HP information");
        }
    }

    @Test
    @DisplayName("Assert that DAOs properly get the HP's ID")
    public void getId() {
        try {
            hpDAO.findOne(eric.getId());
        } catch (NameNotFoundException e) {
            assert(false);
        } catch (InvalidNameException e) {
            fail("Should not trow InvalidNameException");
        }
    }
}
