package ru.compscicenter.trends;

import com.hp.hpl.taxonomy.Topic;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.compscicenter.trends.clustering.GizmodoOrgsMap;
import ru.compscicenter.trends.clustering.OrgsMap;
import ru.compscicenter.trends.ner.EnglishNEExtractor;
import ru.compscicenter.trends.ner.model.NamedEntity;
import ru.compscicenter.trends.ner.model.Tag;
import ru.compscicenter.trends.trends.TrendsExtractor;
import ru.compscicenter.trends.util.FilesCollector;
import ru.compscicenter.trends.util.Pair;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author alexeyev
 */
public class DirectoryTrendsProcessor {

    private final static Logger log = LoggerFactory.getLogger("trends-main-tool");
    private final static OrgsMap map = new GizmodoOrgsMap();
    private final static TrendsExtractor trendsExtractor = new TrendsExtractor();

    private static Pair<List<NamedEntity>, List<Topic>> getTargetData(final String text) {

        // wish it was Scala
        // NER
        List<NamedEntity> fileNEs = EnglishNEExtractor.getNamedEntities(text);
        List<NamedEntity> nes = new LinkedList<NamedEntity>();
        for (NamedEntity ne : fileNEs) {
            if (ne.getTag().equals(Tag.ORGANIZATION)) {
                nes.add(ne);
            }
        }

        // Trends
        List<Topic> topics = trendsExtractor.extract(text);
        return new Pair<List<NamedEntity>, List<Topic>>(nes, topics);
    }

    public static void main(String[] args) throws IOException {
        //todo: decomposition!

        String srcPath = "../new_gizmodo/corpus2/2005.07/";
        String outPath = "../new_gizmodo/results/";

        File src = new File(srcPath);

        LinkedList<Pair<List<NamedEntity>, List<Topic>>> meta =
                new LinkedList<Pair<List<NamedEntity>, List<Topic>>>();

        for (File f : FilesCollector.getAllFiles(src)) {
            String text = FileUtils.readFileToString(f).trim();
            meta.add(getTargetData(text));
        }

        FileWriter writer = new FileWriter(outPath + src.getName() + ".tsv");

        String outputPattern = "%s\t%s\t%s\t%d\t%s\n";

        for (Pair<List<NamedEntity>, List<Topic>> pair : meta) {
            for (NamedEntity ne : pair.first()) {
                for (Topic topic : pair.second()) {
                    writer.write(
                            String.format(outputPattern,
                                    ne.getWords(),
                                    map.getMap().get(ne.getWords()),
                                    topic.name(),
                                    topic.id(),
                                    topic.score));
                }
            }
        }

        /**
         * BUT:
         * $ wc -l 2005.07.tsv
         44873 2005.07.tsv
         * $ cat 2005.07.tsv | sort | uniq | wc -l
         30458
         */
        writer.close();
    }

}
