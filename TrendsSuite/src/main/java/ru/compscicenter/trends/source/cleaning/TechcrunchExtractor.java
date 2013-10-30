package ru.compscicenter.trends.source.cleaning;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Date;
import java.util.Set;

/**
 * @author alexeyev
 */
public class TechcrunchExtractor extends ArticleExtractor {
    //todo: see techcrunch/sample

    private final Document doc;

    TechcrunchExtractor(String html) {
        doc = Jsoup.parse(html);
    }

    @Override
    public Date getDate() {
        //todo
        //<meta name='sailthru.date' content='2007-12-07 18:28:47' />
        return null;
    }

    @Override
    public String getTitle() {
        //todo
        //<title>Ask and Wii shall receive: Guitar Hero 3 in Stereo  |  TechCrunch</title>
        return null;
    }

    @Override
    public String getText() {
        return doc.select("div.article-entry").get(0).text();
    }

    @Override
    public Set<String> getTags() {
        //<meta name='sailthru.tags' content='Activision, Nintendo, Wii, Guitar Hero, CrunchArcade, Headline' />
        return null;
    }
}
