package studio.blackbarn.stocker;

import javax.persistence.*;

/**
 * Created by kmcfadden on 8/28/17.
 */

@Entity
@Table(name = "fundamentals")
public class FundamentalsPO {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private String tickerSymbol;
    private double price;
    private Double div;
    private Double yield;
    private Double pe;
    private Double eps;
    private String cap;

    public FundamentalsPO() {}

    public FundamentalsPO(String tickerSymbol, double price, Double div, Double yield, Double pe, Double eps, String cap) {
        this.tickerSymbol = tickerSymbol;
        this.price = price;
        this.div = div;
        this.yield = yield;
        this.pe = pe;
        this.eps = eps;
        this.cap = cap;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Double getDiv() {
        return div;
    }

    public void setDiv(Double div) {
        this.div = div;
    }

    public Double getYield() {
        return yield;
    }

    public void setYield(Double yield) {
        this.yield = yield;
    }

    public Double getPe() {
        return pe;
    }

    public void setPe(Double pe) {
        this.pe = pe;
    }

    public Double getEps() {
        return eps;
    }

    public void setEps(Double eps) {
        this.eps = eps;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }
}
