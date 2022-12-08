package fr.univ_lyon1.info.m1.mes.initializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NameAlreadyBoundException;

import fr.univ_lyon1.info.m1.mes.utils.YmlFileHandler;

public class Initializer<T, S> {
  public List<T> initializeDAOs(final String path, final S dao, final Class<?> objectClass)
      throws FileNotFoundException, NameAlreadyBoundException {
    File stream = new File(path);
    String[] fileList = stream.list();
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
