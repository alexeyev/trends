package ru.compscicenter.trends.trends;

import com.hp.hpl.taxonomy.TaxonomyExtractor;
import com.hp.hpl.taxonomy.TaxonomyExtractorBasic;
import com.hp.hpl.taxonomy.Term;
import com.hp.hpl.taxonomy.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author alexeyev
 */
public class TrendsPerDirectoryExtractor {

    private final static Logger log =
            LoggerFactory.getLogger(TrendsPerDirectoryExtractor.class);

    private final TaxonomyExtractor extractor;

    public TrendsPerDirectoryExtractor() {
        try {
            extractor = new TaxonomyExtractorBasic();
        } catch (IOException e) {
            log.error("Could not create Taxonomy Extractor. Dying.");
            throw new Error("Problems, officer.");
        }
    }

    public List<Topic> extract(final String text) {
        final ArrayList<Term> terms = extractor.getTerms(text);
        return extractor.extract(terms);
    }
}
