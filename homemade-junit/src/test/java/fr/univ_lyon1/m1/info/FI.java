package fr.univ_lyon1.m1.info;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

@FunctionalInterface
interface IntToInt {
    abstract int run(int x);
}

class C {
    static int increment(int x) {
        return x + 1;
    }
}

public class FI {

    @Test
    public void test() {
        IntToInt fi = x -> x + 1;
        assertEquals(43, fi.run(42));
        fi = C::increment;

    }

}
