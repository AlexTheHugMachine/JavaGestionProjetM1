package fr.univ_lyon1.info.m1.mes;

import fr.univ_lyon1.info.m1.mes.builders.HealthProfessionnal.HealthProfessionalBuilder;
import fr.univ_lyon1.info.m1.mes.builders.Patient.PatientBuilder;
import fr.univ_lyon1.info.m1.mes.controller.MESController;
import fr.univ_lyon1.info.m1.mes.daos.HealthProfessionalDAO;
import fr.univ_lyon1.info.m1.mes.daos.PatientDAO;
import fr.univ_lyon1.info.m1.mes.view.JfxView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class for the application (structure imposed by JavaFX).
 */
public class App extends Application {
    private PatientBuilder patientBuilder = new PatientBuilder();
    private HealthProfessionalBuilder healthProfessionalBuilder = new HealthProfessionalBuilder();
    private PatientDAO patientDAO = new PatientDAO();
    private HealthProfessionalDAO healthProfessionalDAO = new HealthProfessionalDAO();

    /**
     * With javafx, init() is called before the start() method to create or load
     * Objects importants for the application work.
     */
    @Override
    public void init() {
        new AppStartup().loadApplication();
    }

    /**
     * With javafx, start() is called when the application is launched.
     */
    @Override
    public void start(final Stage stage) throws Exception {
        appStartup.loadApplication();
        final MESController mastController = new MESController(
                patientBuilder,
                healthProfessionalBuilder,
                patientDAO,
                healthProfessionalDAO);

        new JfxView(model, stage, 600, 600);

        // Second view
        new JfxView(model, stage, 600, 600);
    }

    /**
     * A main method in case the user launches the application using
     * App as the main class.
     */
    public static void main(final String[] args) {
        Application.launch(args);
    }
}
