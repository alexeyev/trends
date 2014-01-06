package ru.compscicenter.trends.matching;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Collection;
import java.util.List;

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
            List<String> lines = FileUtils.readLines(new File("test_companies.tsv"));
            for (String line : lines) {
                String query = line.replaceAll("[/\"\\)\\(\\*\\?\\]\\[\\s\\+]+", " ").replaceFirst("NOT", "");
                log.warn(query);
                Collection<String> results = matcher.magicSearch(query, 3f);
                log.info(results.toString());
                if (results.isEmpty()) {
                    String shortQuery = query.split("\\s")[0];
                    if (!shortQuery.equals("")) {
                        results = matcher.magicSearch(shortQuery, 3f);
                        log.info("==> " + results.toString());
                    }
                }
            }
        } catch (Exception e) {
            log.error("Oops", e);
        }
    }
}