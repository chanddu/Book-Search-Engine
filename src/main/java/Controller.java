import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class Controller {
    public static void main(String[] args) throws Exception {
        String crawlStorageFolder = "/Users/chandu/Desktop/Data";
        int numberOfCrawlers = 10;

        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);
        config.setMaxPagesToFetch(10000);

        /*
         * Instantiate the controller for this crawl.
         */
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        /*
         * For each crawl, you need to add some seed urls. These are the first
         * URLs that are fetched and then the crawler starts following links
         * which are found in these pages
         */
        controller.addSeed("https://www.reddit.com/r/books/");
        controller.addSeed("http://www.booksamillion.com/books?id=6791689580552");
        controller.addSeed("https://en.wikipedia.org/wiki/Book");
        controller.addSeed("http://www.newyorker.com/books");
        controller.addSeed("http://www.powells.com");
        controller.addSeed("http://www.npr.org/books/");
        controller.addSeed("http://books.disney.com");
        controller.addSeed("http://onlinebooks.library.upenn.edu");
        controller.addSeed("http://www.orbooks.com");
        controller.addSeed("http://www.nytimes.com/section/books");
        controller.addSeed("http://www.ebay.com/rpp/books");
        controller.addSeed("https://en.wikipedia.org/wiki/List_of_science_fiction_novels");
        controller.addSeed("https://en.wikipedia.org/wiki/List_of_Christian_apologetic_works");
        controller.addSeed("https://en.wikipedia.org/wiki/Psychedelic_literature");
        controller.addSeed("http://www.brazosbookstore.com/shop/books");
        controller.addSeed("https://www.amazon.com/s/ref=lp_283155_nr_n_10?fst=as%3Aoff&rh=n%3A283155%2Cn%3A%211000%2Cn%3A8975347011&bbn=1000&ie=UTF8&qid=1479310486&rnid=1000");
        controller.addSeed("https://www.amazon.com/s/ref=lp_283155_nr_n_2?fst=as%3Aoff&rh=n%3A283155%2Cn%3A%211000%2Cn%3A3&bbn=1000&ie=UTF8&qid=1479594436&rnid=1000");
        controller.addSeed("https://www.amazon.com/s/ref=lp_283155_nr_n_3?fst=as%3Aoff&rh=n%3A283155%2Cn%3A%211000%2Cn%3A3248857011&bbn=1000&ie=UTF8&qid=1479594436&rnid=1000");
        controller.addSeed("https://www.abebooks.com");
        controller.addSeed("http://www.nytimes.com/section/books?action=click&contentCollection=Arts%2FBooks&contentPlacement=2&module=SectionsNav&pgtype=sectionfront&region=TopBar&version=BrowseTree");

        /*
         * Start the crawl. This is a blocking operation, meaning that your code
         * will reach the line after this only when crawling is finished.
         */
        controller.start(Crawler.class, numberOfCrawlers);
    }
}