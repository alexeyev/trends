package ru.compscicenter.trends.util;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author alexeyev
 */
public class FilesCollector {

    /**
     * Recursive all-files getter.
     */
    private static BlockingQueue<File> getAllFiles(final File srcDir, final BlockingQueue<File> queue) {
        final BlockingQueue<File> neueQueue = new LinkedBlockingQueue<File>(queue);
        for (final File file : srcDir.listFiles()) {
            if (file.isFile()) {
                neueQueue.add(file);
            } else if (file.isDirectory()) {
                neueQueue.addAll(getAllFiles(file, new LinkedBlockingQueue<File>()));
            }
        }
        return neueQueue;
    }

    public static BlockingQueue<File> getAllFiles(final File srcDir) {
        if (!srcDir.isDirectory()) {
            throw new IllegalArgumentException();
        }
        final BlockingQueue<File> queue = new LinkedBlockingQueue<File>();
        return getAllFiles(srcDir, queue);
    }
}
