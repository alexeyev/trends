package ru.compscicenter.trends.source.model;

import java.util.Date;
import java.util.Set;

/**
 * @author alexeyev
 */
public class Article {
    private String text;
    private String title;
    private Date date;
    private Set<String> tags;

    public Article(final Date date, final String title, final String text, final Set<String> tags) {
        this.date = date;
        this.title = title;
        this.text = text;
        this.tags = tags;
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
}
