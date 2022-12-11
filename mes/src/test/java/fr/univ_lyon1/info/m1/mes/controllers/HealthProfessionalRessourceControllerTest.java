package fr.univ_lyon1.info.m1.mes.controllers;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.Collection;

import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;

import org.hamcrest.Matchers;
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
  void createHealthProfessionalProperlyReturnTheRPPSOfTheNewHP() {
    HealthProfessionalRequestDto hpRequest = new HealthProfessionalRequestDto(
        "john",
        "doe",
        "12345678919",
        HPSpeciality.GENERALISTE.toString());

    try {
      String rppsOfJohn = (String) hpRessourceController.createHealthProfessional(hpRequest);
      assertEquals("12345678919", rppsOfJohn);
      try { // Integration.
        HealthProfessional hp = hpRessourceController.getHealthProfessionalById(rppsOfJohn);
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
        "john",
        "doe",
        "55334108515",
        HPSpeciality.GENERALISTE.toString());

    assertThrows(NameAlreadyBoundException.class,
        () -> hpRessourceController.createHealthProfessional(hpRequest));

  }

  @Test
  void createHealthProfessionalWithInvalidRPPS() {
    HealthProfessionalRequestDto hpRequest = new HealthProfessionalRequestDto(
        "john",
        "doe",
        "1234567891",
        HPSpeciality.GENERALISTE.toString());

    try {
      hpRessourceController.createHealthProfessional(hpRequest);
      fail("The health professional was created with an invalid RPPS");
    } catch (IllegalArgumentException e) {
      assertTrue(true, "Correctly identified that rpps is not of length 11.");
    } catch (NameAlreadyBoundException e) {
      fail("The health professional was created with an invalid RPPS");
    }
  }

  @Test
  void removeHealthProfessionalByIdReturnTrueOnSuccess() {
    String rppsOfEric = eric.getRPPS();
    try {
      assertTrue(hpRessourceController.removeHealthProfessionalById(rppsOfEric));
    } catch (NameNotFoundException e1) {
      fail("The health professional was not removed properly");
    }
  }

  @Test
  void removeHealthProfessionalByIdWithInvalidRPPS() {
    assertThrows(NameNotFoundException.class,
        () -> hpRessourceController.removeHealthProfessionalById("1230137194194"));
  }

  @Test
  void removeHealthProfessionalGivenDto() {
    HealthProfessionalRequestDto hpRequest = new HealthProfessionalRequestDto(
        "eric",
        "zemmour",
        "55334108515",
        HPSpeciality.CHIRURGIEN.toString());
    try {
      hpRessourceController.removeHealthProfessional(hpRequest);
    } catch (NameNotFoundException e1) {
      fail("The health professional was not removed properly");
    }
    try {
      hpRessourceController.getHealthProfessionalById(hpRequest.getRPPS());
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
  void updateHealthProfessionalWithDto() {
    String rppsOfEric = eric.getRPPS();
    HealthProfessionalRequestDto hpRequest = new HealthProfessionalRequestDto(
        "john", "doe", "55334108515", HPSpeciality.GENERALISTE.toString());
    hpRessourceController.updateHealthProfessional(hpRequest);
    HealthProfessional hp;
    try {
      hp = hpRessourceController.getHealthProfessionalById(rppsOfEric);
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
      hp = hpRessourceController.getHealthProfessionalById(rppsOfEric);
      assertEquals(eric, hp);
    } catch (NameNotFoundException e) {
      fail("The health professional was not retrieved properly");
    }
  }

  @Test
  void getHealthProfessionalByIdWithInvalidRPPS() {
    try {
      hpRessourceController.getHealthProfessionalById("1234567891");
      fail("The health professional was retrieved with an invalid RPPS");
    } catch (NameNotFoundException e) {
      // The health professional was not retrieved
    }
  }

  @Test
  void getHealthProfessionalsReturnCollectionOfAllHPStoredInDAO() {
    Collection<HealthProfessional> hps = hpRessourceController.getHealthProfessionals();
    Collection<HealthProfessional> expectedColl = new ArrayList<HealthProfessional>();
    expectedColl.add(eric);
    expectedColl.add(michel);

    assertAll(
        () -> assertEquals(2, hps.size()),
        () -> assertThat(hps, Matchers.containsInAnyOrder(expectedColl.toArray())));
  }
}
