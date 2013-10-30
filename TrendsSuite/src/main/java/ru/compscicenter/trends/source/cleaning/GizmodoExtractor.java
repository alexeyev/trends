package ru.compscicenter.trends.source.cleaning;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import ru.compscicenter.trends.source.model.Article;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * TODO
 *
 * @author alexeyev
 */
public class GizmodoExtractor extends ArticleExtractor {

    private final Document doc;

    GizmodoExtractor(final String html) {
        doc = Jsoup.parse(html);
    }

    @Override
    public Date getDate() {
        return null;
    }

    @Override
    public String getTitle() {
        return doc.select("title").get(0).text();
    }

    @Override
    public String getText() {
        return null;
    }

    @Override
    public Set<String> getTags() {
        for (Element e : doc.select("meta")) {
            if (e.attr("name").equalsIgnoreCase("keywords")) {
                String[] rawKeys = e.attr("content").replace(", ", ",").split(",");
                return new HashSet<String>(Arrays.asList(rawKeys));
            }
        }
        return null;
    }
}
