package fr.univ_lyon1.info.m1.mes.utils;

import java.util.Random;

public interface RandomNumberGenerator {
  static Integer generaRandomInteger(int lowerbound, int upperbound) throws IllegalArgumentException {
    Random rand = new Random(); // instance of random class
    int lower = lowerbound < upperbound && lowerbound > 0 ? lowerbound : 0;
    int upper = upperbound > lower && upperbound > 0 ? upperbound : -1;
    if (upper == -1)
      throw new IllegalArgumentException();
    int random_integer = rand.nextInt(upperbound - lowerbound) + lowerbound;
    return random_integer;
  }
}
