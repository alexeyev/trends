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
public class DirectoryProcessor {

    private static final Logger log = LoggerFactory.getLogger("script");
    private static final CounterLogger tickLog = new CounterLogger(log, 1000, "Processed: %s");

//    private static void updateMap(Map<Tag, TreeBag> map, NamedEntity ne) {
//        if (!map.containsKey(ne.getTag())) {
//            map.put(ne.getTag(), new TreeBag());
//        }
//        map.get(ne.getTag()).add(ne.getWords());
//    }
//
//    abstract private static class Filter<T> {
//        public abstract boolean check(T t);
//    }
//    private static void printMap(Map<Tag, TreeBag> map, FileWriter out, Filter<Tag> filter) throws IOException {
//        for (Tag tag : map.keySet()) {
//            if (filter.check(tag)) {
//                Bag bag = map.get(tag);
//                for (Object rawWord : bag.uniqueSet()) {
//                    String word = (String) rawWord;
//                    out.write(word + "; " + bag.getCount(rawWord) + "\n");
//                }
//            }
//        }
//        //todo: something
//        out.close();
//    }

    private static void extractFromFile(final File textFile, final BlockingQueue<NamedEntity> destination)
            throws IOException {

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

    private static class Extractor implements Runnable {

        final BlockingQueue<File> queue;
        final BlockingQueue<NamedEntity> destQueue;

        public Extractor(final BlockingQueue<File> q, final BlockingQueue<NamedEntity> destQ) {
            queue = q;
            destQueue = destQ;
        }

        @Override
        public void run() {
            while (!queue.isEmpty()) {
                try {
                    tickLog.tick();
                    final File target = queue.take();
                    extractFromFile(target, destQueue);
                } catch (Exception e) {
                    log.error("Exception!" + e.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {

        final Date start = new Date();

        final File sourceDirectory = new File("/home/alexeyev/hp/workspace/new_gizmodo/corpus2/");
        final FileWriter destinationFile = new FileWriter("/home/alexeyev/hp/workspace/new_gizmodo/nes.txt");

        // all texts
        final BlockingQueue<File> queue = FilesCollector.getAllFiles(sourceDirectory);
        // all NEs
        final BlockingQueue<NamedEntity> entities = new LinkedBlockingDeque<NamedEntity>();

        log.info("Files obtained.");

        final int NUMBER_OF_THREADS = 3;
        final ExecutorService pool = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            pool.execute(new Extractor(queue, entities));
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
//        final FileWriter fw = new FileWriter("/home/alexeyev/hp/workspace/new_gizmodo/all_nes.txt");
//        extractFromFile(sourceDirectory, fw);
//        fw.close();

//        if (sourceDirectory.isDirectory()) {
//            // listing all year-aware subdirs
//            final File[] dirs = sourceDirectory.listFiles();
//            if (dirs != null) {
//                for (final File dir : dirs) {
//
//                    log.println("Managing dir: " + dir.getName());
//
//                    final Map<Tag, TreeBag> bags = new HashMap<Tag, TreeBag>();
//
//                    if (dir.isDirectory()) {
//                        final File[] files = dir.listFiles();
//                        if (files != null) {
//                            for (final File file : files) {
//                                final String text = IOUtils.slurpFile(file);
//                                final List<NamedEntity> nes = EnglishNEExtractor.getNamedEntities(text);
//                                for (final NamedEntity ne : nes) {
//                                    updateMap(bags, ne);
//                                }
//                                tickLog.tick();
//                            }
//                        }
//                    }
//
//                    printMap(bags,
//                            new FileWriter("../aggregation/" + dir.getName() + ".txt"),
//                            new Filter<Tag>() {
//                                @Override
//                                public boolean check(Tag tag) {
//                                    return tag.equals(Tag.ORGANIZATION);
//                                }
//                            });
//                }
//            }
//        } else {
//            throw new IllegalArgumentException();
//        }
    }
}