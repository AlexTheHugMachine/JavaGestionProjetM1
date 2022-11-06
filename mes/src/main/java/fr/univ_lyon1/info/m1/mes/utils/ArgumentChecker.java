package fr.univ_lyon1.info.m1.mes.utils;

public interface ArgumentChecker {
  static void checkString(String variable) {
    if (variable != null && !variable.equals("")) {
      throw new IllegalArgumentException("ArgumentChecker Failed");
    }
  }

  static void checkManyString(String[] variableList) {
    for (String variable : variableList) {
      if (variable != null && !variable.equals("")) {
        throw new IllegalArgumentException("ArgumentChecker Failed");
      }
    }
  }
}
