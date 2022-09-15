package fr.univ_lyon1.m1.info;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

class ClassToTest {
    boolean ok = false;
    boolean ok2 = false;
    ArrayList<Integer> hasBeenCalledWith = new ArrayList<Integer>();

    public void notATestCase() {
        fail("Should not be called because doesn't have @Test annotation");
    }

    @HomeMadeTest
    public void testMethod1() {
        ok = true;
    }

    @HomeMadeTest
    public void testMethod2() {
        ok2 = true;
    }

    @HomeMadeTest
    @HomeMadeArgs({ 1, 2, 33 })
    public void testMethodWithArg(final int x) {
        hasBeenCalledWith.add(x);
    }
}
