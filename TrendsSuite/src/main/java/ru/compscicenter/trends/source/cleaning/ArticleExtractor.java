package ru.compscicenter.trends.source.cleaning;

import java.util.Date;
import java.util.Set;

/**
 * @author alexeyev
 */
public interface ArticleExtractor {

    public ru.compscicenter.trends.source.model.Article getArticle();

    Date getDate();

    String getTitle();

    String getText();

    Set<String> getTags();
}
