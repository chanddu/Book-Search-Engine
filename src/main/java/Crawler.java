import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.regex.Pattern;

public class Crawler extends WebCrawler {
    private final static Pattern FILTERS = Pattern.compile(
            ".*(\\.(css|js|bmp|gif|jpe?g|png|tiff?|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|pdf" +
                    "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

    private final String BASE_PATH = "/Users/chandu/Desktop/CrawledData";
    private final String GRAPH_PATH = "/Users/chandu/Desktop/graph.txt";
    private final String URLS_PATH = "/Users/chandu/Desktop/urls.txt";

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        return !FILTERS.matcher(href).matches();

    }

    @Override
    public void visit(Page page) {
        int docId = page.getWebURL().getDocid();
        int parentDocId = page.getWebURL().getParentDocid();
        String url = String.join(" ", String.valueOf(docId), page.getWebURL().getURL(), System.lineSeparator());

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String content = String.join(" ", htmlParseData.getTitle(), htmlParseData.getText());
            String edge = String.join(" ", String.valueOf(parentDocId), String.valueOf(docId), System.lineSeparator());
            try {
                Files.write(Paths.get(BASE_PATH, + docId + ".txt"), content.getBytes());
                Files.write(Paths.get(GRAPH_PATH), edge.getBytes(), StandardOpenOption.APPEND);
                Files.write(Paths.get(URLS_PATH), url.getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
