package ru.compscicenter.trends;

import edu.stanford.nlp.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.compscicenter.trends.ner.EnglishNEExtractor;
import ru.compscicenter.trends.ner.model.NamedEntity;
import ru.compscicenter.trends.ner.model.Tag;
import ru.compscicenter.trends.util.CounterLogger;
import ru.compscicenter.trends.util.FilesCollector;
import ru.compscicenter.trends.util.Pair;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Style is horrible, logic is mixed.
 * Yet this is a script, so I don't care too much.
 */
public class DirectoryNEProcessor {

    private static final Logger log = LoggerFactory.getLogger("script");
    private static final CounterLogger tickLog = new CounterLogger(log, 1000, "Processed: %s");

    static void extractFromFile
            (final File textFile, final BlockingQueue<Pair<NamedEntity, String>> destination)
            throws IOException {

        if (!textFile.isFile()) {
            throw new IllegalArgumentException();
        }
        final String text = IOUtils.slurpFile(textFile);
        final String year = textFile.getParentFile().getName();
        final List<NamedEntity> nes = EnglishNEExtractor.getNamedEntities(text);
        for (final NamedEntity ne : nes) {
            if (ne.getTag().equals(Tag.ORGANIZATION)) {
                destination.add(new Pair<NamedEntity, String>(ne, year));
            }
        }
    }

    private static boolean ready(List<Future<?>> fs) {
        boolean ready = true;
        for (Future f : fs) {
            ready = ready && f.isDone();
        }
        return ready;
    }

    /**
     * Well, i decided to do it in parallel.
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        final Date start = new Date();
        final File sourceDirectory =
                new File("/home/alexeyev/hp/workspace/new_gizmodo/corpus3/");
        final FileWriter destinationFile =
                new FileWriter("/home/alexeyev/hp/workspace/new_gizmodo/date_aware_nes_2.txt");

        // all texts
        final BlockingQueue<File> queue = FilesCollector.getAllFiles(sourceDirectory);
        // all NEs
        final BlockingQueue<Pair<NamedEntity, String>> entities =
                new LinkedBlockingDeque<Pair<NamedEntity, String>>();

        log.info("Files obtained.");

        final int NUMBER_OF_THREADS = 3;
        final ExecutorService pool = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

        List<Future<?>> futures = new LinkedList<>();

        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            futures.add(pool.submit(new ExtractorHorse(queue, entities, tickLog, log)));
        }

        int size = 0;
        final ArrayList<Pair<NamedEntity, String>> buffer = new ArrayList<Pair<NamedEntity, String>>();
        while (!ready(futures)) {
            if (entities.size() > 5000) {
                log.info(TimeUnit.MILLISECONDS.toSeconds(new Date().getTime() - start.getTime()) + " secs");
                size += entities.drainTo(buffer);
                for (final Pair<NamedEntity, String> pair : buffer) {
                    destinationFile.write(pair.second() + "\t" + pair.first().getWords() + "\n");
                }
                log.info("Flushed entities: " + size);
                buffer.clear();
            }
        }

        size += entities.drainTo(buffer);
        for (final Pair<NamedEntity, String> pair : buffer) {
            destinationFile.write(pair.second() + "\t" + pair.first().getWords() + "\n");
        }

        log.info("Flushed entities: " + size);
        buffer.clear();

        destinationFile.close();
        log.info("Done.");
        pool.shutdown();
    }
}