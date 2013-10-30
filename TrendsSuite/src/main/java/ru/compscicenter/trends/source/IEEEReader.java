package ru.compscicenter.trends.source;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author alexeyev
 */
public class IEEEReader implements Reader {

    @Override
    public String readFile(File file) throws IOException {
        final BufferedReader br = new BufferedReader(new FileReader(file));
        final StringBuilder sb = new StringBuilder("");

        while (br.ready()) {
            sb.append(br.readLine());
        }
        br.close();
        return sb.toString();
    }
}
