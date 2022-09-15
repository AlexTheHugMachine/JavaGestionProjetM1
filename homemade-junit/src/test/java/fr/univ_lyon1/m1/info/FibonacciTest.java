package fr.univ_lyon1.m1.info;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class Fibo {
    static int fibo(int n) {
        if (n < 2) {
            return 1;
        } else {
            return fibo(n - 1) + fibo(n - 2);
        }
    }
}
public class FibonacciTest {
    @ParameterizedTest
    @ValueSource(ints = { 1, 3, 5, 15, Integer.MAX_VALUE }) // six numbers
    void isOdd_ShouldReturnTrueForOddNumbers(int number) {
        assertEquals(1, number % 2);
    }

    @ParameterizedTest
    @CsvSource({"1,1", "2,2", "3,3", "4,5", "5,8"})
    void testFibo(String n, String expected_fibo_n) {
        assertEquals(Integer.parseInt(expected_fibo_n),
                     Fibo.fibo(Integer.parseInt(n)));
    }
}