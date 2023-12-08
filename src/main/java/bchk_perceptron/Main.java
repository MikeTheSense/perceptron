package bchk_perceptron;

import bchk_perceptron.ModelTools.History;
import bchk_perceptron.ModelTools.Model;
import bchk_perceptron.ModelTools.ModelConfiguration;
import bchk_perceptron.activationFunctions.InverseReLu;
import bchk_perceptron.activationFunctions.ReLu;
import bchk_perceptron.layers.FuzzyLayer;
import bchk_perceptron.layers.InputLayer;
import bchk_perceptron.layers.Perceptron;
import bchk_perceptron.layers.PerceptronLayer;
import bchk_perceptron.neurons.NeuronOperations;
import bchk_perceptron.preprocessingTools.DatasetTools;
import bchk_perceptron.preprocessingTools.PreprocessingTools;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.module.Configuration;
import java.util.*;
import java.util.function.Function;

public class Main {


    //todo: 1. by Nu -- done 2. by fuzzy neuron -- done  3. by hidden neurons -- done  4. by trainSize 5. epoch -- done
    public static void main(String[] args) throws IOException {
       // epochTest();
       // learnSpeedTest();
        //onetimetest();
        //fuzzyLayerSize();
        //deepNeuroneLayerSize();
        byTestSize();

    }


    public static void byTestSize() throws IOException {
        double[][] myset = DatasetTools.WineSet.readWineData();
        PreprocessingTools.shuffle(myset, (int) new Date().getTime());
        PreprocessingTools.normalize(myset, -1, 1);

        List<String> pam = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            pam.add(i, Integer.toString(i + 1));
        }


        //double[] res = {0.0, 0.0};


        Model model = new Model();
        ModelConfiguration mc = ModelConfiguration.prepareConfigurationForFuzzyTest(myset, pam);
        //model.createAndExecute(mc,false,res );

        List<Double> x = new ArrayList<>();
        List<Double> y = new ArrayList<>();
        List<Double> z = new ArrayList<>();
        for (int i = 10; i < 178; i += 10) {
            mc.setEpochs(150);
            mc.setTestSize(178-i);
            mc.setTrainSize(i);
            Model models = new Model();

            double[] res = {0.0, 0.0};
            models.createAndExecute(mc, true, res);
            // x.add((double)i);
            x.add((double) i);
            y.add(res[1]);
            z.add(res[0]);
        }
        System.out.println(x.size());
        System.out.println(y.size());
        for (int i = 0; i < x.size(); i++) {
            System.out.println(x.get(i) + " Test Loss " + y.get(i) + " Train loss " + z.get(i));
        }
        draw(x, y, "Train size", "Train Loss", "зависимость погрешности классификации от величины тестовой выборки", "TrainSize", "погрешность");
    }



    public static void deepNeuroneLayerSize() throws IOException {
        double[][] myset = DatasetTools.WineSet.readWineData();
        PreprocessingTools.shuffle(myset, (int) new Date().getTime());
        PreprocessingTools.normalize(myset, -1, 1);

        List<String> pam = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            pam.add(i, Integer.toString(i + 1));
        }


        //double[] res = {0.0, 0.0};


        Model model = new Model();
        ModelConfiguration mc = ModelConfiguration.prepareConfigurationForFuzzyTest(myset, pam);
        //model.createAndExecute(mc,false,res );

        List<Double> x = new ArrayList<>();
        List<Double> y = new ArrayList<>();
        List<Double> z = new ArrayList<>();
        for (int i = 1; i < 50; i += 1) {
            mc.setEpochs(150);
            mc.setPerceptronSize(i);
            Model models = new Model();

            double[] res = {0.0, 0.0};
            models.createAndExecute(mc, true, res);
            // x.add((double)i);
            x.add((double) i);
            y.add(res[1]);
            z.add(res[0]);
        }
        System.out.println(x.size());
        System.out.println(y.size());
        for (int i = 0; i < x.size(); i++) {
            System.out.println(x.get(i) + " Test Loss " + y.get(i) + " Train loss " + z.get(i));
        }
        draw(x, y, "DeepNeurons size", "Train Loss", "зависимость погрешности классификации от количества нейронов скрытого слоя", "deepNeuroneSize", "погрешность");
    }





    public static void fuzzyLayerSize() throws IOException {
        double[][] myset = DatasetTools.WineSet.readWineData();
        PreprocessingTools.shuffle(myset, (int) new Date().getTime());
        PreprocessingTools.normalize(myset, -1, 1);

        List<String> pam = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            pam.add(i, Integer.toString(i + 1));
        }


        //double[] res = {0.0, 0.0};


        Model model = new Model();
        ModelConfiguration mc = ModelConfiguration.prepareConfigurationForFuzzyTest(myset, pam);
        //model.createAndExecute(mc,false,res );

        List<Double> x = new ArrayList<>();
        List<Double> y = new ArrayList<>();
        List<Double> z = new ArrayList<>();
        for (int i = 1; i < 50; i += 1) {
            mc.setEpochs(150);
            mc.setFuzzySize(i);
            Model models = new Model();

            double[] res = {0.0, 0.0};
            models.createAndExecute(mc, true, res);
            // x.add((double)i);
            x.add((double) i);
            y.add(res[1]);
            z.add(res[0]);
        }
        System.out.println(x.size());
        System.out.println(y.size());
        for (int i = 0; i < x.size(); i++) {
            System.out.println(x.get(i) + " Test Loss " + y.get(i) + " Train loss " + z.get(i));
        }
        draw(x, y, "FuzzyNeurons size", "Train Loss", "зависимость погрешности классификации от количества нейронов нечеткого слоя", "fuzzySize", "погрешность");
    }

    public static void onetimetest() throws  IOException{
        double[][] myset = DatasetTools.WineSet.readWineData();
        PreprocessingTools.shuffle(myset, (int) new Date().getTime());
        PreprocessingTools.normalize(myset, -1, 1);

        List<String> pam = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            pam.add(i, Integer.toString(i + 1));
        }


        //double[] res = {0.0, 0.0};


        Model model = new Model();
        ModelConfiguration mc = ModelConfiguration.prepareConfigurationForFuzzyTest(myset, pam);
        //model.createAndExecute(mc,false,res );

        List<Double> x = new ArrayList<>();
        List<Double> y = new ArrayList<>();
        List<Double> z = new ArrayList<>();
        for (int i = 10; i < 200; i += 10) {
            // mc.setEpochs(10);
            mc.setEpochs(i);
            Model models = new Model();

            double[] res = {0.0, 0.0};
            models.createAndExecute(mc, true, res);
            // x.add((double)i);
            x.add((double) i);
            y.add(res[1]);
            z.add(res[0]);
        }
        System.out.println(x.size());
        System.out.println(y.size());
        for (int i = 0; i < x.size(); i++) {
            System.out.println(x.get(i) + " Test Loss " + y.get(i) + " Train loss " + z.get(i));
        }
        draw(x, y, "Train Nu Value", "Train Loss", "зависимость погрешности классификации от количества эпох обучения", "oneTimeTest", "погрешность");
    }
    public static void learnSpeedTest() throws IOException{
        double[][] myset = DatasetTools.WineSet.readWineData();
        PreprocessingTools.shuffle(myset, (int) new Date().getTime());
        PreprocessingTools.normalize(myset, -1, 1);

        List<String> pam = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            pam.add(i, Integer.toString(i + 1));
        }


        //double[] res = {0.0, 0.0};


        Model model = new Model();
        ModelConfiguration mc = ModelConfiguration.prepareConfigurationForFuzzyTest(myset, pam);
        //model.createAndExecute(mc,false,res );

        List<Double> x = new ArrayList<>();
        List<Double> y = new ArrayList<>();
        List<Double> z = new ArrayList<>();
        for (double i = 0.1; i < 3.1; i += 0.05) {
            // mc.setEpochs(10);
            mc.setEpochs(50);
            mc.setNu(i);
            Model models = new Model();

            double[] res = {0.0, 0.0};
            models.createAndExecute(mc, true, res);
            // x.add((double)i);
            x.add((double) i);
            y.add(res[1]);
            z.add(res[0]);
        }
        System.out.println(x.size());
        System.out.println(y.size());
        for (int i = 0; i < x.size(); i++) {
            System.out.println(x.get(i) + " Test Loss " + y.get(i) + " Train loss " + z.get(i));
        }
        draw(x, y, "Train Nu Value", "Train Loss", "зависимость погрешности классификации от скорость обучения", "testNu", "погрешность");
    }


    public static void epochTest() throws IOException {
        double[][] myset = DatasetTools.WineSet.readWineData();
        PreprocessingTools.shuffle(myset, (int) new Date().getTime());
        PreprocessingTools.normalize(myset, -1, 1);

        List<String> pam = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            pam.add(i, Integer.toString(i + 1));
        }


        //double[] res = {0.0, 0.0};


        Model model = new Model();
        ModelConfiguration mc = ModelConfiguration.prepareConfigurationForFuzzyTest(myset, pam);
        //model.createAndExecute(mc,false,res );

        List<Double> x = new ArrayList<>();
        List<Double> y = new ArrayList<>();
        List<Double> z = new ArrayList<>();
        for (int i = 1; i < 500; i += 50) {
            // mc.setEpochs(10);
            mc.setEpochs(i);
            Model models = new Model();

            double[] res = {0.0, 0.0};
            models.createAndExecute(mc, true, res);
            // x.add((double)i);
            x.add((double) i);
            y.add(res[1]);
            z.add(res[0]);
        }


        System.out.println(x.size());
        System.out.println(y.size());
        for (int i = 0; i < x.size(); i++) {
            System.out.println(x.get(i) + " Test Loss " + y.get(i) + " Train loss " + z.get(i));
        }
        draw(x, y, "Train Epoch Size", "Train Loss", "зависимость погрешности классификации от количества эпох", "testEpoch", "погрешность");
    }


    public static void draw(List<Double> x, List<Double> y, String xName, String yName, String title, String fileName, String name) throws IOException {
        draw(x, y, xName, yName, title, fileName, null, null, name, null);
    }

    public static void draw(List<Double> x, List<Double> y, String xName, String yName, String title, String fileName,
                            List<Double> X, List<Double> Y, String fName, String sName) throws IOException {
        final XYSeries series = new XYSeries(fName);
        for (int i = 0; i < x.size(); i++) {
            series.add(x.get(i), y.get(i));
        }
        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        if (X != null) {
            final XYSeries seriesS = new XYSeries(sName);
            for (int i = 0; i < X.size(); i++) {
                seriesS.add(X.get(i), Y.get(i));
            }
            dataset.addSeries(seriesS);
        }

        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                title,
                xName,
                yName,
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        int width = 640;   /* Width of the image */
        int height = 480;  /* Height of the image */
        File XYChart = new File(fileName + ".jpeg");
        ChartUtilities.saveChartAsJPEG(XYChart, xylineChart, width, height);
    }

}

