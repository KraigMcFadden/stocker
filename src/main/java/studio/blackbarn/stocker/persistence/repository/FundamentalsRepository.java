package studio.blackbarn.stocker.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import studio.blackbarn.stocker.persistence.model.FundamentalsPO;

/**
 * Created by kmcfadden on 8/30/17.
 */
public interface FundamentalsRepository extends CrudRepository<FundamentalsPO, Integer> {

    FundamentalsPO findByTickerSymbol(String tickerSymbol);
}
