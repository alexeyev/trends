package ru.compscicenter.trends.matching;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.sandbox.queries.SlowFuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocsCollector;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * APL is cool!
 * Creates a new Lucene index for each file with companies list
 * and does fuzzy and magic search.
 *
 * @author alexeyev
 */
public class LuceneMatcher {
    //todo: decomposition

    private Logger log = LoggerFactory.getLogger("lucene-matcher");
    private String companyField = "name";
    private Version luceneVersion = Version.LUCENE_46;

    private void addToIndex(final IndexWriter iw, String company) throws IOException {
        Document doc = new Document();
        doc.add(new TextField(companyField, company, Field.Store.YES));
        iw.addDocument(doc);
    }

    private Analyzer analyzer = new StandardAnalyzer(luceneVersion);
    private Directory index = new RAMDirectory();
    private IndexWriterConfig config = new IndexWriterConfig(luceneVersion, analyzer);

    private void fillIndex(File file) throws IOException {
        IndexWriter iw = new IndexWriter(index, config);
        BufferedReader br = new BufferedReader(new FileReader(file));
        while (br.ready()) {
            String line = br.readLine().trim();
            if (!line.equals("")) {
                addToIndex(iw, line.trim());
            }
        }
        iw.close();
    }

    public LuceneMatcher(File companies) throws IOException {
        fillIndex(companies);
        log.info("Index built.");
    }

    public Collection<String> magicSearch(String query, Float threshold) throws IOException, ParseException {
        IndexReader reader = IndexReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);
        QueryParser qp = new QueryParser(luceneVersion, companyField, analyzer);

        TopDocsCollector collector = TopScoreDocCollector.create(30, true);
        searcher.search(qp.parse(query), collector);
        List<ScoreDoc> hits = Arrays.asList(collector.topDocs().scoreDocs);
        LinkedList<String> results = new LinkedList<>();
        for (ScoreDoc scoreDoc : hits) {
            if (scoreDoc.score > threshold) {
                results.add(searcher.doc(scoreDoc.doc).get(companyField));
            }
        }
        return results;
    }


    public Collection<String> fuzzySearch(String query, int maxEditDistance) throws IOException {

        SlowFuzzyQuery fq =
                new SlowFuzzyQuery(
                        new Term(companyField, query),
                        maxEditDistance);

        IndexReader reader = IndexReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);

        TopDocsCollector collector = TopScoreDocCollector.create(10, true);
        searcher.search(fq, collector);
        List<ScoreDoc> hits = Arrays.asList(collector.topDocs().scoreDocs);
        LinkedList<String> results = new LinkedList<>();
        for (ScoreDoc scoreDoc : hits) {
            results.add(searcher.doc(scoreDoc.doc).get(companyField));
        }
        return results;
    }
}
