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
}
