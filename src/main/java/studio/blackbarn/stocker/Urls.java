package studio.blackbarn.stocker;

/**
 * Created by kmcfadden on 8/25/17.
 */
public enum Urls {
    gf("https://www.google.com/finance?q=NYSE:");

    private String url;

    Urls(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
