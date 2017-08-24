/**
 * Created by kmcfadden on 8/21/17.
 */
public class GoogleFinanceHtmlParser extends HtmlParser {

    private static final String NO_MATCHES = "produced no matches";

    private boolean isValid = true;

    private String tickerSymbol;
    private double price;
    private double div;
    private double yield;
    private double pe;
    private double eps;
    private double cap;
    private char capMagnitude;

    public GoogleFinanceHtmlParser(String html, String tickerSymbol) {
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
                case "Open":      price = Double.parseDouble(val);
                                  continue;

                case "Mkt cap":   capMagnitude = val.charAt(val.length() - 1);
                                  cap = Double.parseDouble(val.substring(0, val.length() - 1));
                                  continue;

                case "P/E":       if (StringUtils.isFloatingPoint(val)) {
                                      pe = Double.parseDouble(val);
                                  }
                                  continue;

                case "Div/yield": div = Double.parseDouble(val.split("/")[0]);
                                  yield = Double.parseDouble(val.split("/")[1]);
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


    // the following methods aren't accurate until parser has been run
    public boolean isValid() {
        return isValid;
    }

    public double getYield() {
        return yield;
    }

    public double getPrice() {
        return price;
    }

    public String[] getData() {
        String[] data = { tickerSymbol, Double.toString(price), Double.toString(div), Double.toString(yield),
                Double.toString(pe), Double.toString(eps), Double.toString(cap) + capMagnitude };
        return data;
    }

    public static String[] getHeader() {
        String[] header = { "Symbol", "Price", "Div", "Yield", "P/E", "EPS", "Mkt Cap" };
        return header;
    }

    // Testing
    public static void main(String[] args) {
        GoogleFinanceHtmlParser parser = new GoogleFinanceHtmlParser(HtmlScraper.getHtml("https://www.google.com/finance?q=NYSE:VZ"), "VZ");

        if (parser.isValid) {
            String[] header = GoogleFinanceHtmlParser.getHeader();
            for (int i = 0; i < header.length; i++)
                System.out.print(header[i] + "   ");
            System.out.println('\n');
            String[] data = parser.getData();
            for (int i = 0; i < data.length; i++)
                System.out.print(data[i] + "   ");
        }
    }
}
