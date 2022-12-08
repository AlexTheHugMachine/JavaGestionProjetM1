package fr.univ_lyon1.info.m1.mes;

import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

import javafx.stage.Stage;

public abstract class AbstractAppStartup {
  public void loadApplication(final Stage stage) {
    try {
      preLoad();
      initializeJavafx(stage);
      loadConfiguration();
      loadDynamicComponents();
    } catch (Exception ex) {
      handleStartupException("error initialzing application", ex);
    }
  }

  /**
   * Call every methods useful to make the application work (fetching data, ...).
   *
   * @throws IllegalStateException
   * @throws NoSuchElementException
   * @throws NullPointerException
   * @throws FileNotFoundException
   */
  abstract void preLoad()
      throws FileNotFoundException,
      NullPointerException,
      NoSuchElementException,
      IllegalStateException;

  /**
   * Create the scene for JavaFX to work.
   */
  abstract void initializeJavafx(Stage stage);

  /**
   * Load config files stored locally.
   */
  abstract void loadConfiguration();

  /**
   * Function that allow us to create and render components at runtime. This is
   * achieved by referring to a container using a template variable and inject
   * newly created components into it as a result of some user interaction.
   */
  abstract void loadDynamicComponents();

  abstract void handleStartupException(String message, Exception e);
}
