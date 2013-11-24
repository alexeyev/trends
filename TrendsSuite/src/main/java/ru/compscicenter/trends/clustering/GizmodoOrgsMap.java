package ru.compscicenter.trends.clustering;

import java.io.*;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author alexeyev
 */
public class GizmodoOrgsMap {
    /*
     * Suggested text files
     * contained map of 1928295 lines.
     *
     * After cat * | sort | uniq > sort_uniq.txt
     * cat sort_uniq.txt | wc -l
     * 55757
     *
     * This fits into memory, yeeeha!
     */

    private final String pathToMap = "gizmodo/sort_uniq.txt";
    private final Map orgs;
    private final Pattern mapPattern = Pattern.compile("(\\d+),\"(.*)\"");

    private String clean(final String in) {
        //todo:
        return null;//in.replaceAll()
    }

    public GizmodoOrgsMap() throws IOException {
        //todo: decomposition
        final File mapFile = new File(pathToMap);
        if (!mapFile.exists())
            throw new FileNotFoundException(pathToMap);
        final BufferedReader br = new BufferedReader(new FileReader(mapFile));
        String line = null;

        HashMap<String, Long> collectingMap = new HashMap<String, Long>();

        // one can't just split by comma
        while (br.ready()) {
            line = br.readLine();
            final Matcher matcher = mapPattern.matcher(clean(line));
            if (matcher.find()) {
                collectingMap.put(matcher.group(2), Long.parseLong(matcher.group(1)));
            } else {
                throw new IOException("Line did not match pattern: [" + line + "]");
            }
        }
        orgs = collectingMap;
    }

    public final Map<String, Long> getMap() {
        return orgs;
    }

}
