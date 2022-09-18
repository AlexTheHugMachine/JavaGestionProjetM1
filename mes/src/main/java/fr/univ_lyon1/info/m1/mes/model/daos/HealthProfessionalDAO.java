package fr.univ_lyon1.info.m1.mes.model.daos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal.HealthProfessional;

public class HealthProfessionalDAO extends AbstractMapDao<HealthProfessional> {
  @Override
  protected Serializable getKeyForElement(final HealthProfessional element) {
    return element.getId();
  }

  public List<HealthProfessional> findManyHealthProfessional(final List<String> listOfIds) {
    List<HealthProfessional> result = new ArrayList<HealthProfessional>();
    listOfIds.forEach((id) -> {
      HealthProfessional foundedHP = this.getCollection().get(id);
      if (foundedHP != null) {
        result.add(foundedHP);
      }
    });
    return result;
  }
}
