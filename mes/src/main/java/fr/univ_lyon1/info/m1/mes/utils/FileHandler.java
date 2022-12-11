package fr.univ_lyon1.info.m1.mes.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public interface FileHandler {

  /**
   * Recopie le contenu d'une chaine de charactère dans un fichier dont le chemin
   * et nom sont spécifiés en params.
   *
   * @param content  Ce que l'on veut ajouter dans le fichier.
   * @param path
   * @param filename
   * @throws IOException Problème lors de l'écriture du fichier.
   */
  static void writeContentToFile(
      final String content,
      final String path,
      final String filename)
      throws IOException {
    // Avoid useless computation.
    if (!content.isEmpty() && content != null) {
      FileWriter fileWriter = new FileWriter(path + filename);
      PrintWriter printWriter = new PrintWriter(fileWriter);
      printWriter.print(content);
      printWriter.close();
    }
  }

  static String[] listFiles(String path) {
    File stream = new File(path);
    String[] fileList = stream.list();
    return fileList;
  }

  /**
   *
   * @param pathname Combination of path + name
   * @throws IllegalArgumentException If the argument is null or empty.
   * @throws IllegalStateException    If the deletion failed.
   * @throws SecurityException        Can happen if the rights of the folder are
   *                                  strict.
   * @throws NullPointerException     Throwed when path + filename is null.
   */
  static void removeFile(final String pathname)
      throws IllegalArgumentException, IllegalStateException, NullPointerException {
    if (pathname.isEmpty() || pathname == null) {
      throw new IllegalArgumentException("The filename is empty or empty.");
    }

    File file = new File(pathname);
    if (file.delete()) {
      System.out.println("Deleted the file: " + file.getName());
    } else {
      throw new IllegalStateException("Failed to delete the file at : " + pathname);
    }
  }
}
