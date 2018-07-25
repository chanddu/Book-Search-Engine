import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.en.PorterStemFilterFactory;
import org.apache.lucene.analysis.standard.StandardFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Indexer {
    private final String INDEX_DIR = "/Users/chandu/Desktop/luceneIndex";
    private final String CRAWLED_DATA_PATH = "/Users/chandu/Desktop/CrawledData/";

    private void index() throws IOException {
        Analyzer analyzer = new SearchEngineAnalyzer().getAnalyzer();
        FSDirectory index = FSDirectory.open(Paths.get(INDEX_DIR));
        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        IndexWriter writer = new IndexWriter(index, config);
        Files.list(Paths.get(CRAWLED_DATA_PATH)).forEach(path -> {
            String id = path.getFileName().toString();
            try {
                String content = new String(Files.readAllBytes(path));
                addDoc(writer, content, id);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });
        writer.close();
    }

    private void addDoc(IndexWriter writer, String content, String id) throws IOException {
        Document doc = new Document();
        doc.add(new TextField("content", content, Field.Store.YES));
        doc.add(new StringField("id", id, Field.Store.YES));
        writer.addDocument(doc);
    }

    public static void main(String[] args) throws IOException{
        Indexer indexer = new Indexer();
        indexer.index();
    }
}
