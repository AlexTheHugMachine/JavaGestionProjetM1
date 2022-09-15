package fr.univ_lyon1.m1.info;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestRunnerWithoutAnn {
    Object objectUnderTest;

    public TestRunnerWithoutAnn(Object tc) {
        objectUnderTest = tc;
    }

    public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class<? extends Object> classUnderTest = objectUnderTest.getClass();
        System.out.println("testing " + classUnderTest.getName() + "...");
        for (Method method : classUnderTest.getMethods()) {
            if (method.getName().startsWith("test") && method.getParameterCount() == 0) {
                System.out.println("  invoking " + method.getName());
                method.invoke(objectUnderTest);
            }
        }
        System.out.println("testing " + classUnderTest.getName() + "... DONE");
    }
}
