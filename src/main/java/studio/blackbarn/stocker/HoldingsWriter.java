package studio.blackbarn.stocker;

import studio.blackbarn.utils.CsvWriter;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by kmcfadden on 8/25/17.
 */
public class HoldingsWriter {

    // Manually edit this to write files for use in the holdings analyzer
    public static void main(String[] args) {

        Path filePath = Paths.get(System.getProperty("user.home"), null, null);

        String[][] holdings = {{null}};

        try {
            CsvWriter.write(filePath.toString(), holdings, false);
        } catch (Exception e) {
        }
    }
}
