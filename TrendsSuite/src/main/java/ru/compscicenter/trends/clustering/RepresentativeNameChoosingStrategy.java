package ru.compscicenter.trends.clustering;

import java.util.Map;

/**
 * For each cluster having a unique ID we must
 * select some string to be suggested as a company's name.
 * @author alexeyev
 */
public interface RepresentativeNameChoosingStrategy {
    /**
     * @param clusteredNames name-to-cluster-id map
     * @return cluster-id-to-representative-name map
     */
    public Map<Long, String> getRepresentativesMap(Map<String, Long> clusteredNames);
}
