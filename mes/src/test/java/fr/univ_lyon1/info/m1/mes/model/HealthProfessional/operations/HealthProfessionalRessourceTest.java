package fr.univ_lyon1.info.m1.mes.model.HealthProfessional.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.naming.NameAlreadyBoundException;

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
            "john", "doe", "123456789", HPSpeciality.GENERALISTE.toString());
        HealthProfessional hp = (HealthProfessional) hpRessource.create(hpRequest);
    
        assertEquals(hpRequest.getName(), hp.getName());
        assertEquals(hpRequest.getSurname(), hp.getSurname());
        assertEquals(hpRequest.getRPPS(), hp.getRPPS());
        assertEquals(hpRequest.getSpeciality(), hp.getSpeciality());
    }
}