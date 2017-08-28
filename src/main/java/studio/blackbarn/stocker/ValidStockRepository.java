package studio.blackbarn.stocker;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by kmcfadden on 8/28/17.
 */
public interface ValidStockRepository extends CrudRepository<ValidStockPO, Integer> {

    ValidStockPO findByTickerSymbol(String tickerSymbol);
}
