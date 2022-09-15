package fr.univ_lyon1.m1.info;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;

class TestExplicitList {

    @Test
    void test() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        // Given
        ClassToTest tc = new ClassToTest();
        TestRunnerExplicitList runner = new TestRunnerExplicitList(tc);
        runner.addTestMethod(tc::testMethod1);
        runner.addTestMethod(tc::testMethod2);

        // When
        runner.run();

        // Then
        assertTrue(tc.ok);
        assertTrue(tc.ok2);
    }

    @Test
    void testMethods() {
        ClassToTest tc = new ClassToTest();

        // Reference to an instance method
        // of a particular object
        Runnable m1 = tc::testMethod1;
        m1.run();

        assertTrue(tc.ok);

        // Reference to an instance method of an
        // arbitrary object of a particular type
        Consumer<ClassToTest> m2 = ClassToTest::testMethod2;
        m2.accept(tc);

        assertTrue(tc.ok2);

        // Reference to an instance method of an
        // arbitrary object with argument
        BiConsumer<ClassToTest, Integer> m3 = ClassToTest::testMethodWithArg;
        m3.accept(tc, 42);

        assertEquals(42, tc.hasBeenCalledWith.get(0).intValue());

    }

}
