package fr.univ_lyon1.info.m1.mes;

import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

public abstract class AbstractAppStartup {
  public void loadApplication() {
    try {
      preLoad();
      initializeJavafx();
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

  abstract void initializeJavafx();

  /**
   * Could be usedful if we save some configuration data for the app.
   */
  abstract void loadConfiguration();

  /**
   * Function that allow us to create and render components at runtime. This is
   * achieved by referring to a container using a template variable and inject
   * newly created components into it as a result of some user interaction.
   */
  abstract void loadDynamicComponents();

  /**
  *
  */
  // abstract void postApplicationLoad();

  abstract void handleStartupException(String message, Exception e);
}
