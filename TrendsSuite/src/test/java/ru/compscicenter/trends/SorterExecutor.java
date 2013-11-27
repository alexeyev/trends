package ru.compscicenter.trends;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.compscicenter.trends.ner.EnglishNEExtractor;
import ru.compscicenter.trends.ner.model.NamedEntity;
import ru.compscicenter.trends.ner.model.Tag;
import ru.compscicenter.trends.util.CounterLogger;

import java.io.File;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * See SortingTool.
 * @author alexeyev
 */
public class SorterExecutor implements Runnable {

    private final Logger log = LoggerFactory.getLogger(this.toString());

    private final BlockingQueue<File> queue;
    private final CounterLogger cl;

    SorterExecutor(BlockingQueue<File> q,
                   CounterLogger clog) {
        queue = q;
        cl = clog;
    }

    @Override
    public void run() {
        File destDirectory = new File("../new_gizmodo/corpus4/");
        destDirectory.mkdirs();
        while ((!queue.isEmpty())) {
            try {
                File year = queue.take();
                log.info(year.getAbsolutePath());
                // articles
                for (File article : year.listFiles()) {
                    String text = FileUtils.readFileToString(article);
                    // NER
                    List<NamedEntity> nes = EnglishNEExtractor.getNamedEntities(text);
                    for (NamedEntity ne : nes) {
                        // filtering organizations
                        if (ne.getTag().equals(Tag.ORGANIZATION)) {
                            Long orgId = SortingTool.map.getMap().get(ne.getWords());
                            // only known organizations
                            if (orgId == null) {
                                //do nothing

                                // only companies that are mentioned often
                            } else if (SortingTool.interestingCompanies.contains(orgId)) {
                                File destYearDir = new File(
                                        String.format(
                                                "%s/%s/%s/",
                                                destDirectory.getAbsolutePath(),
                                                orgId.toString(),
                                                year.getName()));
                                destYearDir.mkdirs();
                                FileUtils.copyFileToDirectory(article, destYearDir);
                            }
                        }
                    }
                    cl.tick();
                }
            } catch (Exception e) {
                log.error("Problems getting stuff from queue.");
            }
        }

    }
}
