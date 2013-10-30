package ru.compscicenter.trends.source.cleaning;

/**
 * @author alexeyev
 */
abstract public class ArticleExtractorFactory {
    public static ArticleExtractor newExtractor(String type, String data) {
        if (type.equals("gizmodo"))
            return new GizmodoExtractor(data);
        if (type.equals("techcrunch"))
            return new TechcrunchExtractor(data);
        return null;
    }
}
