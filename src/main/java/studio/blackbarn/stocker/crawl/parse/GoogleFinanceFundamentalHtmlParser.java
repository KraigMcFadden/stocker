package studio.blackbarn.stocker.crawl.parse;

import studio.blackbarn.stocker.StockExchange;
import studio.blackbarn.stocker.persistence.model.FundamentalsPO;
import studio.blackbarn.utils.StringUtils;

/**
 * Created by kmcfadden on 8/21/17.
 */
public class GoogleFinanceFundamentalHtmlParser extends FundamentalHtmlParser {

    private static final String NO_MATCHES = "produced no matches";

    private boolean isValid = true;

    private String tickerSymbol;
    private double price;
    private Double dividend;
    private Double yield;
    private Double pe;
    private Double eps;
    private String cap;

    public GoogleFinanceFundamentalHtmlParser(String html, String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;

        // determine if html is bad
        if (html == null || html.contains(NO_MATCHES)) {
            isValid = false;
            return;
        } else {
            isValid = true;
        }

        // parse html to find values of interest
        int index = 0;
        while (true) {

            // get the key and value
            index = findIndex(index, html, "key");
            String key = getStringAtIndex(index, html);
            if (key == null) { break; }

            index = findIndex(index, html, "val");
            String val = getStringAtIndex(index, html);
            if (val == null) { continue; }

            // determine what to do based on what the key is
            switch (key) {
                case "Open":      if (StringUtils.isFloatingPoint(val)) {
                                      price = Double.parseDouble(val);
                                  }
                                  continue;

                case "Mkt cap":   if (StringUtils.isFloatingPoint(val.substring(0, val.length() - 1))) {
                                      cap = val;
                                  }
                                  continue;

                case "P/E":       if (StringUtils.isFloatingPoint(val)) {
                                      pe = Double.parseDouble(val);
                                  }
                                  continue;

                case "Div/yield": if (val.contains("/")) {
                                     String[] temp = val.split("/");
                                     if (StringUtils.isFloatingPoint(temp[0]))
                                         dividend = Double.parseDouble(temp[0]);
                                     if (StringUtils.isFloatingPoint(temp[1]))
                                         yield = Double.parseDouble(temp[1]);
                                  }
                                  continue;

                case "EPS":       if (StringUtils.isFloatingPoint(val)) {
                                      eps = Double.parseDouble(val);
                                  }
                                  continue;

                default:          break;
            }
        }
    }

    // returns -1 if no more occurrences of index to search for
    private int findIndex(int index, String html, String query) {
        index = html.indexOf("<td class=\"" + query + "\"", index);
        if (index == -1) { return -1; }
        index = html.indexOf(">", index);
        return index;
    }

    // returns null if it receives -1 as an index
    private String getStringAtIndex(int index, String html) {

        if (index == -1)
            return null;

        char c;
        int i = 1;
        StringBuilder sb = new StringBuilder();
        while ((c = html.charAt(index + i)) != '\n') {
            sb.append(c);
            i++;
        }
        return sb.toString();
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean isValid() {
        return isValid;
    }

    public FundamentalsPO getData(StockExchange exchange) {
        return new FundamentalsPO(tickerSymbol, price, dividend, yield, pe, eps, cap, exchange);
    }

    // Testing
//    public static void main(String[] args) {
//        GoogleFinanceFundamentalHtmlParser parser = new GoogleFinanceFundamentalHtmlParser(HtmlScraper.getHtml("https://www.google.com/finance?q=NYSE:ABCD"), "VZ");
//
//        if (parser.isValid) {
//            String[] header = GoogleFinanceFundamentalHtmlParser.getHeader();
//            for (int i = 0; i < header.length; i++)
//                System.out.print(header[i] + "   ");
//            System.out.println('\n');
//            String[] data = parser.getData();
//            for (int i = 0; i < data.length; i++)
//                System.out.print(data[i] + "   ");
//        }
//    }
}
