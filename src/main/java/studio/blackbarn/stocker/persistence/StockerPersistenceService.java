package studio.blackbarn.stocker.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import studio.blackbarn.stocker.StockExchange;
import studio.blackbarn.stocker.persistence.model.FundamentalsPO;
import studio.blackbarn.stocker.persistence.repository.FundamentalsRepository;

import java.util.HashMap;
import java.util.Map;

@Component
public class StockerPersistenceService {

    // instance fields
    private Map<String, FundamentalsPO> fundamentalsCache;

    // autowired fields
    private FundamentalsRepository fundamentals;

    @Autowired
    public StockerPersistenceService(FundamentalsRepository fundamentals) {
        this.fundamentals = fundamentals;

        // populate cache
        fundamentalsCache = new HashMap<>(1280);
        for (FundamentalsPO po : fundamentals.findAll()) {
            fundamentalsCache.put(po.getTickerSymbol(), po);
        }
    }

    public void saveFundamentals(String tickerSymbol, double price, Double dividend, Double yield, Double pe,
                                 Double eps, String cap, StockExchange exchange) {

        FundamentalsPO po = lookupFundamentals(tickerSymbol);
        po.setTickerSymbol(tickerSymbol);
        po.setPrice(price);
        po.setDividend(dividend);
        po.setYield(yield);
        po.setPe(pe);
        po.setEps(eps);
        po.setCap(cap);
        po.setExchange(exchange);
        saveFundamentals(po);
    }

    public void saveFundamentals(FundamentalsPO po) {
        fundamentalsCache.put(po.getTickerSymbol(), po);
        fundamentals.save(po);
    }

    private FundamentalsPO lookupFundamentals(String tickerSymbol) {

        if (fundamentalsCache.containsKey(tickerSymbol)) {
            return fundamentalsCache.get(tickerSymbol);
        } else {
            FundamentalsPO po = new FundamentalsPO();
            po.setTickerSymbol(tickerSymbol);
            fundamentalsCache.put(tickerSymbol, po);
            return po;
        }
    }
}
