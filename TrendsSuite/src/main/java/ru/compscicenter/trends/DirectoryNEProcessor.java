package ru.compscicenter.trends;

import edu.stanford.nlp.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.compscicenter.trends.ner.EnglishNEExtractor;
import ru.compscicenter.trends.ner.model.NamedEntity;
import ru.compscicenter.trends.ner.model.Tag;
import ru.compscicenter.trends.util.CounterLogger;
import ru.compscicenter.trends.util.FilesCollector;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
            (final File textFile, final BlockingQueue<NamedEntity> destination) throws IOException {

        if (!textFile.isFile()) {
            throw new IllegalArgumentException();
        }
        final String text = IOUtils.slurpFile(textFile);
        final List<NamedEntity> nes = EnglishNEExtractor.getNamedEntities(text);
        for (final NamedEntity ne : nes) {
            if (ne.getTag().equals(Tag.ORGANIZATION)) {
                destination.add(ne);
            }
        }
    }

    /**
     * Well, i decided to do it in parallel.
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        final Date start = new Date();
        final File sourceDirectory =
                new File("/home/alexeyev/hp/workspace/new_gizmodo/corpus2/");
        final FileWriter destinationFile =
                new FileWriter("/home/alexeyev/hp/workspace/new_gizmodo/nes.txt");

        // all texts
        final BlockingQueue<File> queue = FilesCollector.getAllFiles(sourceDirectory);
        // all NEs
        final BlockingQueue<NamedEntity> entities = new LinkedBlockingDeque<NamedEntity>();

        log.info("Files obtained.");

        final int NUMBER_OF_THREADS = 3;
        final ExecutorService pool = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            pool.execute(new ExtractorHorse(queue, entities, tickLog, log));
        }

        log.info("Runners added.");
        int size = 0;

        final ArrayList<NamedEntity> buffer = new ArrayList<NamedEntity>();
        while (!pool.isTerminated()) {
            if (entities.size() > 5000) {
                log.info(TimeUnit.MILLISECONDS.toSeconds(new Date().getTime() - start.getTime()) + " secs");
                size += entities.drainTo(buffer);
                for (final NamedEntity ne : buffer) {
                    destinationFile.write(ne.getWords() + "\n");
                }
                buffer.clear();
                log.info("Flushed entities: " + size);
            }
        }

        size += entities.drainTo(buffer);
        for (final NamedEntity ne : buffer) {
            destinationFile.write(ne.getWords() + "\n");
        }
        buffer.clear();

        destinationFile.close();
        log.info("Done.");
    }
}