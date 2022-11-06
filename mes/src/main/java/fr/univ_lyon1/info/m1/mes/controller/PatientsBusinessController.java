package fr.univ_lyon1.info.m1.mes.controller;

import javax.naming.InvalidNameException;
import javax.naming.NameNotFoundException;

import fr.univ_lyon1.info.m1.mes.model.Patient.Patient;
import fr.univ_lyon1.info.m1.mes.model.Patient.PatientBusiness;
import fr.univ_lyon1.info.m1.mes.model.daos.PrescriptionDAO;

/**
 * Contrôleur délégué aux CU concernant les opérations métier sur les patients.
 * Concrètement : <br>
 * - Un patient doit pouvoir consulter les prescriptions qui lui ont étés
 * données.✅ <br>
 * - Un patient doit pouvoir supprimer lui même des prescriptions. ✅ <br>
 * - Un patient doit pouvoir changer son Adresse et sa Ville. ✅ <br>
 * - Un patient doit pouvoir se connecter. (TODO) <br>
 * - Un patient doit pouvoir se déconnecter. (TODO) <br>
 */
public class PatientsBusinessController {
  private PatientBusiness patientBusiness;

  public PatientsBusinessController(final PrescriptionDAO prescriptionDAO) {
    patientBusiness = new PatientBusiness(prescriptionDAO);
  }

  /**
   *
   * @param action Décris quelle fonction on veut éxécuter.
   * @param args   Un tableau de paramètre que l'on passera en paramètre des
   *               appels aux fonctions correspondant à l'action demandé.
   * @return <code>boolean | List</code> : En fonction de l'action
   *         demandé on peut
   *         ne rien retourné ou une List de Prescription.
   * @throws NameNotFoundException    L'id passé en paramètre de la fonction de
   *                                  suppression d'une prescription est null dans
   *                                  le
   *                                  tableau data.
   * @throws InvalidNameException     Si la synthaxe de la clé de prescription
   *                                  n'est pas valide.
   * @throws IllegalArgumentException Nous n'avons pas passé une action valide en
   *                                  paramètre.
   */
  public Object handler(final String action, final Object[] args)
      throws NameNotFoundException,
      InvalidNameException {
    switch (action) {
      case "getPrescription":
        return patientBusiness.getPrescriptionsPatient((Patient) args[0]);
      case "removePrescription":
        patientBusiness.removePrescription((String) args[0]);
        return true;
      case "changeLocationInformations":
        patientBusiness.changeLocationInformations(
            (Patient) args[0],
            (String) args[1],
            (String) args[2]);
        return true;
      default:
        throw new IllegalArgumentException("No valid action has been passed");
    }
  }

}
