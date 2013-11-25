package ru.compscicenter.trends.clustering;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author alexeyev
 */
public class GizmodoOrgsMap extends OrgsMap {
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
    private Map<String, ArrayList<Long>> orgs;
    private final Pattern mapPattern = Pattern.compile("(\\d+),\"(.*)\"");
    private final static Logger log = LoggerFactory.getLogger(GizmodoOrgsMap.class);

    private String clean(final String in) {
        //todo:
        return in.replaceAll("\u2028", " ");
    }

    public GizmodoOrgsMap() {
        //todo: decomposition
        try {
            final File mapFile = new File(pathToMap);
            if (!mapFile.exists())
                throw new FileNotFoundException(pathToMap);

            final BufferedReader br = new BufferedReader(new FileReader(mapFile));
            String line = null;
            HashMap<String, ArrayList<Long>> collectingMap =
                    new HashMap<String, ArrayList<Long>>();

            // one can't just split by comma
            while (br.ready()) {

                line = br.readLine();
                final Matcher matcher = mapPattern.matcher(clean(line));

                //todo: put to private method
                if (matcher.find()) {
                    String name = matcher.group(2);
                    Long id = Long.parseLong(matcher.group(1));

                    if (collectingMap.containsKey(name)) {
                        collectingMap.get(name).add(id);
                    } else {
                        ArrayList<Long> tempList = new ArrayList<Long>();
                        tempList.add(id);
                        collectingMap.put(name, tempList);
                    }
                } else {
                    throw new IOException("Line did not match pattern: [" + line + "]");
                }
            }
            orgs = collectingMap;
            br.close();
        } catch (IOException e) {
            log.error("Bumz", e);
            orgs = null;
        }
    }

    public final Map<String, ArrayList<Long>> getMap() {
        return orgs;
    }
}
