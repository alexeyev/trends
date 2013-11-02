package ru.compscicenter.trends.source.cleaning;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO
 *
 * @author alexeyev
 */
public class TechcrunchExtractor extends ArticleExtractor {
    //todo: see techcrunch/sample

    private final Document doc;

    TechcrunchExtractor(String html) {
        doc = Jsoup.parse(html);
    }

    private final Pattern datePattern = Pattern.compile("(\\d+-\\d+-\\d+)");

    @Override
    public Date getDate() {
        //<meta name='sailthru.date' content='2007-12-07 18:28:47' />
        try {
            //todo: fix
            final String rawDate =
                    doc.select("meta").attr("name", "sailthru.date").get(0).attr("content");
            System.out.println(rawDate);
            final Matcher matcher = datePattern.matcher(rawDate);
            if (matcher.find()) {
                try {
                    return new SimpleDateFormat("yyyy-MM-dd").parse(matcher.group(1));
                } catch (ParseException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            return null;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }
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

    @Override
    public Set<String> getLinks() {
        return null;
    }
}
