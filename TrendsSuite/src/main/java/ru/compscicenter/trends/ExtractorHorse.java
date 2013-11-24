package ru.compscicenter.trends;

import org.slf4j.Logger;
import ru.compscicenter.trends.ner.model.NamedEntity;
import ru.compscicenter.trends.trends.TrendsPerDirectoryExtractor;
import ru.compscicenter.trends.util.CounterLogger;

import java.io.File;
import java.util.concurrent.BlockingQueue;

/**
 * Cross-dependencies, disgusting code, I know :)
 * @author alexeyev
 */
class ExtractorHorse implements Runnable {

    final BlockingQueue<File> queue;
    final BlockingQueue<NamedEntity> destQueue;
    final CounterLogger ticker;
    final Logger log;

    final static TrendsPerDirectoryExtractor extractor = new TrendsPerDirectoryExtractor();

    public ExtractorHorse(final BlockingQueue<File> q,
                          final BlockingQueue<NamedEntity> destQ,
                          CounterLogger tickLog, Logger log) {
        queue = q;
        destQueue = destQ;
        ticker = tickLog;
        this.log = log;
    }

    @Override
    public void run() {
        while (!queue.isEmpty()) {
            try {
                ticker.tick();
                final File target = queue.take();
                DirectoryProcessor.extractFromFile(target, destQueue);
            } catch (Exception e) {
                log.error("Exception!" + e.getMessage());
            }
        }
    }
}
