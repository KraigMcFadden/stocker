package studio.blackbarn.stocker.crawl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import studio.blackbarn.stocker.StockExchange;
import studio.blackbarn.stocker.Urls;
import studio.blackbarn.stocker.crawl.parse.FundamentalHtmlParser;
import studio.blackbarn.stocker.crawl.parse.GoogleFinanceFundamentalHtmlParser;
import studio.blackbarn.stocker.persistence.StockerPersistenceService;
import studio.blackbarn.utils.Log;
import studio.blackbarn.utils.RunnableManager;
import studio.blackbarn.utils.HtmlScraper;

import java.net.ConnectException;

/**
 * Created by kmcfadden on 8/21/17.
 */
// This class is used to find valid stock tickers and store them to track in the future.  It also does an
// initial sweep of the pages to collect preliminary data about the valid stocks
    
@Component
public class InitialSearchCrawler extends Crawler {

    private final int numLetters = 27;
    private char[] letters = new char[numLetters];

    private StockerPersistenceService persistenceService;

    @Autowired
    public InitialSearchCrawler(StockerPersistenceService persistenceService) {
        this.persistenceService = persistenceService;

        letters[0] = ' ';
        for (int i = 1; i < numLetters; i++) {
            letters[i] = (char) ('A' + i - 1);
        }
    }

    @Override
    public void crawl() {

        Log.info("Starting crawl of all possible stocks on NYSE and NASDAQ");

        RunnableManager manager = new RunnableManager(4);
        int index = 1;

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


                        /*
                        scrape and parse the html - if it's null it wasn't a valid page.  If it says it produced
                        no matches it wasn't a valid ticker symbol

                        it's time regulated to avoid blacklisting

                        results are saved to database

                        NYSE is always tried first, then NASDAQ is done as a backup so only one copy of each ticker
                        is saved.  NYSE is default essentially
                         */
                        final int n = index;
                        manager.submit(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    if (!parseAndSave(tickerSymbol, StockExchange.NYSE, n))
                                        parseAndSave(tickerSymbol, StockExchange.NASDAQ, n);
                                    Thread.sleep(1000);
                                } catch(ConnectException e) {  // retry in ten minutes if a bad connection
                                    try {
                                        Thread.sleep(1000 * 60 * 10);
                                        run();
                                    } catch (Exception ex) {}
                                } catch (Exception e) {
                                    Log.error("Stock " + tickerSymbol + " couldn't be parsed / saved", e);
                                }
                            }
                        });

                        index++;
                        if (index > 3) { index = 1; }
                    }
                }
            }
        }
        manager.stop();
        Log.info("Finished crawl of all stocks on NYSE and NASDAQ");
    }

    private boolean parseAndSave(String tickerSymbol, StockExchange exchange, int parserIndex) throws Exception {

        FundamentalHtmlParser parser;
        if (parserIndex == 1 && exchange.equals(StockExchange.NYSE))
            parser = new GoogleFinanceFundamentalHtmlParser(HtmlScraper.getHtml(Urls.GF_NYSE.getUrl() + tickerSymbol), tickerSymbol);
        else if (parserIndex == 1 && exchange.equals(StockExchange.NASDAQ))
            parser = new GoogleFinanceFundamentalHtmlParser(HtmlScraper.getHtml(Urls.GF_NASDAQ.getUrl() + tickerSymbol), tickerSymbol);
        else
            parser = new GoogleFinanceFundamentalHtmlParser(HtmlScraper.getHtml(Urls.GF_NYSE.getUrl() + tickerSymbol), tickerSymbol);

        if (parser.isValid()) {
            persistenceService.saveFundamentals(parser.getData(exchange));
            return true;
        }
        return false;
    }
}
