import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by kmcfadden on 8/21/17.
 */

// This class is used to find valid Google Finance pages and store them to track in the future.  It also does an
// initial sweep of the pages to collect preliminary data about the valid stocks
public class GoogleFinanceCrawler extends Crawler {

    private final int numLetters = 27;
    private char[] letters = new char[numLetters];

    private final ThreadManager manager = new ThreadManager(5);
    private final List<GoogleFinanceHtmlParser> parsers = new ArrayList<>();

    private final Set<String> urls = new HashSet<>();

    public GoogleFinanceCrawler() {
        letters[0] = ' ';
        for (int i = 1; i < numLetters; i++) {
            letters[i] = (char) ('A' + i - 1);
        }
    }

    public void run() {
        char[] ticker = new char[4];
        for (int i = 0; i < numLetters; i++) {
            for (int j = 0; j < numLetters; j++) {
                for (int k = 0; k < numLetters; k++) {
                    for (int m = 1; m < numLetters; m++) {

                        // conditions to skip on
                        if ((k == 0 && j > 0) || (k == 0 && i > 0) || (j == 0 && i > 0))
                            continue;

                        // produce ticker symbol
                        ticker[0] = letters[i];
                        ticker[1] = letters[j];
                        ticker[2] = letters[k];
                        ticker[3] = letters[m];

                        // append ticker symbol to google finance url
                        String tickerSymbol = String.valueOf(ticker).trim();
                        String url = "https://www.google.com/finance?q=NYSE:" + tickerSymbol;

                        // scrape and parse the html - if it's null it wasn't a valid page.  If it says it produced
                        // no matches it wasn't a valid ticker symbol
                        // ToDo: Make it time regulated to avoid blacklisting
                        manager.submit(new Thread(() -> {
                            GoogleFinanceHtmlParser parser = new GoogleFinanceHtmlParser(HtmlScraper.getHtml(url), tickerSymbol);
                            synchronized (parsers) {
                                parsers.add(parser);
                            }
                        }));
                        // ToDo: get results
                    }
                }
            }
        }
    }

//    // Testing
//    public static void main(String[] args) {
//        GoogleFinanceCrawler crawler = new GoogleFinanceCrawler();
//        System.out.println(crawler.letters);
//        crawler.run();
//    }
}
