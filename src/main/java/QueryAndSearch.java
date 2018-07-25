import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.flexible.standard.builders.PhraseQueryNodeBuilder;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;


public class QueryAndSearch {
    private final static String INDEX_DIR = "/Users/chandu/Desktop/luceneIndex";
    private final static int NUM_OF_RESULTS = 4;

    private PhraseQuery buildPhraseQuery(String queryStr) {
        PhraseQuery.Builder builder = new PhraseQuery.Builder();
        Arrays.stream(queryStr.split("\\s")).forEach(term -> {
            builder.add(new Term("content", term));
        });
        builder.setSlop(4);
        return builder.build();
    }

    private WildcardQuery buildWildCardQuery(String str) {
        return new WildcardQuery(new Term("content", str));
    }


    private String parseQuery(String query) throws IOException, ParseException{
        Analyzer analyzer = new SearchEngineAnalyzer().getAnalyzer();
        return new QueryParser("content", analyzer).parse(query).toString("content");
    }

    public static void main(String[] args) throws  IOException, ParseException{
        String querystr = args.length > 0 ? args[0] : "Game of Thrones";
        QueryAndSearch qs = new QueryAndSearch();
        IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(INDEX_DIR)));
        IndexSearcher searcher = new IndexSearcher(reader);
        Query query = qs.buildPhraseQuery(qs.parseQuery(querystr));
        TopDocs docs = searcher.search(query, NUM_OF_RESULTS, Sort.RELEVANCE);
        for (ScoreDoc hit : docs.scoreDocs) {
            Document d = searcher.doc(hit.doc);
            System.out.println(d.get("id"));
        }

    }


}
