package fr.univ_lyon1.info.m1.mes.controller;

import java.util.Collection;

import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;

import fr.univ_lyon1.info.m1.mes.builders.HealthProfessionnal.HealthProfessionalBuilder;
import fr.univ_lyon1.info.m1.mes.daos.HealthProfessionalDAO;
import fr.univ_lyon1.info.m1.mes.dto.healthprofessional.HealthProfessionalRequestDto;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HealthProfessional;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.operations.HealthProfessionalRessource;

public class HealthProfessionalRessourceController {

    private final HealthProfessionalRessource healthProfessionalRessource;

    public HealthProfessionalRessourceController(final HealthProfessionalDAO healthProfessionalDAO,
            final HealthProfessionalBuilder healthProfessionalBuilder) {
        healthProfessionalRessource = new HealthProfessionalRessource(
                healthProfessionalDAO, healthProfessionalBuilder);
    }

    public void removeHealthProfessionalById(final String healthProfessionalId) throws NameNotFoundException {
        healthProfessionalRessource.deleteById(healthProfessionalId);
    }

    public void removeHealthProfessional(final HealthProfessionalRequestDto hPDto) throws NameNotFoundException {
        healthProfessionalRessource.delete(hPDto);
    }

    public void createDtoHealthProfessional(final HealthProfessionalRequestDto hPDto) throws NameAlreadyBoundException {
        healthProfessionalRessource.create(hPDto);
    }

    public HealthProfessional getHealthProfessional(final String healthProfessionalId) 
            throws NameNotFoundException {
        return healthProfessionalRessource.readOne(healthProfessionalId);
    }

    public Collection<HealthProfessional> getHealthProfessionals() {
        return healthProfessionalRessource.readAll();
    }

    public void updateHealthProfessional(final HealthProfessionalRequestDto hPDto) {
        healthProfessionalRessource.update(hPDto);
    }
}
