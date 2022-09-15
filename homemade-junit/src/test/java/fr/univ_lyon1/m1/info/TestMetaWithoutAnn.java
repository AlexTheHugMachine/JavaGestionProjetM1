package fr.univ_lyon1.m1.info;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.Test;

public class TestMetaWithoutAnn {
    
    @Test
    public void test() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
	// Given
	ClassToTest tc = new ClassToTest();
	TestRunnerWithoutAnn runner = new TestRunnerWithoutAnn(tc);
	
	// When
	runner.run();
	
	// Then
	assertTrue(tc.ok);
	assertTrue(tc.ok2);
    }

}
