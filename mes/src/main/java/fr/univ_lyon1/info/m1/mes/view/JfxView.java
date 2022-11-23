package fr.univ_lyon1.info.m1.mes.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import fr.univ_lyon1.info.m1.mes.model.MES;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HealthProfessional;
import fr.univ_lyon1.info.m1.mes.model.Patient.Patient;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JfxView {
    private Pane patients = new VBox();
    private Pane healthPro = new VBox();
    private final MES mes;

    /**
     * Create the main view of the application.
     */
    public JfxView(final MES mes, final Stage stage,
            final int width, final int height) {
        this.mes = mes;
        // Name of window
        stage.setTitle("Mon Espace Santé");

        final HBox root = new HBox(10);

        createPatientsWidget();
        root.getChildren().add(patients);

        createHPWidget();
        root.getChildren().add(healthPro);

        HBox.setHgrow(patients, Priority.SOMETIMES);
        HBox.setHgrow(healthPro, Priority.ALWAYS);

        // Everything's ready: add it to the scene and display it
        final Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.show();
    }

    private Pane createHPWidget() {
        for (HealthProfessional p : mes.getHealthProfessional()) {
            HealthProfessionalView hpv = new HealthProfessionalView(p);
            healthPro.getChildren().add(hpv.asPane());
        }
        return healthPro;
    }

    private void createPatientsWidget() {
        patients.getChildren().clear();
        /* for (Patient p : mes.getPatients()) {
            final PatientView hpv = new PatientView(p);
            patients.getChildren().add(hpv.asPane());
        } */
        final Label nameL = new Label("Name: ");
        final TextField nameT = new TextField();
        final Label ssIDL = new Label("ssID: ");
        final TextField ssIDT = new TextField();
        final Button newP = new Button("New");
        patients.getChildren().addAll(
                new HBox(nameL, nameT),
                new HBox(ssIDL, ssIDT),
                newP);
        newP.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                String getNamePatient = nameT.getText();
                String getSSIDPatient = ssIDT.getText();
     /*            final Patient newPatient = mes.createPatient(getNamePatient, getSSIDPatient);
                final PatientView newPatientView = new PatientView(newPatient);
                patients.getChildren().add(newPatientView.asPane());
      */       }
        });
    }
}
