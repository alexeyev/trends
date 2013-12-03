package ru.compscicenter.trends.clustering;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Choosing the shortest string as the cluster representative name.
 *
 * @author alexeyev
 */
public class DumbStrategy implements RepresentativeNameChoosingStrategy {

    @Override
    public Map<Long, String> getRepresentativesMap(final Map<String, Long> clusteredNames) {

        Map<Long, Set<String>> clusters = new HashMap<Long, Set<String>>();

        for (Map.Entry<String, Long> e : clusteredNames.entrySet()) {
            if (!clusters.containsKey(e.getValue())) {
                clusters.put(
                        e.getValue(),
                        new TreeSet<String>(
                                // a treeset sorted by length
                                new Comparator<String>() {
                                    @Override
                                    public int compare(String o1, String o2) {
                                        return Integer.compare(o1.length(), o2.length());
                                    }
                                }));
            }
            clusters.get(e.getValue()).add(e.getKey());
        }

        log.debug(clusters.toString());
        log.debug(clusters.size() + "");

        Map<Long, String> representatives = new TreeMap<Long, String>();

        for (Map.Entry<Long, Set<String>> e : clusters.entrySet()) {
            String shortestName = e.getValue().iterator().next();
            representatives.put(e.getKey(), shortestName);
            log.info(" " + e.getKey() + " " + e.getValue().size() + " " + e.getValue().toString());
//            log.info(" " + e.getKey() + " " + shortestName);
        }
        return representatives;
    }

    private static Logger log = LoggerFactory.getLogger("dumb-strategy");

    public static void main(String[] args) throws IOException {
        //todo: remove
        FileWriter fw = new FileWriter("../id-to-representative.tsv");
        Map<Long, String> map = new DumbStrategy().getRepresentativesMap(new GizmodoOrgsMap().getMap());
        for (Map.Entry<Long, String> e : map.entrySet()) {
            fw.write(e.getKey() + "\t" + e.getValue() + "\n");
        }
        fw.close();
    }
}
