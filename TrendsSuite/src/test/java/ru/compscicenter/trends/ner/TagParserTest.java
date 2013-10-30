package ru.compscicenter.trends.ner;

import org.junit.Test;
import ru.compscicenter.trends.ner.EnglishNEExtractor;
import ru.compscicenter.trends.ner.FlatTagParser;
import ru.compscicenter.trends.ner.model.NamedEntity;
import ru.compscicenter.trends.ner.model.Tag;

/**
 * @author alexeyev
 */
public class TagParserTest {

    private String tryText = "Yesterday at 3 pm <OR>Macrosoft</OR> <verb>bought</verb> <SH>Svype</SH>!";

    @Test
    public void testParser() throws Exception {
        assert (FlatTagParser.parseAll(tryText).size() == 3);
    }

}
