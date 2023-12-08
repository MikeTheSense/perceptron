package bchk_perceptron.preprocessingTools;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PreprocessingTools {
    public static double[][] shuffle(double[][] data, int seed) {
        List<double[]> list = Arrays.asList(data);
        Collections.shuffle(list);
        list.toArray(data);
        return data;
    }
    public static void normalize(double[][] data, double start, double end) {

        double[] maxArr = new double[data[0].length];
        double[] minArr = new double[data[0].length];
        for(int i = 0; i < data[0].length; i++) {
            maxArr[i] = 0;
            minArr[i] = 0;
        }
        for(int i = 0; i < data.length; i++) {
            for(int j = 1; j < data[0].length; j++) {
                if(data[i][j] > maxArr[j]) {
                    maxArr[j] = data[i][j];
                }
                if(data[i][j] < minArr[j]) {
                    minArr[j] = data[i][j];
                }
            }
        }
        for(int i = 0; i < data.length; i++) {
            for(int j = 1; j < data[0].length; j++) {
                if(maxArr[j] - minArr[j] == 0) {
                    data[i][j] = start;
                } else {
                    data[i][j] = start + (((data[i][j] - minArr[j]) / (maxArr[j] - minArr[j])) * (end - start));
                }
            }
        }
    }

    public static void normalize(double[] data) {

        double min = 10;
        double max = -10;
        for(int i = 0; i < data.length; i++) {
            if(data[i] > max) {
                max = data[i];
            }
            if(data[i] < min) {
                min = data[i];
            }
        }
        for(int i = 0; i < data.length; i++) {
            if(max - min == 0) {
                data[i] = 0;
            } else {
                data[i] = 0 + (((data[i] - min) / (max - min)) * (1 - 0));
            }
        }
    }


    /* public static void save(String fileName, double[][]data) {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))){
            for(double[]d : data) {
                String line = Arrays.stream(d).mapToObj(Double::toString).collect(Collectors.joining(","));
                bw.write(line);
                bw.write("\n");
            }
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     */
}
