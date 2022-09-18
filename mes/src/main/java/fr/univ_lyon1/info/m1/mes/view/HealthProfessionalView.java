package fr.univ_lyon1.info.m1.mes.view;

import java.util.ArrayList;
import java.util.List;

import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HealthProfessional;
import fr.univ_lyon1.info.m1.mes.model.Patient.Patient;
import fr.univ_lyon1.info.m1.mes.model.Prescription.Prescription;
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
        // Label avec le nom du docteur
        final Label l = new Label(hp.getName());
        pane.getChildren().add(l);
        // Container de l'input recherche et bouton search
        final HBox search = new HBox();
        // | Input pour le numéro de sécu | Bouton Search
        final TextField t = new TextField();
        final Button b = new Button("Search");
        // Ajout dans le pane
        search.getChildren().addAll(t, b);
        pane.getChildren().addAll(search, prescriptions);
        // Ajout de la fonction handler qui s'occupe de gérer le cas où on a rien mis et
        // d'afficher les prescriptions du patient selectedPatientSSID.
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

        // Partie où le doc ajoute des prescriptions.
        pane.getChildren().add(new Label("Prescribe"));
        // Container pour l'input et le bouton Add
        final HBox addPrescription = new HBox();
        final TextField tp = new TextField();
        final Button bp = new Button("Add");
        // On ajoute le tout dans le container.
        addPrescription.getChildren().addAll(tp, bp);
        // On ajoute le tout dans le super Container parent qui affiche tout.
        pane.getChildren().add(addPrescription);
        // WTF ?
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
        // Cette partie est à gérer dans le controleur et avec le model pour que le
        // controleur envoie la bonne string à la vue.
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
        // Génère des boutons en fonction de la liste que l'on aura passé en paramètre.
        // Extraire dans une nouvelle fonction ou classe si ça peut aller avec autre
        // chose du même type.
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
        // Faire remonter au controleur que l'on veut ajouter une prescription via le
        // numéro du patient : selectedPatientSSID
        // La chaine de charactères : prescription
        // Le professionnel de santé : healthProfessional
        healthProfessional
                .getPatient(selectedPatientSSID)
                .addPrescription(healthProfessional, prescription);
        showPrescriptions();
    }

    void showPrescriptions() {
        prescriptions.getChildren().clear();
        // On doit passer en paramètre de cette fonction le patient.
        Patient p = healthProfessional.getPatient(selectedPatientSSID);
        if (p == null) {
            prescriptions.getChildren().add(new Label(
                    "Use search above to see prescriptions"));
            return;
        }
        prescriptions.getChildren().add(new Label(
                "Prescriptions for " + p.getName()));
        // Extraire ce morceau de code et le mettre dans une autre fonction (voir une
        // autre class responsable de l'affichage des prescriptions).
        for (final Prescription pr : p.getPrescriptions(healthProfessional)) {
            // Extraire dans une fonction les instructions suivantes afin d'avoir un objet
            // que l'on peut paramètrer facielement pour afficher une nouvelle prescription.
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
