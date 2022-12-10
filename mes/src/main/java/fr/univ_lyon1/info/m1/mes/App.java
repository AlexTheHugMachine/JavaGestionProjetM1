package fr.univ_lyon1.info.m1.mes;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
  /**
   * With javafx, start() is called when the application is launched.
   */
  @Override
  public void start(final Stage stage) throws Exception {
    AppStartup app = new AppStartup();
    app.loadApplication(stage);
    app.writeErrorQueueToLogFile();

  }

  @Override
  public void stop() {
    // Read queues and save the new data in the correct path.
  }

  /**
   * A main method in case the user launches the application using
   * App as the main class.
   */
  public static void main(final String[] args) {
    Application.launch(args);
  }
}
