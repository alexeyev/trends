package ru.compscicenter.trends;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.compscicenter.trends.clustering.GizmodoOrgsMap;
import ru.compscicenter.trends.clustering.OrgsMap;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author alexeyev
 */
public class NEDateTool {

    private static Logger log = LoggerFactory.getLogger("local");

    public static void main(String[] args) throws IOException {

        Iterator<String> lines =
                FileUtils.lineIterator(new File("../new_gizmodo/date_aware_nes_2.txt"));
        OrgsMap map = new GizmodoOrgsMap();

        Set<String> set = new HashSet<String>();

        while (lines.hasNext()) {
            String line = lines.next();
            if (map.getMap().get(line.split("\t")[1]) == null)
                set.add(line.split("\t")[1]);
        }

        log.info("count: " + set.size());
        for (String el : set) {
            log.info(el);
        }
    }
}
