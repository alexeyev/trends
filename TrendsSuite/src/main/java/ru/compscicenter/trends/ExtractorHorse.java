package ru.compscicenter.trends;

import org.slf4j.Logger;
import ru.compscicenter.trends.ner.model.NamedEntity;
import ru.compscicenter.trends.util.CounterLogger;
import ru.compscicenter.trends.util.Pair;

import java.io.File;
import java.util.concurrent.BlockingQueue;

/**
 * Cross-dependencies, disgusting code, I know :)
 * @author alexeyev
 */
class ExtractorHorse implements Runnable {

    final BlockingQueue<File> queue;
    final BlockingQueue<Pair<NamedEntity,String>> destQueue;
    final CounterLogger ticker;
    final Logger log;

    public ExtractorHorse(final BlockingQueue<File> q,
                          final BlockingQueue<Pair<NamedEntity,String>> destQ,
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
                DirectoryNEProcessor.extractFromFile(target, destQueue);
            } catch (Exception e) {
                log.error("Exception!" + e.getMessage());
            }
        }
        log.info("Done.");
    }
}
