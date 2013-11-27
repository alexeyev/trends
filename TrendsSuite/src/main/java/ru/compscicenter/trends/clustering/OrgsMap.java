package ru.compscicenter.trends.clustering;

import java.util.Map;

/**
 * Stuff that reads clustering map for
 * either Gizmodo or TechCrunch:
 * <p/>
 * Name -> List (id0, id1, ...)
 *
 * @author alexeyev
 */
public abstract class OrgsMap {
    public abstract Map<String, Long> getMap();
}
