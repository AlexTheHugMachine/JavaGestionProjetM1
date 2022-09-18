package fr.univ_lyon1.info.m1.mes.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public interface MESFileReader {
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
