package fr.univ_lyon1.m1.info;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class TestRunnerExplicitList {
    private Object objectUnderTest;
    private ArrayList<Runnable> methodsToTest = new ArrayList<Runnable>();

    public TestRunnerExplicitList(Object tc) {
        objectUnderTest = tc;
    }

    public void addTestMethod(Runnable m) {
        methodsToTest.add(m);
    }

    public void run() throws IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        String name = objectUnderTest.getClass().getName();
        System.out.println("Testing class " + name + "...");
        for (Runnable m : methodsToTest) {
            System.out.println("  testing one method");
            m.run();
        }
        System.out.println("Testing class " + name + ": DONE");
    }
}
