import java.util.List;
import java.util.Map;

import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HealthProfessional;
import fr.univ_lyon1.info.m1.mes.model.Prescription.Prescription;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * Comme on affiche une liste pour le Patient et le Professionnel de santé il
 * faut pouvoir faire en sorte que selon le statut l'endroit où on affiche cette
 * liste on ajoute ou enlève certains bouton.
 */
public class PrescriptionsList {
  private TableView<Prescription> table;
  private List<Prescription> prescriptions;
  private ObservableList<Prescription> data;

  // HP1 : {[content]}, HP2 : ...
  PrescriptionsList(List<Prescription> prescriptionsPatientPourChaqueHP) {
    this.prescriptions = prescriptionsPatientPourChaqueHP;
    // Logiquement si on met à jour les prescriptions sans créer un nouvelle objet
    // le tableau se mettra tous seul à jour.

  }

  public void buildData() {
    data = FXCollections.observableArrayList(this.prescriptions);

    // Map qui a comme clé tous les attributs de la classe Prescription sauf
    // idHP, idPrescription
    TableColumn<String, String> colContent = new TableColumn("Prescriptions");
    // TODO : Voir comment générer plusieurs colonnes en fonction des attributs de la classe
    prescriptions.forEach((prescription) -> System.out.print(prescription.getClass().getDeclaredFields()));
    // Sans connaître ces derniers.

    TableColumn<Map.Entry<String, String>, String> column1 = new TableColumn<>("Key");

  }

}
