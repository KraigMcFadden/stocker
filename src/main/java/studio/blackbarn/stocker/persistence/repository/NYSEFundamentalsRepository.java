package studio.blackbarn.stocker.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import studio.blackbarn.stocker.persistence.model.NYSEFundamentalsPO;

/**
 * Created by kmcfadden on 8/28/17.
 */
public interface NYSEFundamentalsRepository extends CrudRepository<NYSEFundamentalsPO, Integer> {

    NYSEFundamentalsPO findByTickerSymbol(String tickerSymbol);
}
