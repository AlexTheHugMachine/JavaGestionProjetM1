package fr.univ_lyon1.m1.info;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestRunnerWithAnn {
    Object objectUnderTest;

    public TestRunnerWithAnn(Object tc) {
        objectUnderTest = tc;
    }

    public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class<? extends Object> classUnderTest = objectUnderTest.getClass();
        System.out.println("testing " + classUnderTest.getName() + "...");
        for (Method method : classUnderTest.getMethods()) {
            processMethod(method);
        }
        System.out.println("testing " + classUnderTest.getName() + "... DONE");
    }

    private void processMethod(Method method) throws IllegalAccessException, InvocationTargetException {
        HomeMadeTest a = method.getAnnotation(HomeMadeTest.class);
        if (a != null) {
            HomeMadeArgs p = method.getAnnotation(HomeMadeArgs.class);
            if (p != null) {
                for (int arg : p.value()) {
                    System.out.println("  invoking " + method.getName() + " with arg " + arg);
                    method.invoke(objectUnderTest, arg);
                }
            } else {
                System.out.println("  invoking " + method.getName());
                method.invoke(objectUnderTest);
            }
        } else {
            System.out.println("  NOT invoking " + method.getName());
        }
    }

}
