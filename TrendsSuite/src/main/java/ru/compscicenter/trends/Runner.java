package ru.compscicenter.trends;

import org.apache.commons.collections.Bag;
import org.apache.commons.collections.bag.TreeBag;
import ru.compscicenter.trends.ner.EnglishNEExtractor;
import ru.compscicenter.trends.ner.model.NamedEntity;
import ru.compscicenter.trends.ner.model.Tag;
import ru.compscicenter.trends.source.IEEEReader;
import ru.compscicenter.trends.util.CounterWriter;

import java.io.File;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Runner {

    private static final PrintStream log = System.out;

    private static final CounterWriter tickLog = new CounterWriter(log, 50, "Processed: %s");

    private static void updateMap(Map<Tag, TreeBag> map, NamedEntity ne) {
        if (!map.containsKey(ne.getTag())) {
            map.put(ne.getTag(), new TreeBag());
        }
        map.get(ne.getTag()).add(ne.getWords());
    }

    abstract private static class Filter<T> {
        public abstract boolean check(T t);
    }

    private static void printMap(Map<Tag, TreeBag> map, PrintStream out, Filter<Tag> filter) {
        for (Tag tag : map.keySet()) {

            if (filter.check(tag)) {
                out.println(tag.name());
                Bag bag = map.get(tag);

                for (Object rawWord : bag.uniqueSet()) {
                    String word = (String) rawWord;
                    out.println("  " + word + " " + bag.getCount(rawWord));
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        String sourceDirectoryPath = "/home/alexeyev/hp/data/ieee_parsed/2003/";
        Map<Tag, TreeBag> bags = new HashMap<Tag, TreeBag>();

        final File dir = new File(sourceDirectoryPath);
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    String text = new IEEEReader().readFile(file);
                    List<NamedEntity> nes = EnglishNEExtractor.getNamedEntities(text);
                    for (NamedEntity ne : nes) {
                        updateMap(bags, ne);
                    }
                    tickLog.tick();
                }
            }
        }

        printMap(bags,
                System.out,
                new Filter<Tag>() {
                    @Override
                    public boolean check(Tag tag) {
                        return tag.equals(Tag.ORGANIZATION);
                    }
                });
    }
}