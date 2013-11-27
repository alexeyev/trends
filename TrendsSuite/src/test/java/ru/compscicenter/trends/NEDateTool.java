package ru.compscicenter.trends;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.compscicenter.trends.clustering.GizmodoOrgsMap;
import ru.compscicenter.trends.clustering.OrgsMap;
import ru.compscicenter.trends.ner.EnglishNEExtractor;
import ru.compscicenter.trends.ner.model.NamedEntity;
import ru.compscicenter.trends.ner.model.Tag;
import ru.compscicenter.trends.util.CounterLogger;

import java.io.File;
import java.util.*;

/**
 * Раскладка по значимым организациям.
 *
 * @author alexeyev
 */
public class NEDateTool {

    private static Logger log = LoggerFactory.getLogger("local");
    private static CounterLogger clog = new CounterLogger(LoggerFactory.getLogger("counter"), 100, "%s articles processed");

    public static void main(String[] args) throws Exception {

        // whole pipeline here

        OrgsMap map = new GizmodoOrgsMap();
        LineIterator lines = FileUtils.lineIterator(new File("../new_gizmodo/dan_2.txt"));
        HashMap<Long, Set<Integer>> companyToYears = new HashMap<Long, Set<Integer>>();

        // getting map companyid -> years

        while (lines.hasNext()) {
            String line = (String) lines.next();
            Long id = map.getMap().get(line.split("\t")[1]);
            if (id == null) {
                log.error("No id for company " + line.split("\t")[1]);
            } else {
                if (!companyToYears.containsKey(id)) {
                    companyToYears.put(id, new HashSet<Integer>());
                }
                companyToYears.get(id).add(Integer.parseInt(line.split("\t")[0]));
                companyToYears.get(id).add(Integer.parseInt(line.split("\t")[0]));
            }
        }

        List<Long> interestingCompanies = new LinkedList<Long>();

        int threshold = 5;
        int counter = 0;
        for (Map.Entry<Long, Set<Integer>> entry : companyToYears.entrySet()) {
            if (entry.getValue().size() > threshold) {
                counter += 1;
                log.info(entry.toString());
            }
        }
        log.info("Total: " + counter + " companies.");

        //-----------------splitting-by-folders---wish it was scala-----------

        File[] years = new File("../new_gizmodo/corpus3/").listFiles();
        File destDirectory = new File("../new_gizmodo/corpus4/");
        destDirectory.mkdirs();

        // yearly folders
        for (File year : years) {
            log.info(year.getAbsolutePath());
            // articles
            for (File article : year.listFiles()) {
                String text = FileUtils.readFileToString(article);
                // NER
                List<NamedEntity> nes = EnglishNEExtractor.getNamedEntities(text);
                for (NamedEntity ne : nes) {
                    // filtering organizations
                    if (ne.getTag().equals(Tag.ORGANIZATION)) {
                        Long orgId = map.getMap().get(ne.getWords());
                        // only known organizations
                        if (orgId == null) {
                            //do nothing
                            // only companies that are mentioned often
                        } else if (interestingCompanies.contains(orgId)) {
                            File destYearDir = new File(
                                    String.format(
                                            "%s/%s/%s/",
                                            destDirectory.getAbsolutePath(),
                                            orgId.toString(),
                                            year.getName()));
                            destYearDir.mkdirs();
                            FileUtils.copyFileToDirectory(article, destYearDir);
                        }
                    }
                }
                clog.tick();
            }
        }
    }
}