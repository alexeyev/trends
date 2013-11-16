package ru.compscicenter.trends;

import edu.stanford.nlp.io.IOUtils;
import org.apache.commons.collections.Bag;
import org.apache.commons.collections.bag.TreeBag;
import ru.compscicenter.trends.ner.EnglishNEExtractor;
import ru.compscicenter.trends.ner.model.NamedEntity;
import ru.compscicenter.trends.ner.model.Tag;
import ru.compscicenter.trends.source.IEEEReader;
import ru.compscicenter.trends.util.CounterWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DirectoryProcessor {

    private static final PrintStream log = System.out;
    private static final CounterWriter tickLog = new CounterWriter(log, 10000, "Processed: %s");

    private static void updateMap(Map<Tag, TreeBag> map, NamedEntity ne) {
        if (!map.containsKey(ne.getTag())) {
            map.put(ne.getTag(), new TreeBag());
        }
        map.get(ne.getTag()).add(ne.getWords());
    }

    abstract private static class Filter<T> {
        public abstract boolean check(T t);
    }

    private static void printMap(Map<Tag, TreeBag> map, FileWriter out, Filter<Tag> filter) throws IOException {
        for (Tag tag : map.keySet()) {
            if (filter.check(tag)) {
                Bag bag = map.get(tag);
                for (Object rawWord : bag.uniqueSet()) {
                    String word = (String) rawWord;
                    out.write(word + "; " + bag.getCount(rawWord) + "\n");
                }
            }
        }
        //todo: something
        out.close();
    }

    private static void recursiveExtractor(final File srcDir, final FileWriter destFile) throws IOException {
        if (!srcDir.isDirectory()) {
            throw new IllegalArgumentException();
        }
        for (final File file : srcDir.listFiles()) {
            if (file.isFile()) {
                final String text = IOUtils.slurpFile(file);
                final List<NamedEntity> nes = EnglishNEExtractor.getNamedEntities(text);
                for (final NamedEntity ne : nes) {
                    if (ne.getTag().equals(Tag.ORGANIZATION)) {
                        destFile.write(ne.getWords() + "\n");
                    }
                }
            } else if (file.isDirectory()) {
                recursiveExtractor(file, destFile);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        final String sourceDirectoryPath = "/home/alexeyev/hp/workspace/new_gizmodo/corpus/";
        final File headDir = new File(sourceDirectoryPath);

        final FileWriter fw = new FileWriter("/home/alexeyev/hp/workspace/new_gizmodo/all_nes.txt");
        recursiveExtractor(headDir, fw);
        fw.close();

//        if (headDir.isDirectory()) {
//            // listing all year-aware subdirs
//            final File[] dirs = headDir.listFiles();
//            if (dirs != null) {
//                for (final File dir : dirs) {
//
//                    log.println("Managing dir: " + dir.getName());
//
//                    final Map<Tag, TreeBag> bags = new HashMap<Tag, TreeBag>();
//
//                    if (dir.isDirectory()) {
//                        final File[] files = dir.listFiles();
//                        if (files != null) {
//                            for (final File file : files) {
//                                final String text = IOUtils.slurpFile(file);
//                                final List<NamedEntity> nes = EnglishNEExtractor.getNamedEntities(text);
//                                for (final NamedEntity ne : nes) {
//                                    updateMap(bags, ne);
//                                }
//                                tickLog.tick();
//                            }
//                        }
//                    }
//
//                    printMap(bags,
//                            new FileWriter("../aggregation/" + dir.getName() + ".txt"),
//                            new Filter<Tag>() {
//                                @Override
//                                public boolean check(Tag tag) {
//                                    return tag.equals(Tag.ORGANIZATION);
//                                }
//                            });
//                }
//            }
//        } else {
//            throw new IllegalArgumentException();
//        }
    }
}