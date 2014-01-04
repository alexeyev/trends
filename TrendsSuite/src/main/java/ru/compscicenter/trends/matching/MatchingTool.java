package ru.compscicenter.trends.matching;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * For experiments.
 *
 * @author alexeyev
 */
public class MatchingTool {
    private static Logger log = LoggerFactory.getLogger("matching");

    public static void main(String[] args) {
        try {
            LuceneMatcher matcher = new LuceneMatcher(new File("techcrunch.txt"));
            log.info(matcher.fuzzySearch("Shmoogle", 4).toString());
            log.info(matcher.magicSearch("mail.ru", 7f).toString());
            log.info(matcher.magicSearch("mail.ru group", 6f).toString());
        } catch (Exception e) {
            log.error("Oops", e);
        }
    }
}
