package studio.blackbarn.stocker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StockerPersistenceService {

    private FundamentalsRepository fundamentals;

    @Autowired
    public StockerPersistenceService(FundamentalsRepository fundamentals) {
        this.fundamentals = fundamentals;
    }

    public void saveStockFundamentals(String tickerSymbol, double price, Double div, Double yield, Double pe,
                                      Double eps, String cap) {
        FundamentalsPO po = fundamentals.findByTickerSymbol(tickerSymbol);
        if (po == null)
            po = new FundamentalsPO();

        po.setTickerSymbol(tickerSymbol);
        po.setPrice(price);
        po.setDiv(div);
        po.setYield(yield);
        po.setPe(pe);
        po.setEps(eps);
        po.setCap(cap);

        fundamentals.save(po);
    }
}
