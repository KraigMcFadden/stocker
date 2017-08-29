package studio.blackbarn.stocker.persistence.model;

import javax.persistence.*;

/**
 * Created by kmcfadden on 8/29/17.
 */

@Entity
@Table(name = "nasdaq_fundamentals")
public class NASDAQFundamentalsPO {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    private String tickerSymbol;
    private double price;
    private Double dividend;
    private Double yield;
    private Double pe;
    private Double eps;
    private String cap;

    public NASDAQFundamentalsPO() {}

    public NASDAQFundamentalsPO(String tickerSymbol, double price, Double dividend, Double yield, Double pe, Double eps, String cap) {
        this.tickerSymbol = tickerSymbol;
        this.price = price;
        this.dividend = dividend;
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

    public Double getDividend() {
        return dividend;
    }

    public void setDividend(Double dividend) {
        this.dividend = dividend;
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
