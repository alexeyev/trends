package ru.compscicenter.trends;

import com.hp.hpl.taxonomy.Extract;
import com.hp.hpl.taxonomy.TaxonomyExtractorBasic;
import com.hp.hpl.taxonomy.Term;
import com.hp.hpl.taxonomy.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * @author alexeyev
 */
public class TrendsTool {

    private static Logger log = LoggerFactory.getLogger("trends");

    public static void main(String[] args) {
        final String[] arguments = new String[]{"basic", ".", "."};
        try {
            final ArrayList<Term> terms = new TaxonomyExtractorBasic().getTerms(text);
            final ArrayList<Topic> topics = new TaxonomyExtractorBasic().extract(terms);

//            Extract.main(arguments);
        } catch (Exception e) {
            log.error("poo-tee-weet", e);
        }
    }

    private final static String text = "One man's nocturnal emission into the pulsating streets of Paris. Drinks will be had, porn will be stolen, and balls will be played with in this night of electronic debauchery. Click through for the video with a very NSFW splash screen.\n" +

            "Turn Out the Lights is the latest single and title track from Tom Dinsdale's (formerly of Audio Bully) new project, Mineo. P.E. Joubert directed this, the official music video. You can listen to the full song on Soundcloud and get the album from Beatport.\n";
}
