import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kmcfadden on 8/24/17.
 */
public class PersonalFinanceReporter {

    private static final String url = "https://www.google.com/finance?q=NYSE:";
    private static final Path filePath = Paths.get(System.getProperty("user.home"), "Documents", "holdings.txt");

    private static Map<String, Integer> holdings = new HashMap<>();
    static {
        try {
            for (String[] row : CsvReader.read(filePath.toString())) {
                holdings.put(row[0], Integer.parseInt(row[1]));
            }
        } catch(Exception e) {
            throw new RuntimeException("Issue reading file");
        }
    }

    public static double getTotalYield() {

        double yield = 0.0;
        double totalWeight = 0.0;
        for (String stockTicker : holdings.keySet()) {

            GoogleFinanceHtmlParser parser = new GoogleFinanceHtmlParser(HtmlScraper.getHtml(url + stockTicker), stockTicker);
            if (parser.isValid()) {
                double weight = holdings.get(stockTicker) * parser.getPrice();
                yield += parser.getYield() * weight;
                totalWeight += weight;

                System.out.println(stockTicker + " - Price: " + parser.getPrice() + " - Yield: " + parser.getYield());
            }

            try {
                Thread.sleep(60 * 1000); // take a break for a minute to avoid blacklisting
            } catch (Exception e) {}
        }
        yield /= totalWeight;
        return yield;
    }

    public static void main(String[] args) {

//        String[][] holdings = {{"AGNC", "50"}, {"CSCO", "35"}, {"CYS", "34"}, {"F", "11"}, {"LXP", "31"},
//                               {"NLY", "30"}, {"RWT", "71"}, {"T", "86"}, {"VER", "76"}, {"VZ", "63"}};
//
//        try {
//            CsvWriter.write(filePath.toString(), holdings, false);
//        } catch (Exception e) {}

        System.out.println("Total Yield: " + getTotalYield());
    }
}
