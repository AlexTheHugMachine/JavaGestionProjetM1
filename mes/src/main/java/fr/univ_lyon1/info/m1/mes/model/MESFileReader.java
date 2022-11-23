package fr.univ_lyon1.info.m1.mes.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public interface MESFileReader {
  /**
   *
   * @param pathToFile
   * @return List of <code>String[]</code> where each rows corresponds to the
   *         informations stored for each Users.
   * @throws NullPointerException   Throwed if we passed a null path.
   * @throws FileNotFoundException  Throwed when the <code>File</code> does not
   *                                exist.
   * @throws NoSuchElementException Should not happen.
   * @throws IllegalStateException  If the scanner used to read the file is closed
   *                                while we are stille reading it.
   */
  static ArrayList<String[]> readFile(String pathToFile) throws FileNotFoundException,
      NullPointerException,
      NoSuchElementException,
      IllegalStateException {

    ArrayList<String[]> listOfData = new ArrayList<String[]>();

    File fileInput = new File(pathToFile);
    Scanner reader = new Scanner(fileInput);

    while (reader.hasNextLine()) {
      String data = reader.nextLine();
      listOfData.add(data.split(" "));
    }
    reader.close();
    return listOfData;
  }
}
