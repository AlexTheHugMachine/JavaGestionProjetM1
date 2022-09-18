package fr.univ_lyon1.info.m1.mes.view;

import java.util.List;

import fr.univ_lyon1.info.m1.mes.controller.Patient;
import fr.univ_lyon1.info.m1.mes.model.Prescription.Prescription;
import fr.univ_lyon1.info.m1.mes.utils.EasyClipboard;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class PatientView {
    private final Pane home = new VBox();
    private final Patient controller;

    public PatientView(Patient controllerPatient, String nom, String ssid, List<Prescription> prescriptionsPatient) {
        this.controller = controllerPatient;
        home.setStyle("-fx-border-color: gray;\n"
                + "-fx-border-insets: 5;\n"
                + "-fx-padding: 5;\n"
                + "-fx-border-width: 1;\n");
        final Label l = new Label(nom);

        // TODO : Bouton qui permet de copier le num de sécu d'un patient. (N'est pas utile pour un patient donc doit être déplacé.)
        final Button bSSID = new Button("📋");
        bSSID.setOnAction(ActionEvent -> controller.copyPatientSSID());

        // Bouton qui permet de reload la liste des prescriptions. (parragé par le patient et le professionnel de santé)
        Button bReload = new Button("🗘");
        bReload.setOnAction(ActionEvent -> controller.refreshPatientList());

        final HBox nameBox = new HBox();
        nameBox.getChildren().addAll(l, bReload);
        home.getChildren().addAll(nameBox, prescriptionPane);
    }

}
