package fr.univ_lyon1.info.m1.mes.daos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InvalidNameException;
import javax.naming.NameNotFoundException;

import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HealthProfessional;
import fr.univ_lyon1.info.m1.mes.utils.ArgumentChecker;

public class HealthProfessionalDAO extends AbstractMapDao<HealthProfessional> {
  @Override
  protected Serializable getKeyForElement(final HealthProfessional element) {
    return element.getRPPS();
  }

  @Override
  public void update(final Serializable id, final HealthProfessional element)
      throws InvalidNameException, IllegalArgumentException {
    if (id.equals(element.getRPPS())) {
      super.update(id, element);
    } else {
      throw new IllegalArgumentException("RPPS are not the same.");
    }
  }

  /**
   *
   * @param name Name of the HealthProfessional we are searching.
   * @return List of matching HealthProfessional given the name in parameters.
   * @throws NameNotFoundException
   */
  public List<HealthProfessional> findByName(final String name)
      throws NameNotFoundException, IllegalArgumentException {
    ArgumentChecker.checkStringNotNullOrEmpty(name);
    List<HealthProfessional> list = new ArrayList<HealthProfessional>();
    this.getCollection().forEach((uid, hp) -> {
      String upperCaseName = hp.getName().toUpperCase();
      if (upperCaseName.equals(name.toUpperCase())) {
        list.add(hp);
      }
    });
    if (list.isEmpty()) {
      throw new NameNotFoundException();
    }
    return list;
  }
}
