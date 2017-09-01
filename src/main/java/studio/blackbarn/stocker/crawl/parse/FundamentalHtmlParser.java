package studio.blackbarn.stocker.crawl.parse;

import studio.blackbarn.stocker.StockExchange;
import studio.blackbarn.stocker.persistence.model.FundamentalsPO;

/**
 * Created by kmcfadden on 8/22/17.
 */
public abstract class FundamentalHtmlParser {

    public abstract boolean isValid();

    public abstract FundamentalsPO getData(StockExchange exchange);
}
