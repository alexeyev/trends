package ru.compscicenter.trends.ner;

import org.junit.Test;
import ru.compscicenter.trends.ner.EnglishNEExtractor;
import ru.compscicenter.trends.ner.model.NamedEntity;
import ru.compscicenter.trends.ner.model.Tag;

import java.util.List;

/**
 * @author alexeyev
 */
public class CoreNLPTest {

    private final String tryText = "Humpty dumpty sat on the wall " +
            "humpty dumpty bought Skype and Nokia and then had a great fall";

    @Test
    public void testAdd() {
        String out = EnglishNEExtractor.toAnnotatedString(tryText);
    }

    @Test
    public void testExtractor() {
        List result = EnglishNEExtractor.getNamedEntities(tryText);
        assert (result.size() == 2);
        assert (result.get(0).equals(new NamedEntity(Tag.ORGANIZATION, "Skype")) &&
                result.get(1).equals(new NamedEntity(Tag.ORGANIZATION, "Nokia")));
    }
}
