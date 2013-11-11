package ru.compscicenter.trends.source.cleaning;

import ru.compscicenter.trends.source.Source;

/**
 * @author alexeyev
 */
abstract public class ArticleExtractorFactory {
    public static ArticleExtractor newExtractor(Source type, String data) {
        if (type.equals(Source.GIZMODO))
            return new GizmodoExtractor(data);
        if (type.equals(Source.TECHCRUNCH))
            return new TechcrunchExtractor(data);
        if (type.equals(Source.IEEE))
            return new IeeeExtractor(data);
        return null;
    }
}
