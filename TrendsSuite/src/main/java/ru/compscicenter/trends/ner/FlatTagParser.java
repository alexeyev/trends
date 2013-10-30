package ru.compscicenter.trends.ner;

import ru.compscicenter.trends.util.Pair;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parsing xml markup believing tags are not nested
 * (which is natural).
 *
 * @author alexeyev
 */
public class FlatTagParser {

    private final static Pattern pattern = Pattern.compile("<(\\w+)>([^<]+)</\\w+>");

    public static List<Pair<String, String>> parseAll(final String text) {
        final Matcher matcher = pattern.matcher(text);
        final LinkedList<Pair<String, String>> results = new LinkedList<Pair<String, String>>();

        while (matcher.find()) {
            results.add(new Pair(matcher.group(1), matcher.group(2)));
        }
        return results;
    }
}
