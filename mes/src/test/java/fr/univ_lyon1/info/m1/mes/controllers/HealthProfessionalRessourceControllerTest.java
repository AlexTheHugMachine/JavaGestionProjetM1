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
    void createHealthProfessionalProperly() throws NameAlreadyBoundException {
      HealthProfessionalRequestDto hpRequest = new HealthProfessionalRequestDto(
            "john", "doe", "12345678919", HPSpeciality.GENERALISTE.toString());

        String rppsOfJohn = (String) hpRessourceController.createHealthProfessional(hpRequest);
    
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
    void createHealthProfessionalWithInvalidRPPS() throws NameAlreadyBoundException {
      HealthProfessionalRequestDto hpRequest = new HealthProfessionalRequestDto(
            "john", "doe", "1234567891", HPSpeciality.GENERALISTE.toString());

        try {
          hpRessourceController.createHealthProfessional(hpRequest);
          fail("The health professional was created with an invalid RPPS");
        } catch (IllegalArgumentException e) {
          // The health professional was not created
        }
    }

    @Test
    void removeHealthProfessionalByIdProperly() throws NameNotFoundException {
      String rppsOfEric = eric.getRPPS();
      hpRessourceController.removeHealthProfessionalById(rppsOfEric);
      try {
        hpRessourceController.getHealthProfessional(rppsOfEric);
        fail("The health professional was not removed properly");
      } catch (NameNotFoundException e) {
        // The health professional was removed
      }
    }

    @Test
    void removeHealthProfessionalByIdWithInvalidRPPS() throws NameNotFoundException {
      try {
        hpRessourceController.removeHealthProfessionalById("1234567891");
        fail("The health professional was removed with an invalid RPPS");
      } catch (NameNotFoundException e) {
        // The health professional was not removed
      }
    }

    @Test
    void removeHealthProfessionalProperly() throws NameNotFoundException {
      HealthProfessionalRequestDto hpRequest = new HealthProfessionalRequestDto(
        "eric", "zemmour", "55334108515", HPSpeciality.CHIRURGIEN.toString());
      hpRessourceController.removeHealthProfessional(hpRequest);
      try {
        hpRessourceController.getHealthProfessional(hpRequest.getRPPS());
        fail("The health professional was not removed properly");
      } catch (NameNotFoundException e) {
        // The health professional was removed
      }
    }

    @Test
    void removeHealthProfessionalWithInvalidRPPS() throws NameNotFoundException {
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
    void updateHealthProfessionalProperly() throws NameNotFoundException {
      String rppsOfEric = eric.getRPPS();
      HealthProfessionalRequestDto hpRequest = new HealthProfessionalRequestDto(
            "john", "doe", "55334108515", HPSpeciality.GENERALISTE.toString());
      hpRessourceController.updateHealthProfessional(hpRequest);
      HealthProfessional hp = hpRessourceController.getHealthProfessional(rppsOfEric);
      assertEquals(hpRequest.getName(), hp.getName());
      assertEquals(hpRequest.getSurname(), hp.getSurname());
      assertEquals(hpRequest.getRPPS(), hp.getRPPS());
      assertEquals(hpRequest.getSpeciality(), hp.getSpeciality().toString());
    }

    @Test
    void getHealthProfessionalByIdProperly() throws NameNotFoundException {
      String rppsOfEric = eric.getRPPS();
      HealthProfessional hp = hpRessourceController.getHealthProfessional(rppsOfEric);
      assertEquals(eric, hp);
    }

    @Test
    void getHealthProfessionalByIdWithInvalidRPPS() throws NameNotFoundException {
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
