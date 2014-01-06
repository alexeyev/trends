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
            writer.write(String.format("%s\t%s\n", prod, company));
        }
    }

    /**
     * @return a list of pairs: name, permalink
     */
    private static List<Pair<String, String>> getPermalinksAndCompaniesFromFile() throws IOException {
        BufferedReader br =
                new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream("crunchbase-companies.tsv")));
        List<Pair<String, String>> companies = new LinkedList<>();
        while (br.ready()) {
            String[] line = br.readLine().split("\t");
            companies.add(new Pair<String, String>(line[0], line[1]));
        }
        return companies;
    }

    private static void oneCompanyStuff(Pair<String, String> company, HttpClient client) throws IOException {
        String url = String.format(
                "http://api.crunchbase.com/v/1/company/%s.js?api_key=%s",
                company.second(),
                key);
        log.info("Requesting data... ");
        HttpResponse response = client.execute(new HttpGet(url));
        String result = IOUtils.toString(response.getEntity().getContent(), "utf8");
        EntityUtils.consumeQuietly(response.getEntity());

        log.debug(result);

        if (!result.matches("\\s*")) {
            List<String> prodsNames = parseProds(result);
            writeProds(company.first(), prodsNames);
            log.info(String.format("A total of %d products: %s", prodsNames.size(), prodsNames.toString()));
        } else {
            log.info("No products.");
        }
    }

    private static void updateStuff() throws IOException {
        log.info("Starting...\n" + new Date());

        HttpClient client = new DefaultHttpClient();

        int counter = 1;
        for (Pair<String, String> company : getPermalinksAndCompaniesFromFile()) {
            log.info(String.format("%d %s", counter, company.first()));
            counter++;
            //todo: rewrite as a loop
            //todo: or, better, get names for requests from companies list API
            try {
                oneCompanyStuff(company, client);
            } catch (Exception e) {
                log.error("Ducked up company: " + company);
                FileWriter fw = new FileWriter("duc.txt", true);
                fw.append(company + "\n");
                fw.close();
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
