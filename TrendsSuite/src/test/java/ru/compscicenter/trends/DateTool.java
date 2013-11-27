package ru.compscicenter.trends;


import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * corpus2 (monthly articles) to corpus3 (yearly)
 */
public class DateTool {

    private static Logger log = LoggerFactory.getLogger("");

    /**
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        File dir = new File("../new_gizmodo/corpus2/");
        File dest = new File("../new_gizmodo/corpus3/");
        dest.mkdirs();

        for (File subdir : dir.listFiles()) {
            log.info(subdir.getName() +
                    " -> " +
                    subdir.getName().split("\\.")[0]);
            File yDest =
                    new File(dest.getAbsolutePath() + "/" + subdir.getName().split("\\.")[0]);
            yDest.mkdirs();

            int i = 0;
            for (File file : subdir.listFiles()) {
                File destFile = new File(yDest.getAbsolutePath() + "/" + new Date().getTime() + "." + i);
                //destFile.mkdirs();
                log.info(file.getAbsolutePath() + "->" + destFile.getAbsolutePath());
                FileUtils.copyFile(file, destFile);
                i++;
            }
        }

    }
}