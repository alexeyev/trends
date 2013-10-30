package ru.compscicenter.trends.ner.model;

import ru.compscicenter.trends.util.Pair;

/**
 * @author alexeyev
 */
public class Conversions {

    /**
     * if impossible to convert, returns null
     */
    public static NamedEntity pairToNamedEntity(Pair<String, String> pair) {
        for (Tag tag: Tag.values()) {
            if (tag.name().equals(pair.first())) {
                return new NamedEntity(tag, pair.second());
            }
        }
        return null;
    }
}
