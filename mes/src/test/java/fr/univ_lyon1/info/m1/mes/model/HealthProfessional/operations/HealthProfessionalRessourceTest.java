package fr.univ_lyon1.info.m1.mes.model.HealthProfessional.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.univ_lyon1.info.m1.mes.builders.HealthProfessionnal.HealthProfessionalBuilder;
import fr.univ_lyon1.info.m1.mes.daos.HealthProfessionalDAO;
import fr.univ_lyon1.info.m1.mes.dto.healthprofessional.HealthProfessionalRequestDto;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HPSpeciality;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HealthProfessional;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.operations.HealthProfessionalRessource;

public class HealthProfessionalRessourceTest {

    private HealthProfessionalRessource hpRessource;
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

      hpRessource = new HealthProfessionalRessource(hpDAO, builder);
    }

    @Test
    void createHealthProfessionalProperly() throws NameAlreadyBoundException {
      HealthProfessionalRequestDto hpRequest = new HealthProfessionalRequestDto(
            "john", "doe", "12345678919", HPSpeciality.GENERALISTE.toString());

        String rppsOfJohn = (String) hpRessource.create(hpRequest);
    
        try {
          HealthProfessional hp = hpRessource.readOne(rppsOfJohn);
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
            "john", "doe", "82837180785", HPSpeciality.GENERALISTE.toString());

        try {
          hpRessource.create(hpRequest);
          fail("The health professional was created with an existing RPPS");
        } catch (NameAlreadyBoundException e) {
          assertEquals(NameAlreadyBoundException.class, e.getClass());
        }
    }

    @Test
    void createHealthProfessionalWithInvalidRPPS() {
      HealthProfessionalRequestDto hpRequest = new HealthProfessionalRequestDto(
            "john", "doe", "1234567891", HPSpeciality.GENERALISTE.toString());

        try {
          hpRessource.create(hpRequest);
          fail("The health professional was created with an invalid RPPS");
        } catch (NameAlreadyBoundException e) {
          fail("The health professional was created with an invalid RPPS");
        } catch (IllegalArgumentException e) {
          assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }

    @Test
    void createHealthProfessionalWithInvalidSpeciality() {
      HealthProfessionalRequestDto hpRequest = new HealthProfessionalRequestDto(
            "john", "doe", "12345678919", "invalid");

        try {
          hpRessource.create(hpRequest);
          fail("The health professional was created with an invalid speciality");
        } catch (NameAlreadyBoundException e) {
          fail("The health professional was created with an invalid speciality");
        } catch (IllegalArgumentException e) {
          assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }

    @Test
    void readOneHealthProfessionalProperly() throws NameNotFoundException {
      HealthProfessional hp = hpRessource.readOne("55334108515");
      assertEquals(eric.getName(), hp.getName());
      assertEquals(eric.getSurname(), hp.getSurname());
      assertEquals(eric.getRPPS(), hp.getRPPS());
      assertEquals(eric.getSpeciality(), hp.getSpeciality());
    }

    @Test
    void readOneHealthProfessionalWithInvalidRPPS() {
      try {
        hpRessource.readOne("12345678919");
        fail("The health professional was read with an invalid RPPS");
      } catch (NameNotFoundException e) {
        assertEquals(NameNotFoundException.class, e.getClass());
      }
    }

    @Test
    void readAllHealthProfessionalsProperly() {
      assertEquals(2, hpRessource.readAll().size());
    }

    @Test
    void updateHealthProfessionalProperly() throws NameNotFoundException {
      HealthProfessionalRequestDto hpRequest = new HealthProfessionalRequestDto(
            "john", "doe", "82837180785", HPSpeciality.GENERALISTE.toString());

        hpRessource.update(hpRequest);
        HealthProfessional hp = hpRessource.readOne("82837180785");
        assertEquals(hpRequest.getName(), hp.getName());
        assertEquals(hpRequest.getSurname(), hp.getSurname());
        assertEquals(hpRequest.getRPPS(), hp.getRPPS());
        assertEquals(hpRequest.getSpeciality(), hp.getSpeciality().toString());
    }

    @Test
    void updateHealthProfessionalWithInvalidRPPS() {
      HealthProfessionalRequestDto hpRequest = new HealthProfessionalRequestDto(
            "john", "doe", "123456789192", HPSpeciality.GENERALISTE.toString());

        try {
          hpRessource.update(hpRequest);
          fail("The health professional was updated with an invalid RPPS");
        } catch (IllegalArgumentException e) {
          assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }

    @Test
    void updateHealthProfessionalWithEmptyRPPS() {
      HealthProfessionalRequestDto hpRequest = new HealthProfessionalRequestDto(
            "john", "doe", "", HPSpeciality.GENERALISTE.toString());

        try {
          hpRessource.update(hpRequest);
          fail("The health professional was updated with an empty RPPS");
        } catch (IllegalArgumentException e) {
          assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }

    @Test
    void deleteByIdHealthProfessionalProperly() throws NameNotFoundException {
      hpRessource.deleteById("55334108515");
      assertEquals(1, hpRessource.readAll().size());
    }

    @Test
    void deleteByIdHealthProfessionalWithInvalidRPPS() {
      try {
        hpRessource.deleteById("12345678919");
        fail("The health professional was deleted with an invalid RPPS");
      } catch (NameNotFoundException e) {
        assertEquals(NameNotFoundException.class, e.getClass());
      }
    }

    @Test
    void deleteByIdHealthProfessionalWithEmptyRPPS() throws NameNotFoundException {
      try {
        hpRessource.deleteById("");
        fail("The health professional was deleted with an empty RPPS");
      } catch (IllegalArgumentException e) {
        assertEquals(IllegalArgumentException.class, e.getClass());
      }
    }

    @Test
    void deleteHealthProfessionalProperly() throws NameNotFoundException {
      HealthProfessionalRequestDto hpRequest = new HealthProfessionalRequestDto(
        "eric", "zemmour", "55334108515", HPSpeciality.CHIRURGIEN.toString());
      hpRessource.delete(hpRequest);
      assertEquals(1, hpRessource.readAll().size());
    }

    @Test
    void deleteHealthProfessionalWithInvalidRPPS() {
      HealthProfessionalRequestDto hpRequest = new HealthProfessionalRequestDto(
        "eric", "zemmour", "12345678919", HPSpeciality.CHIRURGIEN.toString());
      try {
        hpRessource.delete(hpRequest);
        fail("The health professional was deleted with an invalid RPPS");
      } catch (NameNotFoundException e) {
        assertEquals(NameNotFoundException.class, e.getClass());
      }
    }
}
