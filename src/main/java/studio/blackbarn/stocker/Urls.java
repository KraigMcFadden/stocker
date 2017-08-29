package studio.blackbarn.stocker;

/**
 * Created by kmcfadden on 8/25/17.
 */
public enum Urls {
    GF_NYSE("https://www.google.com/finance?q=NYSE:"),      // use these to crawl Google Finance for valid stocks
    GF_NASDAQ("https://www.google.com/finance?q=NASDAQ:");

    private String url;

    Urls(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
