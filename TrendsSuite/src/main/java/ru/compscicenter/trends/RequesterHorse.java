package ru.compscicenter.trends;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.compscicenter.trends.ner.model.NamedEntity;
import ru.compscicenter.trends.util.CounterLogger;
import ru.compscicenter.trends.util.Pair;

import java.io.File;
import java.util.concurrent.BlockingQueue;

/**
 * @author alexeyev
 */
public class RequesterHorse implements Runnable {

    final private File file;
    final private Logger log = LoggerFactory.getLogger(this.getClass());

    public RequesterHorse(File file, Logger log) {
        this.file = file;
    }

    @Override
    public void run() {
        try {
            CrunchBaseProductsUpdater.updateStuff(file);
        } catch (Exception e) {
            log.error("Exception!" + e.getMessage());
        }
    }
}
