package ru.compscicenter.trends.clustering;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author alexeyev
 */
public class GizmodoReadingTest {

    private static Logger log = LoggerFactory.getLogger(GizmodoReadingTest.class);

    @Test
    public void test() throws IOException {
        final Map<String, Long> map = new GizmodoOrgsMap().getMap();

        log.info("Strings: " + map.size());

        int count = map.entrySet().size();

        log.info("Longs: " + count);

        final BufferedReader reader =
                new BufferedReader(new FileReader("gizmodo/sort_uniq.txt"));
        int lines = 0;
        while (reader.readLine() != null) lines++;
        reader.close();

        assert(lines == count);
    }

}
