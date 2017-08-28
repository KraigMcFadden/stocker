package studio.blackbarn.stocker;

import javax.persistence.*;

/**
 * Created by kmcfadden on 8/28/17.
 */

@Entity
@Table(name = "valid_stocks")
public class ValidStockPO {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private String tickerSymbol;
    private String gfUrl;
    private String srUrl;

    public ValidStockPO() {}

    public ValidStockPO(String tickerSymbol, String gfUrl, String srUrl) {
        this.tickerSymbol = tickerSymbol;
        this.gfUrl = gfUrl;
        this.srUrl = srUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public void setTickerSymbol(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }

    public String getGFUrl() {
        return gfUrl;
    }

    public void setGFUrl(String gfUrl) {
        this.gfUrl = gfUrl;
    }


    public String getSRUrl() {
        return srUrl;
    }

    public void setSRUrl(String srUrl) {
        this.srUrl = srUrl;
    }
}
