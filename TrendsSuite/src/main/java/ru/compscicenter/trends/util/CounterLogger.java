package ru.compscicenter.trends.util;

import org.slf4j.Logger;

/**
 * @author alexeyev
 */
public class CounterLogger {

    private volatile long counter = 0;
    private final Logger log;
    private final int step;
    private final String pattern;
    private volatile long prevCounter = 0;

    public CounterLogger(final Logger log, final int step, final String pattern) {
        if (step < 1)
            throw new IllegalArgumentException("Step must be a positive integer.");
        this.log = log;
        this.step = step;
        this.pattern = pattern;
    }

    public void tick() {
        counter++;
        if (counter % step == 0) {
            log.info(String.format(pattern, counter));
            prevCounter = counter;
        }
    }

    public void tick(int up) {
        counter += up;
        if (counter - prevCounter >= step) {
            log.info(String.format(pattern, counter));
            prevCounter = counter;
        }
    }
}
