package ru.compscicenter.trends.util;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Writes to a print-writer per @step executions.
 *
 * @author alexeyev
 */
public class CounterWriter {

    private long counter;
    private PrintStream out;
    private String pattern;
    private int step;

    /**
     * Please use %s if using interpolation in pattern.
     */
    public CounterWriter(PrintStream out, int step, String pattern) {
        counter = 0;
        this.out = out;
        this.pattern = pattern;
        this.step = step;
    }

    public void tick() {
        counter++;
        if (counter % step == 0) {
            out.println(String.format(pattern, counter));
        }
    }
}
