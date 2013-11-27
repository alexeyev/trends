package ru.compscicenter.trends;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.compscicenter.trends.clustering.GizmodoOrgsMap;
import ru.compscicenter.trends.clustering.OrgsMap;
import ru.compscicenter.trends.util.CounterLogger;

import java.io.File;
import java.util.*;
import java.util.concurrent.*;

/**
 * Copying articles from older corpus to organizations' folders.
 *
 * @author alexeyev
 */
public class SortingTool {

    private static Logger log = LoggerFactory.getLogger("local");
    private static CounterLogger clog = new CounterLogger(LoggerFactory.getLogger("counter"), 100, "%s articles processed");

    // yeahs, i know this should not be done,
    // i have very little time left
    static OrgsMap map = new GizmodoOrgsMap();
    static Set<Long> interestingCompanies = new HashSet<Long>();


    private static boolean ready(List<Future<?>> fs) {
        boolean ready = true;
        for (Future f : fs) {
            ready = ready && f.isDone();
        }
        return ready;
    }

    public static void main(String[] args) throws Exception {

        // whole pipeline here
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

        int threshold = 5;
        int counter = 0;
        for (Map.Entry<Long, Set<Integer>> entry : companyToYears.entrySet()) {
            if (entry.getValue().size() > threshold) {
                counter += 1;
                log.info(entry.toString());
                interestingCompanies.add(entry.getKey());
            }
        }
        log.info("Total: " + counter + " companies.");

        //-----------------splitting-by-folders---wish it was scala-----------

        File[] years = new File("../new_gizmodo/corpus3/").listFiles();

        // parallel stuff
        final int NUMBER_OF_THREADS = 3;
        final BlockingQueue<File> queue = new LinkedBlockingDeque<File>(Arrays.asList(years));
        final ExecutorService pool = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

        List<Future<?>> futures = new LinkedList<Future<?>>();
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            futures.add(pool.submit(new SorterExecutor(queue, clog)));
        }

        while (!ready(futures)) ;
        pool.shutdown();
        log.info("Done");
    }
}