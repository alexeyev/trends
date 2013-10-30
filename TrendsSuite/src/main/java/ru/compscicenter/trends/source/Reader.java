package ru.compscicenter.trends.source;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author alexeyev
 */
interface Reader {

  public String readFile(File file) throws IOException;

}
