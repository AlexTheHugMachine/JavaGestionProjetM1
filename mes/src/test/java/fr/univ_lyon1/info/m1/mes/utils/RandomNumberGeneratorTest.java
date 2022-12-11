package fr.univ_lyon1.info.m1.mes.utils;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.allOf;

import org.junit.jupiter.api.Test;

// Easter egg : https://i.stack.imgur.com/VjJgg.jpg
public class RandomNumberGeneratorTest {
  @Test
  void generateRandomIntegerThrowExceptionWhenUpperIsNegative() {
    int badUpper = -3;
    int lower = 1;

    assertThrows(IllegalArgumentException.class,
        () -> RandomNumberGenerator.generaRandomInteger(lower, badUpper));
  }

  @Test
  void generaRandomIntegerThrowExceptionWhenUpperAndLowerEquals() {
    int badUpper = 1;
    int badLower = 1;

    assertThrows(IllegalArgumentException.class,
        () -> RandomNumberGenerator.generaRandomInteger(badLower, badUpper));
  }

  @Test
  void generaRandomIntegerThrowExceptionWhenUpperIsLowerThanLowerbound() {
    int badUpper = 1;
    int lower = 3;

    assertThrows(IllegalArgumentException.class,
        () -> RandomNumberGenerator.generaRandomInteger(lower, badUpper));
  }

  @Test
  void generaRandomIntegerThatFitInTheRangerOfUpperAndLower() {
    int lower = 0;
    int upper = 10;

    assertThat(RandomNumberGenerator.generaRandomInteger(lower, upper),
        allOf(greaterThanOrEqualTo(lower), lessThanOrEqualTo(upper)));

  }
}
