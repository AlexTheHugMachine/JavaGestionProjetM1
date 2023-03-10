package fr.univ_lyon1.info.m1.mes.utils;

import java.util.List;

public interface ArgumentChecker {

  static void checkStringListNotNullOrEmpty(List<String> list)
      throws IllegalArgumentException {
    if (list == null) {
      throw new IllegalArgumentException("ArgumentChecker Failed Null list. ");
    } else if (list.isEmpty()) {
      throw new IllegalArgumentException("ArgumentChecker Failed Empty list. ");
    }
  }

  /**
   * Vérifie que la variable passé en paramètre n'est pas null ou vide et renvoie
   * un message adapté au cas.
   *
   * @param variable La variable que l'on veut checker.
   * @throws IllegalArgumentException "ArgumentChecker Failed Null | Empty
   *                                  variable. "
   */
  static void checkStringNotNullOrEmpty(String variable)
      throws IllegalArgumentException {
    if (variable == null) {
      throw new IllegalArgumentException("ArgumentChecker Failed Null variable. ");
    } else if (variable.isEmpty()) {
      throw new IllegalArgumentException("ArgumentChecker Failed Empty variable. ");
    }
  }

  static void checkManyStringNotNullOrEmpty(List<String> variableList)
      throws IllegalArgumentException {
    for (String variable : variableList) {
      if (variable == null) {
        throw new IllegalArgumentException("ArgumentChecker Failed Null element. ");
      } else if (variable.isEmpty()) {
        throw new IllegalArgumentException("ArgumentChecker Failed Empty element. ");
      }
    }
  }
}
