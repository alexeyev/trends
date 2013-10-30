package ru.compscicenter.trends.source.cleaning;

import ru.compscicenter.trends.source.model.Article;

import java.util.Date;
import java.util.Set;

/**
 * @author alexeyev
 */
public abstract class ArticleExtractor {

    public Article getArticle() {
        return new Article(getDate(), getTitle(), getText(), getTags());
    }

    abstract Date getDate();

    abstract String getTitle();

    abstract String getText();

    abstract Set<String> getTags();
}
