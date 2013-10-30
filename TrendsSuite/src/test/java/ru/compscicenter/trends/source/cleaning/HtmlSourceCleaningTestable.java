package ru.compscicenter.trends.source.cleaning;

import java.io.IOException;

/**
 * @author alexeyev
 */
interface HtmlSourceCleaningTestable {

    abstract public void testDate() throws IOException;

    abstract public void testTitle() throws IOException;

    abstract public void testTags() throws IOException;

    abstract public void testText() throws IOException;
}
