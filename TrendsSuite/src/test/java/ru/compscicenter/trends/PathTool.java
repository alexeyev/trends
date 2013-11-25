package ru.compscicenter.trends;

import java.io.File;

/**
 * @author alexeyev
 */
public class PathTool {


    public static void main(String[] args) {
        File f  = new File("../mew_gizmodo/corpus3/t.txt");
        System.out.println(f.getAbsolutePath());
        System.out.println(f.getParentFile().getName());
    }
}
