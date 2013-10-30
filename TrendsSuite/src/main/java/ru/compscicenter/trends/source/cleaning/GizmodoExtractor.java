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
public class GizmodoExtractor implements ArticleExtractor {

    private final Document doc;

    public GizmodoExtractor(final String html) {
        //todo: consider using InputStream
        doc = Jsoup.parse(html);
        //todo: rewrite to serialized xpath-like notation\
    }

    @Override
    public Article getArticle() {
        return null;
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
        /*
        "<meta content=\"" +
                "mav on a wire, " +
                "mav, micro air vehicle, " +
                "power lines, Spy, spy plane, telephone lines, Gizmodo\" name=\"keywords\">"
                */
        for (Element e : doc.select("meta")) {
            if (e.attr("name").equalsIgnoreCase("keywords")) {
                String[] rawKeys = e.attr("content").replace(", ", ",").split(",");
                return new HashSet<String>(Arrays.asList(rawKeys));
            }
        }
        return null;
    }
}
