package studio.blackbarn.stocker.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import studio.blackbarn.stocker.crawl.InitialSearchCrawler;
import studio.blackbarn.stocker.persistence.StockerPersistenceService;

/**
 * Created by kmcfadden on 8/29/17.
 */

@RestController
public class StockerRestController {

    @Autowired
    InitialSearchCrawler gfCrawler;

    @Autowired
    StockerPersistenceService persistenceService;

    @RequestMapping(value = "/crawl")
    public ResponseEntity crawl(@RequestParam(value = "which") String crawlerName) {

        gfCrawler.crawl();
        return new ResponseEntity(HttpStatus.OK);
    }

//    @RequestMapping(value = "/test")
//    public ResponseEntity test(@RequestParam(value = "ticker") String tickerSymbol) {
//
//        persistenceService.saveStockFundamentals(tickerSymbol, 1.0, null, null, null, null, null);
//        return new ResponseEntity(HttpStatus.OK);
//    }
}
