package fr.univ_lyon1.info.m1.mes.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;

/**
 * Only used for tests and should stay like that because it could cause some
 * security breaches.
 */
public final class ExecuteScript {

  private static class StreamGobbler implements Runnable {
    private InputStream inputStream;
    private Consumer<String> consumer;

    StreamGobbler(final InputStream inputStream, final Consumer<String> consumer) {
      this.inputStream = inputStream;
      this.consumer = consumer;
    }

    @Override
    public void run() {
      new BufferedReader(new InputStreamReader(inputStream)).lines()
          .forEach(consumer);
    }
  }

  static void execute(final String path, final String scriptToExecute)
      throws IOException, InterruptedException, ExecutionException {
    ProcessBuilder builder = new ProcessBuilder();
    boolean isWindows = System.getProperty("os.name")
        .toLowerCase().startsWith("windows");

    // Place
    builder.directory(new File(path));

    if (isWindows) {
      builder.command("cmd.exe", "/c", "./" + scriptToExecute);
    } else {
      builder.command("sh", "-c", "./" + scriptToExecute);
    }

    Process process = builder.start();
    StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), System.out::println);
    Future<?> future = Executors.newSingleThreadExecutor().submit(streamGobbler);

    int exitCode = process.waitFor();
    assert exitCode == 0;

    future.get(); // waits for streamGobbler to finish
  }

  private ExecuteScript() {
  }
}
