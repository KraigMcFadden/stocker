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
    private double price;
    private double div;
    private double yield;
    private double pe;
    private double eps;
    private double cap;

    public FundamentalsPO() {}

    public FundamentalsPO(double price, double div, double yield, double pe, double eps, double cap) {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiv() {
        return div;
    }

    public void setDiv(double div) {
        this.div = div;
    }

    public double getYield() {
        return yield;
    }

    public void setYield(double yield) {
        this.yield = yield;
    }

    public double getPe() {
        return pe;
    }

    public void setPe(double pe) {
        this.pe = pe;
    }

    public double getEps() {
        return eps;
    }

    public void setEps(double eps) {
        this.eps = eps;
    }

    public double getCap() {
        return cap;
    }

    public void setCap(double cap) {
        this.cap = cap;
    }
}
