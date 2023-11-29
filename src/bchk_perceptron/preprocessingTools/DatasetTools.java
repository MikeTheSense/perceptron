package bchk_perceptron.preprocessingTools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class DatasetTools {
    private static void readCsv(double[][] data, int start, String fileName, int[] strColumns, Function<String, String>[] mappers) {
        int index = start;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] lines = line.split(",");
                for (int i = 0; i < strColumns.length; i++) {
                    lines[strColumns[i]] = "0";
                }
                double[] values = Arrays.stream(lines).mapToDouble(Double::parseDouble).toArray();
                data[index] = values;
                index++;
            }
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    public static class WineSet {
        static Map<String, String> wineMap = new HashMap<>();

        {
            wineMap.put("Barolo", "1");
            wineMap.put("Grignolino", "2");
            wineMap.put("Barbera", "3");
        }

        public static double[][] readWineData() {
            double[][] res = new double[178][15];
            Function<String, String>[] mappers = new Function[]{l -> wineMap.get((String) l)};
            readCsv(res, 0, "dataSets/wine.csv", new int[]{1}, mappers);
            return res;
        }
    }
}
