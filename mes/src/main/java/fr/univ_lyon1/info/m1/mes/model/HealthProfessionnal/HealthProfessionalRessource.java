package fr.univ_lyon1.info.m1.mes.model.HealthProfessionnal;

import java.io.Serializable;
import java.util.Set;

import javax.naming.InvalidNameException;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;

import fr.univ_lyon1.info.m1.mes.builders.HealthProfessionnal.HealthProfessionalBuilder;
import fr.univ_lyon1.info.m1.mes.daos.HealthProfessionalDAO;
import fr.univ_lyon1.info.m1.mes.directors.HealthProfessionalsDirector;
import fr.univ_lyon1.info.m1.mes.utils.ArgumentChecker;

/**
 * Réalise les opérations "simples" CRUD de gestion des ressources de type
 * <code>HealthProfessionnal</code>. <br>
 * Cette classe est créer et utilisée par le contrôleur de ressources
 * <code>HealthProfessionnalController</code> qui lui injected le DAO et le
 * builder
 * pour créer des patients.
 */
public class HealthProfessionalRessource {
  private final HealthProfessionalDAO hpDao;
  private final HealthProfessionalBuilder builder;
  private final HealthProfessionalsDirector director;

  // TODO : En reprenant la classe PatientRessource écrire les commentaires pour
  // Cette fonction.
  public String create(
      final String name, final String surname)
      throws NameAlreadyBoundException,
      IllegalArgumentException,
      InvalidNameException {
    String[] check = {name, surname};
    ArgumentChecker.checkManyString(check);
    HealthProfessional hp = director.constructGeneraliste(builder, name, surname);
    hpDao.add(hp);
    return hp.getId();
  }

  /**
   * Renvoie les IDs de tous les HealthProfessionnal présents dans le DAO.
   *
   * @return L'ensemble des IDs sous forme d'un
   *         <code>Set&lt;Serializable&gt;</code>
   */
  public Set<Serializable> readAll() {
    return hpDao.getAllIds();
  }

  // TODO :
  public HealthProfessional readOne(final String key)
      throws NumberFormatException,
      NameNotFoundException,
      InvalidNameException {
    ArgumentChecker.checkString(key);
    return hpDao.findOne(Integer.valueOf(key));
  }

  // TODO :
  public void update(final String key, final String name, final String surname)
      throws NumberFormatException,
      NameNotFoundException,
      InvalidNameException {
    HealthProfessional hp = readOne(key);
    if (name != null && !name.equals("")) {
      hp.setName(name);
    }
    if (surname != null && !surname.equals("")) {
      hp.setSurname(surname);
    }
  }

  // TODO
  public void delete(final String key)
      throws NumberFormatException,
      NameNotFoundException,
      InvalidNameException {
    ArgumentChecker.checkString(key);
    hpDao.deleteById(Integer.valueOf(key));
  }

  public HealthProfessionalRessource(
      final HealthProfessionalDAO hpDao,
      final HealthProfessionalBuilder builder,
      final HealthProfessionalsDirector director) {
    this.hpDao = hpDao;
    this.director = director;
    this.builder = builder;
  }
}
