package ru.compscicenter.trends.source.cleaning;

import ru.compscicenter.trends.source.model.Article;

import java.util.Date;
import java.util.Set;

/**
 * @author alexeyev
 */
public class TechcrunchExtractor implements ArticleExtractor {

    TechcrunchExtractor(String html) {
        //todo
    }

    // todo: example in techcrunch/sample

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
        return null;
    }
}
