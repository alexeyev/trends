package ru.compscicenter.trends;

import org.apache.commons.io.IOUtils;
import ru.compscicenter.trends.source.Source;
import ru.compscicenter.trends.source.cleaning.ArticleExtractor;
import ru.compscicenter.trends.source.cleaning.ArticleExtractorFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author alexeyev
 */
public class HtmlProcessor {

    private static void p(Object s) {
        System.out.println(s);
    }

    private static void printDirectory(String dirPath, Source type) throws IOException {
        p("dir: " + dirPath);
        File dir = new File(dirPath);
        if (dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                String data = IOUtils.toString(new FileInputStream(file));
                ArticleExtractor extractor = ArticleExtractorFactory.newExtractor(type, data);
                p(extractor.getDate());
                p(extractor.getTags());
                p(extractor.getTitle());
                p(extractor.getText().replace("\n", ""));
                p("---");
            }
        }
    }

    public static void main(String[] args) throws IOException {
        //gizmodo
        printDirectory("../new_gizmodo/sample/", Source.GIZMODO);
        //techcrunch
        printDirectory("../techcrunch/sample/", Source.TECHCRUNCH);
    }
}
