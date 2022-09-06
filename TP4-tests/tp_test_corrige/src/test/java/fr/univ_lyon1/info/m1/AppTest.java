package fr.univ_lyon1.info.m1;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.contains;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AppTest {
    private final CharManipulator manipulator = new CharManipulator();

    @Test
    public void testAbcd() {
        // Given
        final String original = "ABCD";

        // When
        final String invert = manipulator.invertOrder(original);

        // Then
        assertThat(invert, is("DCBA"));
    }

    @Test
    public void testMap() {
        // Given
        Map<String, Integer> map = new HashMap<>();

        // When
        map.put("Foo", 42);

        // Then
        assertThat(map, hasKey("Foo"));
        assertThat(map, not(hasKey("Bar")));
        assertThat(map, hasEntry("Foo", 42));
    }

    @Test
    public void testList() {
        // Given
        List<String> list = new ArrayList<>();

        // When
        list.add("Bar");

        // Then
        assertThat(list, contains("Bar"));
    }

}
