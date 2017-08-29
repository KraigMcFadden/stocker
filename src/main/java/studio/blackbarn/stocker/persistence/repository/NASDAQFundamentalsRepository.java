package studio.blackbarn.stocker.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import studio.blackbarn.stocker.persistence.model.NASDAQFundamentalsPO;

/**
 * Created by kmcfadden on 8/29/17.
 */
public interface NASDAQFundamentalsRepository extends CrudRepository<NASDAQFundamentalsPO, Integer> {

    NASDAQFundamentalsPO findByTickerSymbol(String tickerSymbol);
}
