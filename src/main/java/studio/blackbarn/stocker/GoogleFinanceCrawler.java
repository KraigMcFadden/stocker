package studio.blackbarn.stocker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import studio.blackbarn.stocker.persistence.StockerPersistenceService;
import studio.blackbarn.utils.Log;
import studio.blackbarn.utils.RunnableManager;
import studio.blackbarn.utils.HtmlScraper;

/**
 * Created by kmcfadden on 8/21/17.
 */
// This class is used to find valid Google Finance pages and store them to track in the future.  It also does an
// initial sweep of the pages to collect preliminary data about the valid stocks
    
@Component
public class GoogleFinanceCrawler extends Crawler {

    private final int numLetters = 27;
    private char[] letters = new char[numLetters];

    private StockerPersistenceService persistenceService;

    @Autowired
    public GoogleFinanceCrawler(StockerPersistenceService persistenceService) {
        this.persistenceService = persistenceService;

        letters[0] = ' ';
        for (int i = 1; i < numLetters; i++) {
            letters[i] = (char) ('A' + i - 1);
        }
    }

    public void crawl() {

        Log.info("Starting crawl of Google Finance");

        RunnableManager manager = new RunnableManager(5);

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

                        // send off for asynchronous processing
                        submitForProcessing(manager, StockExchange.NYSE, Urls.GF_NYSE.getUrl() + tickerSymbol, tickerSymbol);
                        submitForProcessing(manager, StockExchange.NASDAQ, Urls.GF_NASDAQ.getUrl() + tickerSymbol, tickerSymbol);
                    }
                }
            }
        }
        manager.stop();
        Log.info("Finished crawl of Google Finance");
    }


    /*
    scrape and parse the html - if it's null it wasn't a valid page.  If it says it produced
    no matches it wasn't a valid ticker symbol

    it's time regulated to avoid blacklisting

    results are saved to database
     */
    private void submitForProcessing(RunnableManager manager, StockExchange exchange, String url, String tickerSymbol) {

        manager.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    GoogleFinanceHtmlParser parser = new GoogleFinanceHtmlParser(HtmlScraper.getHtml(url), tickerSymbol);
                    if (parser.isValid()) {
                        persistenceService.saveStockFundamentals(exchange, tickerSymbol, parser.getPrice(),
                                parser.getDividend(), parser.getYield(), parser.getPe(), parser.getEps(),
                                parser.getCap());
                    }
                    Thread.sleep(80 * 1000);
                } catch (Exception e) {
                    Log.error("Stock " + tickerSymbol + " couldn't be parsed / saved", e);
                }
            }
        });
    }
}
