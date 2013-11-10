package ru.compscicenter.trends.source.cleaning;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
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
        try {
            for (Element e : doc.select("meta")) {
                if (e.attr("name").equalsIgnoreCase("sailthru.date")) {
                    String rawDate = e.attr("content");
                    final Matcher matcher = datePattern.matcher(rawDate);
                    if (matcher.find()) {
                        try {
                            return new SimpleDateFormat("yyyy-MM-dd").parse(matcher.group(1));
                        } catch (ParseException ex) {
                            ex.printStackTrace();
                            return null;
                        }
                    }
                }
            }
            return null;
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public String getTitle() {
        try {
            for (Element e : doc.select("meta")) {
                if (e.attr("name").equalsIgnoreCase("sailthru.title")) {
                    String result = e.attr("content").replaceAll("\u00a0", " "); // replace &nbsp;
                    return result;
                }
            }
            return null;
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public String getText() {
        return doc.select("div.article-entry").get(0).text();
    }

    @Override
    public Set<String> getTags() {
        try {
            for (Element e : doc.select("meta")) {
                if (e.attr("name").equalsIgnoreCase("sailthru.tags")) {
                    String[] rawKeys = e.attr("content").replace(", ", ",").split(",");
                    return new HashSet<String>(Arrays.asList(rawKeys));
                }
            }
            return null;
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public Set<String> getLinks() {
        try {
            final HashSet<String> links = new HashSet<String>();
            for (Element e : doc.select("div.article-entry")) {
                for (Element a : e.select("a")) {
                    if (a.hasAttr("href")) {
                        links.add(a.attr("href"));
                    }
                }
            }
            return links;
        } catch (NullPointerException e) {
            return null;
        }
    }
}
