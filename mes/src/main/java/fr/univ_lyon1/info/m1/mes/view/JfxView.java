package fr.univ_lyon1.info.m1.mes.view;

import fr.univ_lyon1.info.m1.mes.controller.MESController;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JfxView {
  private Pane patients = new VBox();
  private Pane healthPro = new VBox();
  private final MESController mes;

  /**
   * Create the main view of the application.
   */
  public JfxView(final MESController mes, final Stage stage,
      final int width, final int height) {
    this.mes = mes;
    // Name of window
    stage.setTitle("Mon Espace Santé");

    final HBox root = new HBox(10);

    // createPatientsWidget();
    root.getChildren().add(patients);

    // createHPWidget();
    root.getChildren().add(healthPro);

    HBox.setHgrow(patients, Priority.SOMETIMES);
    HBox.setHgrow(healthPro, Priority.ALWAYS);

    // Everything's ready: add it to the scene and display it
    final Scene scene = new Scene(root, width, height);
    stage.setScene(scene);
    stage.show();
  }
}
