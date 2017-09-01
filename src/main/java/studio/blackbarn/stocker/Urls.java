package studio.blackbarn.stocker;

/**
 * Created by kmcfadden on 8/25/17.
 */
public enum Urls {
    GF_NYSE("https://www.google.com/finance?q=NYSE:"),      // use these to crawl Google Finance for valid stocks
    GF_NASDAQ("https://www.google.com/finance?q=NASDAQ:"),

    MSN_NYSE("http://www.msn.com/en-us/money/stockdetails/NYS?symbol="),
    MSN_NASDAQ("http://www.msn.com/en-us/money/stockdetails/NAS?symbol="),

    YH_NYSE("https://finance.yahoo.com/quote/"),
    YH_NASDAQ("https://finance.yahoo.com/quote/");

    private String url;

    Urls(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
