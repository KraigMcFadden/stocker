import java.util.HashSet;
import java.util.Set;

/**
 * Created by kmcfadden on 8/21/17.
 */

// This class is used to find valid Google Finance pages and store them to track in the future
public class GoogleFinanceCrawler {

    private final int numLetters = 27;
    private char[] letters = new char[numLetters];
    private Set<String> urls = new HashSet<>();

    public GoogleFinanceCrawler() {
        letters[0] = ' ';
        for (int i = 1; i < numLetters; i++) {
            letters[i] = (char) ('A' + i - 1);
        }
    }

    public void run() {
        char[] ticker = new char[4];
        for (int i = 0; i < numLetters; i++) {
            for (int j = 0; j < numLetters; j++) {
                for (int k = 0; k < numLetters; k++) {
                    for (int m = 1; m < numLetters; m++) {

                        if ((k == 0 && j > 0) || (k == 0 && i > 0) || (j == 0 && i > 0))
                            continue;

                        ticker[0] = letters[i];
                        ticker[1] = letters[j];
                        ticker[2] = letters[k];
                        ticker[3] = letters[m];

                        String toAppendToUrl = String.valueOf(ticker).trim();
                        String url = "https://www.google.com/finance?q=NYSE:" + toAppendToUrl;

                        if (HtmlScraper.getHtml(url) != null) {
                            urls.add(url);
                        }
                    }
                }
            }
        }
    }

//    // Testing
//    public static void main(String[] args) {
//        GoogleFinanceCrawler crawler = new GoogleFinanceCrawler();
//        System.out.println(crawler.letters);
//        crawler.run();
//    }
}
