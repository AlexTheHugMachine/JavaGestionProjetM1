package fr.univ_lyon1.info.m1.mes.controller;

import javax.naming.InvalidNameException;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;

import fr.univ_lyon1.info.m1.mes.model.Prescription.Prescription;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.operations.HealthProfessionnalBusiness;
import fr.univ_lyon1.info.m1.mes.model.Patient.Patient;

/**
 * Contrôleur délégué aux CU concernant les opérations métier sur les patiens.
 * Concrètement :
 * - Un Professionnel de santé doit pouvoir chercher un patient avec son num
 * de sécurité sociale. ✅ <br>
 * - Un Professionnel de santé doit pouvoir voir les infos d'un patient.✅ <br>
 * - Un Professionnel de santé doit pouvoir Ajouter une Prescription pour un
 * Patient. ✅ <br>
 * - Un Professionnel de santé doit pouvoir Supprimer une Prescription d'un
 * Patient.✅ <br>
 * - Un professionnel de santé doit pouvoir créer un Patient.✅ <br>
 */
public class HealthProfessionnalBusinessController {
  private HealthProfessionnalBusiness hpBusiness;

  public Object handler(final String action, final Object[] args)
      throws NameNotFoundException,
      InvalidNameException,
      NameAlreadyBoundException {
    switch (action) {
      case "getPatientBySSID":
        return hpBusiness.getPatientBySSID((String) args[0]);
      case "getPatientInfos":
        return hpBusiness.getPatientInfos((String) args[0]);
      case "addPrescription":
        return hpBusiness.addprescription((Prescription) args[0]);
      case "removePrescription":
        return hpBusiness.removePrescription((String) args[0]);
      case "createPatient":
        return hpBusiness.createPatient((Patient) args[0]);
      default:
        throw new IllegalArgumentException("No valid action has been passed.");
    }
  }

  public HealthProfessionnalBusinessController(final HealthProfessionnalBusiness hpBusiness) {
    this.hpBusiness = hpBusiness;
  }

}
