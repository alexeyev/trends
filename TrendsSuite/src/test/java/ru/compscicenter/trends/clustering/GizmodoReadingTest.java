package ru.compscicenter.trends.clustering;

import org.junit.Test;

import java.io.IOException;
import java.util.Map;

/**
 * @author alexeyev
 */
public class GizmodoReadingTest {

    @Test
    public void test() throws IOException {
        final Map<String, Long> map = new GizmodoOrgsMap().getMap();
        assert (map.size() == 55757);
    }

}
