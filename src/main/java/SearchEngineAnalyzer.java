import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.en.PorterStemFilterFactory;
import org.apache.lucene.analysis.standard.StandardFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;

import java.io.IOException;

public class SearchEngineAnalyzer {
    public Analyzer getAnalyzer() throws IOException {
        Analyzer analyzer = CustomAnalyzer.builder()
                .withTokenizer(StandardTokenizerFactory.class)
                .addTokenFilter(StandardFilterFactory.class)
                .addTokenFilter(LowerCaseFilterFactory.class)
                .addTokenFilter(StopFilterFactory.class)
                .addTokenFilter(PorterStemFilterFactory.class)
                .build();
        return analyzer;
    }
}
