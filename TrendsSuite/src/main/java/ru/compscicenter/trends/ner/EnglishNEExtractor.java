package ru.compscicenter.trends.ner;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import ru.compscicenter.trends.ner.model.Conversions;
import ru.compscicenter.trends.ner.model.NamedEntity;
import ru.compscicenter.trends.util.Pair;

import java.util.LinkedList;
import java.util.List;

/**
 * @author alexeyev
 */
public class EnglishNEExtractor {

    private final static String classifiersPath = "classifiers/english.all.3class.distsim.crf.ser.gz";
    private final static AbstractSequenceClassifier<CoreLabel> classifier =
            CRFClassifier.getClassifierNoExceptions(classifiersPath);

    /**
     * @param text raw text
     * @return text with xml-ne-annoting tags
     */
    public static String toAnnotatedString(final String text) {
        String normalizedText = text.replace("\n", " ");
        return classifier.classifyWithInlineXML(normalizedText);
    }

    /**
     * @param text raw text
     */
    public static List<NamedEntity> getNamedEntities(final String text) {
        List<NamedEntity> result = new LinkedList<NamedEntity>();
        for (Pair<String, String> pair : FlatTagParser.parseAll(toAnnotatedString(text))) {
            NamedEntity attempt = Conversions.pairToNamedEntity(pair);
            if (attempt != null) {
                result.add(attempt);
            }
        }
        return result;
    }

    private EnglishNEExtractor() {
    }
}
