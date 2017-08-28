package studio.blackbarn.stocker;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by kmcfadden on 8/28/17.
 */
public interface FundamentalsRepository extends CrudRepository<FundamentalsPO, Integer> {

    FundamentalsPO findByTickerSymbol(String tickerSymbol);
}
