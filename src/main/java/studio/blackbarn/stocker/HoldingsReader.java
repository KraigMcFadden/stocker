package studio.blackbarn.stocker;

import studio.blackbarn.utils.CsvReader;

import java.util.Map;

/**
 * Created by kmcfadden on 8/25/17.
 */
public class HoldingsReader {

    public static void read(String filePath, Map<String, Double> toFill) {
        try {
            for (String[] row : CsvReader.read(filePath)) {
                toFill.put(row[0], Double.parseDouble(row[1]));
            }
        } catch(Exception e) {
            throw new RuntimeException("Issue reading file");
        }
    }
}
