package fr.univ_lyon1.info.m1.mes.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.univ_lyon1.info.m1.mes.builders.HealthProfessionnal.HealthProfessionalBuilder;
import fr.univ_lyon1.info.m1.mes.controller.HealthProfessionalRessourceController;
import fr.univ_lyon1.info.m1.mes.daos.HealthProfessionalDAO;
import fr.univ_lyon1.info.m1.mes.dto.healthprofessional.HealthProfessionalRequestDto;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HPSpeciality;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HealthProfessional;

public class HealthProfessionalRessourceControllerTest {

    private HealthProfessionalRessourceController hpRessourceController;
    private HealthProfessional eric;
    private HealthProfessional michel;

    @BeforeEach
    void setUp() {
      HealthProfessionalDAO hpDAO = new HealthProfessionalDAO();
      HealthProfessionalBuilder builder = new HealthProfessionalBuilder();

      eric = new HealthProfessional(
        "eric", "zemmour", "55334108515", HPSpeciality.CHIRURGIEN);
      michel = new HealthProfessional(
        "michel", "jordunk", "82837180785", HPSpeciality.GENERALISTE);

      try {
        hpDAO.add(eric);
        hpDAO.add(michel);
      } catch (NameAlreadyBoundException e) {
        e.printStackTrace();
      } catch (IllegalArgumentException e) {
        e.printStackTrace();
      }
      hpRessourceController = new HealthProfessionalRessourceController(hpDAO, builder);
    }

    @Test
    void createHealthProfessionalProperly() {
      HealthProfessionalRequestDto hpRequest = new HealthProfessionalRequestDto(
            "john", "doe", "12345678919", HPSpeciality.GENERALISTE.toString());

        String rppsOfJohn;
        try {
          rppsOfJohn = (String) hpRessourceController.createHealthProfessional(hpRequest);
          try {
            HealthProfessional hp = hpRessourceController.getHealthProfessional(rppsOfJohn);
            assertEquals("12345678919", rppsOfJohn);
            assertEquals(hpRequest.getName(), hp.getName());
            assertEquals(hpRequest.getSurname(), hp.getSurname());
            assertEquals(hpRequest.getRPPS(), hp.getRPPS());
            assertEquals(hpRequest.getSpeciality(), hp.getSpeciality().toString());
          } catch (NameNotFoundException e) {
            fail("The health professional was not created properly");
          }
        } catch (NameAlreadyBoundException e1) {
          fail("The health professional was not created properly");
        }
    }

    @Test
    void createHealthProfessionalWithExistingRPPS() {
      HealthProfessionalRequestDto hpRequest = new HealthProfessionalRequestDto(
            "john", "doe", "55334108515", HPSpeciality.GENERALISTE.toString());

        try {
          hpRessourceController.createHealthProfessional(hpRequest);
          fail("The health professional was created with an existing RPPS");
        } catch (NameAlreadyBoundException e) {
          // The health professional was not created
        }
    }

    @Test
    void createHealthProfessionalWithInvalidRPPS() {
      HealthProfessionalRequestDto hpRequest = new HealthProfessionalRequestDto(
            "john", "doe", "1234567891", HPSpeciality.GENERALISTE.toString());

        try {
          hpRessourceController.createHealthProfessional(hpRequest);
          fail("The health professional was created with an invalid RPPS");
        } catch (IllegalArgumentException e) {
          // The health professional was not created
        } catch (NameAlreadyBoundException e) {
          fail("The health professional was created with an invalid RPPS");
        }
    }

    @Test
    void removeHealthProfessionalByIdProperly() {
      String rppsOfEric = eric.getRPPS();
      try {
        hpRessourceController.removeHealthProfessionalById(rppsOfEric);
      } catch (NameNotFoundException e1) {
        fail("The health professional was not removed properly");
      }
      try {
        hpRessourceController.getHealthProfessional(rppsOfEric);
        fail("The health professional was not removed properly");
      } catch (NameNotFoundException e) {
        // The health professional was removed
      }
    }

    @Test
    void removeHealthProfessionalByIdWithInvalidRPPS() {
      try {
        hpRessourceController.removeHealthProfessionalById("1234567891");
        fail("The health professional was removed with an invalid RPPS");
      } catch (NameNotFoundException e) {
        // The health professional was not removed
      }
    }

    @Test
    void removeHealthProfessionalProperly() {
      HealthProfessionalRequestDto hpRequest = new HealthProfessionalRequestDto(
        "eric", "zemmour", "55334108515", HPSpeciality.CHIRURGIEN.toString());
      try {
        hpRessourceController.removeHealthProfessional(hpRequest);
      } catch (NameNotFoundException e1) {
        fail("The health professional was not removed properly");
      }
      try {
        hpRessourceController.getHealthProfessional(hpRequest.getRPPS());
        fail("The health professional was not removed properly");
      } catch (NameNotFoundException e) {
        // The health professional was removed
      }
    }

    @Test
    void removeHealthProfessionalWithInvalidRPPS() {
      HealthProfessionalRequestDto hpRequest = new HealthProfessionalRequestDto(
        "eric", "zemmour", "1234567891", HPSpeciality.CHIRURGIEN.toString());
      try {
        hpRessourceController.removeHealthProfessional(hpRequest);
        fail("The health professional was removed with an invalid RPPS");
      } catch (NameNotFoundException e) {
        // The health professional was not removed
      }
    }

    @Test
    void updateHealthProfessionalProperly() {
      String rppsOfEric = eric.getRPPS();
      HealthProfessionalRequestDto hpRequest = new HealthProfessionalRequestDto(
            "john", "doe", "55334108515", HPSpeciality.GENERALISTE.toString());
      hpRessourceController.updateHealthProfessional(hpRequest);
      HealthProfessional hp;
      try {
        hp = hpRessourceController.getHealthProfessional(rppsOfEric);
        assertEquals(hpRequest.getName(), hp.getName());
        assertEquals(hpRequest.getSurname(), hp.getSurname());
        assertEquals(hpRequest.getRPPS(), hp.getRPPS());
        assertEquals(hpRequest.getSpeciality(), hp.getSpeciality().toString());
      } catch (NameNotFoundException e) {
        fail("The health professional was not updated properly");
      }
    }

    @Test
    void getHealthProfessionalByIdProperly() {
      String rppsOfEric = eric.getRPPS();
      HealthProfessional hp;
      try {
        hp = hpRessourceController.getHealthProfessional(rppsOfEric);
        assertEquals(eric, hp);
      } catch (NameNotFoundException e) {
        fail("The health professional was not retrieved properly");
      }
    }

    @Test
    void getHealthProfessionalByIdWithInvalidRPPS() {
      try {
        hpRessourceController.getHealthProfessional("1234567891");
        fail("The health professional was retrieved with an invalid RPPS");
      } catch (NameNotFoundException e) {
        // The health professional was not retrieved
      }
    }

    @Test
    void getHealthProfessionalsProperly() {
      assertEquals(2, hpRessourceController.getHealthProfessionals().size());
    }
}
