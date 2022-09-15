package fr.univ_lyon1.m1.info;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import java.lang.reflect.InvocationTargetException;

public class TestTestWithAnnotations {

    @Test
    public void test() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ClassToTest tc = new ClassToTest();
        TestRunnerWithAnn r = new TestRunnerWithAnn(tc);
        r.run();
        assertTrue(tc.ok);
        assertTrue(tc.ok2);
        assertEquals(1, tc.hasBeenCalledWith.get(0).intValue());
        assertEquals(2, tc.hasBeenCalledWith.get(1).intValue());
        assertEquals(33, tc.hasBeenCalledWith.get(2).intValue());
    }

}
