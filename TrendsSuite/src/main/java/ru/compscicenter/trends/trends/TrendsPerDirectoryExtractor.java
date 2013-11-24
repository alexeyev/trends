package ru.compscicenter.trends.trends;

import com.hp.hpl.taxonomy.TaxonomyExtractor;
import com.hp.hpl.taxonomy.TaxonomyExtractorBasic;
import com.hp.hpl.taxonomy.Term;
import com.hp.hpl.taxonomy.Topic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author alexeyev
 */
public class TrendsPerDirectoryExtractor {

    private final TaxonomyExtractor extractor;

    public TrendsPerDirectoryExtractor() throws IOException {
        extractor = new TaxonomyExtractorBasic();
    }

    public List<Topic> extract(final String text) {
        final ArrayList<Term> terms = extractor.getTerms(text);
        return extractor.extract(terms);
    }
}
