package fr.univ_lyon1.info.m1.mes.view;

import fr.univ_lyon1.info.m1.mes.model.Prescription;
import javafx.scene.layout.HBox;

import java.util.List;

/**
 * Comme on affiche une liste pour le Patient et le Professionnel de santé il
 * faut pouvoir faire en sorte que selon le statut l'endroit où on affiche cette
 * liste on ajoute ou enlève certains bouton.
 */
public class PrescriptionsList {
  private HBox prescriptionContainer;

  public PrescriptionsList(List<Prescription> prescriptions) {

  }

}
