package fr.univ_lyon1.info.m1.mes.controller;

import java.util.ArrayList;

import javax.naming.NameNotFoundException;

import fr.univ_lyon1.info.m1.mes.builders.HealthProfessionnal.HealthProfessionalBuilder;
import fr.univ_lyon1.info.m1.mes.daos.HealthProfessionalDAO;
import fr.univ_lyon1.info.m1.mes.daos.PrescriptionDAO;
import fr.univ_lyon1.info.m1.mes.dto.healthprofessional.HealthProfessionalRequestDto;
import fr.univ_lyon1.info.m1.mes.dto.patient.PatientRequestDto;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HPSpeciality;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HealthProfessional;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.operations.HealthProfessionalRessource;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.operations.HealthProfessionnalBusiness;

public class HealthProfessionalController {

    public HealthProfessionalController(final HealthProfessionalDAO healthProfessionalDAO,
            final PrescriptionDAO prescriptionDAO,
            final HealthProfessionalBuilder healthProfessionalBuilder) {
    }


    public void removeHealthProfessional(final String healthProfessionalId) {
        HealthProfessionalRessource ressource = new HealthProfessionalRessource();
        ressource.deleteById(healthProfessionalId);
        
    }

    public void createDtoHealthProfessional(final String name, final String surname, 
            final String rpps, final String speciality) {
        HealthProfessionalRequestDto dto = new 
            HealthProfessionalRequestDto(name, surname, rpps, speciality);
        HealthProfessionalRessource ressource = new HealthProfessionalRessource();
        ressource.create(dto);
    }

    public void createDtoPatient(final String name, final String surname, 
            final String ssid, final String adress, final String city) {
        PatientRequestDto dto = new PatientRequestDto(name, surname, ssid, adress, city);
    }

    public HealthProfessional getHealthProfessional(final String healthProfessionalId) 
            throws NameNotFoundException {
        /*HealthProfessionalRessource HealthProfessionalRessource = new 
            HealthProfessionalRessource();
        return HealthProfessionalRessource.readOne(healthProfessionalId);*/
        return null;
    }

    public ArrayList<HealthProfessional> getHealthProfessionals() {
        return null;
    }

    public void updateHealthProfessional(final String healthProfessionalId, 
            final String name, final String surname, final String rpps,
            final HPSpeciality speciality) {
    }

    public void createDtoPrescription(String content, String quantite, String idPrescription, String idHP,
            String idPatient) {
    }

    public void removePrescription(String prescriptionId) {
    }

}
