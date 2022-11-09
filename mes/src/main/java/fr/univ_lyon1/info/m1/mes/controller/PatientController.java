package fr.univ_lyon1.info.m1.mes.controller;
import java.util.List;

import fr.univ_lyon1.info.m1.mes.model.Prescription.Prescription;
import fr.univ_lyon1.info.m1.mes.utils.EasyClipboard;
import fr.univ_lyon1.info.m1.mes.view.PatientView;
import fr.univ_lyon1.info.m1.mes.view.PrescriptionsList;

// Handle all the action that the Patient trigger from the interface.
public class Patient {
  private fr.univ_lyon1.info.m1.mes.model.Patient.Patient currentPatient;
  private List<Prescription> listPrescriptions;
  private PatientView patientView;

  Patient(MES mes) {
    // On doit avoir un controller encore un lvl au dessus qui a les informations
    // de la session.
    // SuperController peut pas être statique sinon on va recréer les objets à chaque fois.
    //currentPatient = superController.getPatientDAO();

    // Initialise la vue Patient.

  }

  // Avec le DAO Prescription je veux récupérer la liste des id de prescriptions du patient en fonction de l'ID du patient.
  // Je vais appeler la fonction updateListPrescription qui est dans Patient et passer la liste que j'ai eu via le DAO Prescription en param.


  public void refreshPatientList() {
    patientView.prescriptionPane.getChildren().clear();
    patientView.showPrescriptionsList();
  }

  public void listPrescriptions() {
    // On a besoin des infos du patient de la session.
    this.listPrescriptions = currentPatient.getPrescriptions();
    // Charge la vue des prescriptions.
    PatientView.add(PrescriptionsList(this.listPrescriptions));
  }

  public void deletePrescription(String idPrescription) {
    return true;
  }

  public void updatePersonnalInformations() {

  }

  public void copyPatientSSID() {
    EasyClipboard.copy(currentPatient.getSSID());
  }
}
