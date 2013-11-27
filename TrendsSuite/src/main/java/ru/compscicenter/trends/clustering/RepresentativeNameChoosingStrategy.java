package ru.compscicenter.trends.clustering;

import java.util.Map;

/**
 * @author alexeyev
 */
public interface RepresentativeNameChoosingStrategy {
    public Map<Long, String> getRepresentativesMap(Map<String, Long> clusteredNames);
}
