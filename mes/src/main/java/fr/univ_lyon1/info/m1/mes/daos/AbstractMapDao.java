package fr.univ_lyon1.info.m1.mes.daos;

import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Début d'implémentation de l'interface DAO sous forme d'une Map d'objets.
 * Classe abstraite qui doit être instanciée en fonction du type d'objet stocké
 * et de clé.
 *
 * @param <T> Le type d'objet auquel s'applique le DAO ; défini dans une
 *            sous-classe
 *
 * @author Lionel Médini
 */
public abstract class AbstractMapDao<T> implements Dao<T> {
  private Map<Serializable, T> collection;

  AbstractMapDao() {
    this.collection = new HashMap<>();
  }

  protected Map<Serializable, T> getCollection() {
    return this.collection;
  }

  @Override
  public Serializable add(final T element) throws NameAlreadyBoundException {
    Serializable key = getKeyForElement(element);
    if (this.collection.containsKey(key)) {
      throw new NameAlreadyBoundException("This patient already exist.");
    }
    this.collection.put(key, element);
    return key;
  }

  @Override
  public void delete(final T element) throws NameNotFoundException {
    Serializable key = getKeyForElement(element);
    if (!this.collection.containsKey(key)) {
      throw new NameNotFoundException(key.toString());
    }
    this.collection.remove(key);
  }

  @Override
  public void deleteById(final Serializable id) throws NameNotFoundException {
      if (!this.collection.containsKey(id)) {
        throw new NameNotFoundException("No prescription found.");
      }
      this.collection.remove(id);
  }

  @Override
  public void update(final Serializable id, final T element) {
      this.collection.put(id, element);
  }

  @Override
  public Serializable getId(final T element) {
    return getKeyForElement(element);
  }

  @Override
  public Set<Serializable> getAllIds() {
    return this.collection.keySet().stream().filter(Objects::nonNull).collect(Collectors.toSet());
  }

  @Override
  public T findOne(final Serializable id) throws NameNotFoundException {
      if (!this.collection.containsKey(id)) {
        throw new NameNotFoundException(id.toString());
      }
      return this.collection.get(id);
  }

  @Override
  public Collection<T> findAll() {
    return this.collection.values();
  }

  @Override
  public Collection<T> findByIds(final Collection<Serializable> ids)
      throws NameNotFoundException,
      IllegalArgumentException {
    try {
      if (ids == null || ids.isEmpty()) {
        throw new IllegalArgumentException("List of ids is empty.");
      }
      Collection<T> result = new ArrayList<T>();
      ids.forEach((id) -> {
        T foundedMatchingElement = this.collection.get(id);
        if (foundedMatchingElement != null) {
          result.add(foundedMatchingElement);
        }
      });

      if (result.isEmpty() || ids.size() != result.size()) {
        throw new NameNotFoundException("One or multiples items aren't present.");
      }
      return result;
    } catch (NullPointerException e) {
      throw new NullPointerException(e.getMessage());
    }
  }

  /**
   * Renvoie la clé de l'élément<br>
   * Le type de la clé peut être choisi spécifiquement au type d'objet stocké.
   * Exemples : un champ "id" d'une classe, un hash des champs de l'objet...
   *
   * @param element élément du type de la classe à stocker dans le DAO
   * @return une clé qui est une instance d'une sous-classe de
   *         <code>Serializable</code>
   */
  protected abstract Serializable getKeyForElement(T element);
}
