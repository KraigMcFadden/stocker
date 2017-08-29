package studio.blackbarn.stocker;

import studio.blackbarn.utils.HtmlScraper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kmcfadden on 8/24/17.
 */
public class PortfolioAnalyzer {

    private Map<String, Double> holdings = new HashMap<>();  // contains stock ticker and number of shares

    // stats about the stock
    private double portfolioYield;
    private double portfolioValue;
    private double portfolioYieldOnCost;
    private double twelveMonthForwardDividendIncome;

    public PortfolioAnalyzer(String filePath) {
        updateHoldings(filePath);
    }

    public void updateHoldings(String filePath) {
        HoldingsReader.read(filePath, holdings);
        run();
    }

    public void run() {

        // reset stats
        portfolioYield = 0.0;
        portfolioValue = 0.0;

        // gather data
        for (String stockTicker : holdings.keySet()) {

            GoogleFinanceHtmlParser parser = new GoogleFinanceHtmlParser(HtmlScraper.getHtml(Urls.GF_NYSE.getUrl() + stockTicker), stockTicker);
            if (parser.isValid()) {
                double weight = holdings.get(stockTicker) * parser.getPrice();
                portfolioYield += parser.getYield() * weight;
                portfolioValue += weight;

                System.out.println(stockTicker + " - Price: " + parser.getPrice() + " - Yield: " + parser.getYield());
            }

            try {
                Thread.sleep(60 * 1000); // take a break for a minute to avoid blacklisting
            } catch (Exception e) {}
        }

        // compute stats
        portfolioYield /= portfolioValue;
        twelveMonthForwardDividendIncome = portfolioValue * (1.0 + portfolioYield);
    }

    public double getPortfolioYield() {
        return portfolioYield;
    }

    public double getPortfolioValue() {
        return portfolioValue;
    }

    public double getPortfolioYieldOnCost() {
        return portfolioYieldOnCost;
    }

    public double getTwelveMonthForwardDividendIncome() {
        return twelveMonthForwardDividendIncome;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Portfolio Stats: \n");
        sb.append("Value: ");                                sb.append(portfolioValue);                   sb.append('\n');
        sb.append("Yield: ");                                sb.append(portfolioYield);                   sb.append('\n');
        sb.append("Yield on Cost: ");                        sb.append(portfolioYieldOnCost);             sb.append('\n');
        sb.append("Twelve Month Forward Dividend Income: "); sb.append(twelveMonthForwardDividendIncome); sb.append('\n');
        return sb.toString();
    }

    // Analyze holdings
//    public static void main(String[] args) {
//        Path filePath = Paths.get(System.getProperty("user.home"), "Documents", "E_holdings.txt");
//        PortfolioAnalyzer analyzer = new PortfolioAnalyzer(filePath.toString());
//        System.out.println(analyzer.toString());
//    }
}
