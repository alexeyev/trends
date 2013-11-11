package ru.compscicenter.trends.source.cleaning;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Date;
import java.util.Set;

/**
 * @author alexeyev
 */
public class IeeeExtractor extends ArticleExtractor {


    private final Document doc;

    IeeeExtractor(final String html) {
        doc = Jsoup.parse(html);
    }

    @Override
    public Date getDate() {
        return null;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public String getText() {
        return null;
    }

    @Override
    public Set<String> getTags() {
        return null;
    }

    @Override
    public Set<String> getLinks() {
        return null;
    }
}
