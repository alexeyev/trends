package ru.compscicenter.trends;

import org.apache.commons.io.IOUtils;
import ru.compscicenter.trends.source.Source;
import ru.compscicenter.trends.source.cleaning.ArticleExtractor;
import ru.compscicenter.trends.source.cleaning.ArticleExtractorFactory;
import ru.compscicenter.trends.util.CounterWriter;

import java.awt.geom.IllegalPathStateException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This is a temporary script for blog articles processing.
 *
 * @author alexeyev
 */
public class HtmlProcessor {

    //hacks
    private static String removeS(final String s) {
        return s.replaceAll("\\sS\\s", "").replaceAll("^S\\s", "");
    }

    private static void p(Object s) {
        System.out.println(s);
    }

    private static void printDirectory(String dirPath, Source type) throws IOException {
        p("dir: " + dirPath);
        File dir = new File(dirPath);
        if (dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                try {
                    String data = IOUtils.toString(new FileInputStream(file));
                    ArticleExtractor extractor = ArticleExtractorFactory.newExtractor(type, data);
                    p(new SimpleDateFormat("yyyy.MM").format(extractor.getDate()));
                    //p(extractor.getTags());
                    //p(extractor.getTitle());
                    p(removeS(extractor.getText().replace("\n", "")));
                    p("---");
                } catch (NullPointerException e) {
                    p("---no data---");
                }
            }
        }
    }

    private static void writeToDirectory(String sourceDirPath, Source type, String destPath) throws IOException {

        final CounterWriter writer = new CounterWriter(System.out, 500, "%s docs processed.");

        final File dest = new File(destPath);
        dest.mkdirs();
        if (!dest.isDirectory())
            throw new IllegalPathStateException();

        final File dir = new File(sourceDirPath);
        if (dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                try {
                    final String data = IOUtils.toString(new FileInputStream(file));
                    final ArticleExtractor extractor = ArticleExtractorFactory.newExtractor(type, data);
                    final int titleHash = extractor.getTitle().hashCode();
                    final File subDir =
                            new File(dest.getAbsolutePath() + "/" +
                                    new SimpleDateFormat("yyyy.MM").format(extractor.getDate()) + "/");
                    subDir.mkdirs();
                    final FileWriter fw = new FileWriter(subDir + "/" + titleHash + new Date().getTime());
                    fw.write(removeS(extractor.getText().replace("\n", "")));
                    fw.close();
                    writer.tick();
                } catch (NullPointerException e) {
                    p("No data!");
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        //gizmodo
        writeToDirectory(
                "/home/alexeyev/hp/workspace/new_gizmodo/raw/",
                Source.GIZMODO,
                "/home/alexeyev/hp/workspace/new_gizmodo/corpus/");
    }
}
