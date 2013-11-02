package ru.compscicenter.trends.source.model;

import java.util.Date;
import java.util.Set;

/**
 * @author alexeyev
 */
public class Article {
    //todo: think about factory
    private String text;
    private String title;
    private Date date;
    private Set<String> tags;
    private Set<String> relatedLinks;

    public Article(final Date date, final String title, final String text,
                   final Set<String> tags, final Set<String> relatedLinks) {
        this.date = date;
        this.title = title;
        this.text = text;
        this.tags = tags;
        this.relatedLinks = relatedLinks;
    }

    public Set<String> getTags() {
        return tags;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    public Set<String> getLinks() {
        return relatedLinks;
    }
}
