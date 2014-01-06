package ru.compscicenter.trends;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author alexeyev
 */
public class CrunchBaseProductsUpdater {

    private static Logger log = LoggerFactory.getLogger("updater");

    private CrunchBaseProductsUpdater() {
    }

    private static String key = "9tntveu599j2wun8r9wz99mp";

    private static List<String> parseProds(String src) {
        JSONObject json = new JSONObject(src);
        JSONArray prods = json.getJSONArray("products");
        int size = prods.length();
        List<String> results = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            results.add(prods.getJSONObject(i).getString("name"));
        }
        return results;
    }

    private static FileWriter writer;

    private static void writeProds(String company, List<String> prods) throws IOException {
        for (String prod : prods) {
            writer.write(String.format("\"%s\",\"%s\"\n", prod, company));
        }
    }

    private static List<String> getCompaniesFromFile() throws IOException {
        BufferedReader br =
                new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream("crunchbase-companies.tsv")));
        List<String> companies = new LinkedList<>();
        while (br.ready()) {
            companies.add(br.readLine());
        }
        return companies;
    }

    private static void updateStuff() throws IOException {
        log.info("Starting...\n" + new Date());

        for (String company : getCompaniesFromFile()) {
            String url = String.format(
                    "http://api.crunchbase.com/v/1/company/%s.js?api_key=%s",
                    company.replace(" ", "_").replace(".", "-"),
                    key);

            HttpClient client = new DefaultHttpClient();
            try {
                log.info("Requesting data... " + company.replace(" ", "_"));
                HttpResponse response = client.execute(new HttpGet(url));
                String result = IOUtils.toString(response.getEntity().getContent(), "utf8");

                log.debug(result);

                if (!result.matches("\\s*")) {
                    List<String> prodsNames = parseProds(result);
                    writeProds(company, prodsNames);

                    log.debug(prodsNames.toString());
                    log.info(String.format("A total of %d products.", prodsNames.size()));
                } else {
                    log.info("No products.");
                }
                EntityUtils.consumeQuietly(response.getEntity());

            } catch (JSONException e) {
                log.error("Problems with parsing", e);

            }
        }
        log.info("Done.\n" + new Date());
    }

    public static void main(String[] args) throws IOException {
        writer = new FileWriter("crunchbase-products.tsv");
        updateStuff();
        writer.close();
    }
}
