package fr.univ_lyon1.info.m1.mes.initializer;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import javax.naming.NameAlreadyBoundException;

import fr.univ_lyon1.info.m1.mes.constants.Constants;
import fr.univ_lyon1.info.m1.mes.daos.AbstractMapDao;

public abstract class Initializer<T, S> {

  /**
   * @param filename
   * @param builder
   * @param dao
   */
 /* public void initDAOs(
      final String filename,
      final S builder,
      final AbstractMapDao<T> dao)

      throws FileNotFoundException,
      NullPointerException,
      NoSuchElementException,
      IllegalStateException {
    ArrayList<String[]> list = MESFileReader.readFile(Constants.getLocalPath() + filename);
    list.forEach(row -> {
      T element = build(builder, row);
      try {
        dao.add(element);
      } catch (NameAlreadyBoundException e) {
        throw new IllegalStateException("The element is already inside.");
      }
    });
  }*/

  protected abstract T build(S builder, String[] row);


}
