package fr.univ_lyon1.info.m1.mes.view;


import java.util.ArrayList;
import java.util.List;
import fr.univ_lyon1.info.m1.mes.model.Dentist;
import fr.univ_lyon1.info.m1.mes.model.Homeopath;
import fr.univ_lyon1.info.m1.mes.model.Masseur;
import fr.univ_lyon1.info.m1.mes.model.HealthProfessional;
import fr.univ_lyon1.info.m1.mes.model.Patient;
import fr.univ_lyon1.info.m1.mes.model.Prescription;
import fr.univ_lyon1.info.m1.mes.utils.EasyAlert;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class HealthProfessionalView {
    private final VBox pane = new VBox();
    private HealthProfessional healthProfessional;
    private String selectedPatientSSID;
    private final VBox prescriptions = new VBox();

        public HealthProfessionalView(final HealthProfessional hp) {
        pane.setStyle("-fx-border-color: gray;\n"
                + "-fx-border-insets: 5;\n"
                + "-fx-padding: 5;\n"
                + "-fx-border-width: 1;\n");
        this.healthProfessional = hp;
        final Label l = new Label(hp.getName());
        pane.getChildren().add(l);
        final HBox search = new HBox();
        final TextField t = new TextField();
        final Button b = new Button("Search");
        search.getChildren().addAll(t, b);
        pane.getChildren().addAll(search, prescriptions);
        final EventHandler<ActionEvent> ssHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                final String text = t.getText().trim();
                if (text.equals("")) {
                    return; // Do nothing
                }
                selectedPatientSSID = text;
                showPrescriptions();
                t.setText("");
                t.requestFocus();
            }
        };
        b.setOnAction(ssHandler);
        t.setOnAction(ssHandler);

        pane.getChildren().add(new Label("Prescribe"));
        final HBox addPrescription = new HBox();
        final TextField tp = new TextField();
        final Button bp = new Button("Add");
        addPrescription.getChildren().addAll(tp, bp);
        pane.getChildren().add(addPrescription);
        final HealthProfessionalView parent = this;
        final EventHandler<ActionEvent> prescriptionHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                final String text = tp.getText().trim();
                if (text.equals("")) {
                    return; // Do nothing
                }
                tp.setText("");
                tp.requestFocus();
                parent.prescribe(text);
            }
        };
        // TODO: someone wrote some business logic within the view :-\
        List<String> predefPrescr = new ArrayList<>();
        predefPrescr.add("Paracetamol");
        if (hp instanceof Dentist) {
            predefPrescr.add("Don't eat for one hour");
        } else if (hp instanceof Homeopath) {
            predefPrescr.add("Natrum Muriaticum 30CH");
            predefPrescr.add("Sucre 200K");
        } else if (hp instanceof Masseur) {
            predefPrescr.add("Vaseline 1 kg");
        }
        for (final String p : predefPrescr) {
            final Button predefPrescrB = new Button(p);
            predefPrescrB.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent event) {
                    parent.prescribe(p);
                }
            });
            pane.getChildren().add(predefPrescrB);
        }
        tp.setOnAction(prescriptionHandler);
        bp.setOnAction(prescriptionHandler);
    }
    
    void prescribe(final String prescription) {
        if (selectedPatientSSID == null) {
            EasyAlert.alert("Please select a patient first");
            return;
        }
        healthProfessional
            .getPatient(selectedPatientSSID)
            .addPrescription(healthProfessional, prescription);
        showPrescriptions();
    }

    void showPrescriptions() {
        prescriptions.getChildren().clear();
        Patient p = healthProfessional.getPatient(selectedPatientSSID);
        if (p == null) {
            prescriptions.getChildren().add(new Label(
                "Use search above to see prescriptions"));
            return;
        }
        prescriptions.getChildren().add(new Label(
            "Prescriptions for " + p.getName()));
        for (final Prescription pr : p.getPrescriptions(healthProfessional)) {
            final HBox pView = new HBox();
            final Label content = new Label(
                    "- " + pr.getContent());
            final Button removeBtn = new Button("x");
            removeBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent event) {
                    p.removePrescription(pr);
                    pView.getChildren().remove(content);
                    pView.getChildren().remove(removeBtn);
                }
                
            });
            pView.getChildren().addAll(content, removeBtn);
            prescriptions.getChildren().add(pView);
        }
    }

    public Pane asPane() {
        return pane;
    }

}
