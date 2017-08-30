package studio.blackbarn.stocker.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import studio.blackbarn.stocker.StockExchange;
import studio.blackbarn.stocker.persistence.model.FundamentalsPO;
import studio.blackbarn.stocker.persistence.model.NASDAQFundamentalsPO;
import studio.blackbarn.stocker.persistence.model.NYSEFundamentalsPO;
import studio.blackbarn.stocker.persistence.repository.FundamentalsRepository;
import studio.blackbarn.stocker.persistence.repository.NASDAQFundamentalsRepository;
import studio.blackbarn.stocker.persistence.repository.NYSEFundamentalsRepository;

@Component
public class StockerPersistenceService {

    private NYSEFundamentalsRepository nyseFundamentals;
    private NASDAQFundamentalsRepository nasdaqFundamentals;
    private FundamentalsRepository fundamentals;

    @Autowired
    public StockerPersistenceService(NYSEFundamentalsRepository nyseFundamentals, NASDAQFundamentalsRepository nasdaqFundamentals,
                                     FundamentalsRepository fundamentals) {
        this.nyseFundamentals = nyseFundamentals;
        this.nasdaqFundamentals = nasdaqFundamentals;
        this.fundamentals = fundamentals;
    }

    public void saveStockFundamentals(StockExchange exchange, String tickerSymbol, double price, Double dividend,
                                      Double yield, Double pe, Double eps, String cap) {

        if (exchange.equals(StockExchange.NASDAQ)) {

            NASDAQFundamentalsPO po = nasdaqFundamentals.findByTickerSymbol(tickerSymbol);
            if (po == null)
                po = new NASDAQFundamentalsPO();

            po.setTickerSymbol(tickerSymbol);
            po.setPrice(price);
            po.setDividend(dividend);
            po.setYield(yield);
            po.setPe(pe);
            po.setEps(eps);
            po.setCap(cap);

            nasdaqFundamentals.save(po);

        } else if (exchange.equals((StockExchange.NYSE))) {

            NYSEFundamentalsPO po = nyseFundamentals.findByTickerSymbol(tickerSymbol);
            if (po == null)
                po = new NYSEFundamentalsPO();

            po.setTickerSymbol(tickerSymbol);
            po.setPrice(price);
            po.setDividend(dividend);
            po.setYield(yield);
            po.setPe(pe);
            po.setEps(eps);
            po.setCap(cap);

            nyseFundamentals.save(po);

        }
    }

    // hacky method to move data from two crap tables to one consolidated table - delete once done
    public void swapToNewTable() {

    }
}
