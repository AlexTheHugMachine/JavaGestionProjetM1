package fr.univ_lyon1.info.m1.mes.ressources;

import java.io.Serializable;
import java.util.Collection;

import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;


/**
 * Interface que peuvent implémenter toutes les classes de gestion
 * des ressources selon qu'on utilise une base de données, un DAO ... etc.
 *
 * @param <T> Les éléments que l'on souhaite manipuler avec cette classe.
 * @param <S> Les éléments que l'on souhaite retourner au client.
 */
public interface RessourceInterface<T, S> {
  /**
   * A partir de l'élément passé en paramètre on va aller ajouter une nouvelle
   * ressource dans le système de gestion des données que l'on utilise.
   *
   * @param element Object qui contient toutes les informations dont on a besoin
   *                pour créer la nouvelle ressource dans le système de gestion
   *                des données.
   * @throws NameAlreadyBoundException L'élément que l'on essaie d'ajouter est
   *                                   déjà présent.
   */
  Serializable create(T element) throws NameAlreadyBoundException;

  // WIP : Could be use as a transactionnal query.
  // void createMany();

  /**
   * Cherche un élément à partir d'un identifiant dans le système de gestion des
   * données que l'on utilise.
   *
   * @param key L'identifiant qui nous permettra de trouver l'élément recherché.
   * @throws NameNotFoundException Aucune ressource avec cette identifiant n'a été
   *                               trouvé.
   */
  S readOne(Serializable key) throws NameNotFoundException;

  /**
   * Va chercher toutes les ressources stockés.
   *
   * @return Une liste de toutes les ressources stockés.
   */
  Collection<S> readAll();

  /*
   * A partir d'une liste d'identifiant, on va pouvoir retrouver de nombreux
   * éléments.
   *
   * @param keys
   *
   * @return Tous les éléments qui correspondent aux identifiants passés en
   * paramètre.
   */
  // List<T> readMany(List<Serializable> keys);

  /**
   * A partir de l'élément passé en paramètre qui stock les nouvelles infos, on va
   * aller mettre à jour une ressource dans le système de gestion des données que
   * l'on utilise.
   *
   * @param element Object qui contient toutes les nouvelles infos.
   * @return true si mis à jour, false sinon.
   */
  Boolean update(T element);

  /**
   * Va supprimer la ressource dont l'id correspont à la clé que l'on a passé en
   * paramètre.
   *
   * @param key Clé qui permet d'identifier la ressource à supprimer.
   * @return
   * @throws NameNotFoundException La ressource que l'on essaie de supprimer
   *                               n'existe plus.
   */
  Boolean deleteById(Serializable key) throws NameNotFoundException;

  /**
   * A partir de l'élément passé en paramètre on va aller supprimer la ressource
   * stocké dans le gestionnaire de données que l'on utilise.
   *
   * @param element Object qui représente la ressource que l'on souhaite supprimer
   *                dans le gestionaire de données.
   * @throws NameNotFoundException Si l'élément ne correspond à aucune ressource
   *                               stocké.
   */
  void delete(T element) throws NameNotFoundException;
}
