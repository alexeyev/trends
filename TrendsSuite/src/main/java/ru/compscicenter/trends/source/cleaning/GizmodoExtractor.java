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
public class GizmodoExtractor extends ArticleExtractor {

    private final Document doc;

    GizmodoExtractor(final String html) {
        doc = Jsoup.parse(html);
    }

    private static Pattern datePattern = Pattern.compile("(\\d+/\\d+/\\d+)");

    @Override
    public Date getDate() {
            final String rawDate =
                    doc.select(".post-container").select(".publish-time").select(".show-on-hover").text();
            final Matcher matcher = datePattern.matcher(rawDate);
            if (matcher.find()) {
                try {
                    return new SimpleDateFormat("MM/dd/yy").parse(matcher.group(1));
                } catch (ParseException e) {
                    return null;
                }
            }
            return null;
    }

    @Override
    public String getTitle() {
        try {
            return doc.select("title").get(0).text();
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * TODO: check on dataset
     *
     * @return
     */
    @Override
    public String getText() {
        try {
            return doc.select(".post-content").get(0).text().replaceAll("\n", " ").
                    // removing references
                            replaceAll("\\[[\\w\\s\\.\\-\\,;\\?\\!\\:]+\\]", "");
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public Set<String> getTags() {
        try {
            for (Element e : doc.select("meta")) {
                if (e.attr("name").equalsIgnoreCase("keywords")) {
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
            for (Element e : doc.select(".post-content")) {
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
