package ru.compscicenter.trends.source.cleaning;

import ru.compscicenter.trends.source.model.Article;

import java.util.Date;
import java.util.Set;

/**
 * @author alexeyev
 */
public abstract class ArticleExtractor {

    public Article getArticle() {
        return new Article(getDate(), getTitle(), getText(), getTags(), getLinks());
    }

    abstract public Date getDate();

    abstract public String getTitle();

    abstract public String getText();

    abstract public Set<String> getTags();

    abstract public Set<String> getLinks();
}
