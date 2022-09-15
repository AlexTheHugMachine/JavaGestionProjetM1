package fr.univ_lyon1.m1.info;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class TestPlainJUnit {
    @Test
    void test() {
        assertEquals(4, 2 + 2);
    }
    
    @Test
    void testExcept() {
        assertThrows(MyException.class, () -> {
            throw new MyException(); // test fails if removed
        });
    }
}
