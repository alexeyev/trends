package ru.compscicenter.trends;


import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.compscicenter.trends.util.Pair;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Putting crunchbase companies to 'crunchbase.tsv'.
 * Takes about 55 seconds.
 *
 * @author alexeyev
 */
public class CrunchBaseCompaniesUpdater {

    private static Logger log = LoggerFactory.getLogger("updater");

    private CrunchBaseCompaniesUpdater() {
    }

    private static String key = "9tntveu599j2wun8r9wz99mp";

    private static List<Pair<String, String>> parseCompanies(String src) {

        JSONArray compRecords = new JSONArray(src);
        int size = compRecords.length();
        List<Pair<String, String>> results = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            JSONObject json = compRecords.getJSONObject(i);
            results.add(new Pair<String, String>(json.getString("name"), json.getString("permalink")));
        }
        return results;
    }

    private static void writeCompanies(List<Pair<String, String>> companies) throws IOException {
        FileWriter writer = new FileWriter("crunchbase-companies.csv");
        for (Pair company : companies) {
            writer.write(String.format("%s\t%s\n", company.first(), company.second()));
        }
        writer.close();
    }

    private static void updateCompanies() throws IOException {

        String url = "http://api.crunchbase.com/v/1/companies.js?api_key=" + key;

        HttpClient client = new DefaultHttpClient();
        log.info("Requesting data...\n" + new Date());
        HttpResponse response = client.execute(new HttpGet(url));
        String result = IOUtils.toString(response.getEntity().getContent(), "utf8");

        log.debug(result);

        List<Pair<String, String>> companiesNames = parseCompanies(result);
        writeCompanies(companiesNames);

        log.debug(companiesNames.toString());
        log.info(String.format("A total of %d companies.", companiesNames.size()));

        EntityUtils.consumeQuietly(response.getEntity());
        log.info("Done.\n" + new Date());
    }

    public static void main(String[] args) throws IOException {
        updateCompanies();
    }
}
