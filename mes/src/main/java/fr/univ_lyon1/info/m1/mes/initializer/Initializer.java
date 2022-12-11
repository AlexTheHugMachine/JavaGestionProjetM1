package fr.univ_lyon1.info.m1.mes.initializer;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import fr.univ_lyon1.info.m1.mes.utils.FileHandler;
import fr.univ_lyon1.info.m1.mes.utils.YmlFileHandler;

public class Initializer<T> {
  public List<T> initializeDAOs(final String path, final Class<?> objectClass)
      throws FileNotFoundException {
    String[] fileList = FileHandler.listFiles(path);
    List<T> listOfObjectToInsert = new ArrayList<T>();
    for (String filename : fileList) {
      final T response = (T) YmlFileHandler.readToCustomType(
          path + filename,
          objectClass);
      listOfObjectToInsert.add(response);
    }
    return listOfObjectToInsert;
  }

}
