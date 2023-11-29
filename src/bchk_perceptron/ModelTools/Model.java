package bchk_perceptron.ModelTools;

import bchk_perceptron.activationFunctions.InverseReLu;
import bchk_perceptron.activationFunctions.ReLu;
import bchk_perceptron.layers.FuzzyLayer;
import bchk_perceptron.layers.InputLayer;
import bchk_perceptron.layers.Perceptron;
import bchk_perceptron.layers.PerceptronLayer;
import bchk_perceptron.neurons.NeuronOperations;
import bchk_perceptron.preprocessingTools.PreprocessingTools;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Model {
    public double createAndExecute(ModelConfiguration config, boolean isLoad, double[] rmse){
        double[][] data = config.getData();

        int trainSize = config.getTrainSize();
        int testSize = config.getTestSize();
        double[][] train = new double[trainSize][];
        double[][] test = new double[testSize][];

        System.arraycopy(data, 0, train, 0, trainSize);
        System.arraycopy(data, trainSize, test, 0, testSize);

        InputLayer inputLayer = new InputLayer(train[0], 14);
        Perceptron perceptron = new Perceptron();

        int fSize = config.getFuzzySize();
        if(fSize == 0) {
            fSize = 14;
        }
        FuzzyLayer fuzzyLayer = new FuzzyLayer("0", fSize, inputLayer, 2);

        int pSize = config.getPerceptronSize();
        if(pSize == 0) {
            pSize = 15;
        }
        PerceptronLayer firstLayer = new PerceptronLayer(pSize, new ReLu(), new InverseReLu(), fuzzyLayer, "1");
        PerceptronLayer secondLayer = new PerceptronLayer(3, new ReLu(), new InverseReLu(), firstLayer, "2");
        perceptron.addLayer(firstLayer);
        perceptron.addLayer(secondLayer);

        History history = new History();

        history.save(fuzzyLayer.getName(),  History.createHistory(fuzzyLayer));
        history.save(firstLayer.getName(),  History.createHistory(firstLayer));
        history.save(secondLayer.getName(), History.createHistory(secondLayer));

        Map<NeuronOperations, Double> refs = new HashMap<>();
        for(int i = 0; i < secondLayer.getNeurons().size(); i++) {
            refs.put(secondLayer.getNeurons().get(i), config.getNormA());
        }

        fuzzyLayer.train(config.getBorder(), config.getDifBorder());
        for(int i = 0; i < config.getEpochs(); i++) {
            for (double[] bucket : train) {
                inputLayer.setSignals( Arrays.copyOfRange(bucket, 1, bucket.length));
                refs.put(secondLayer.getNeurons().get((int)bucket[0] - 1), 1.0);
                fuzzyLayer.train(config.getBorder(), config.getDifBorder());
                perceptron.train(config.getNu(), config.getAlpha(), refs);
                refs.put(secondLayer.getNeurons().get((int)bucket[0] - 1), config.getNormA());

            }
        }



        int counter = 0;
        double[] resCounter = {0,0,0};
        double[] example = {0,0,0};

        double sum = 0;
        for(double[] bucket : train) {
            resCounter = new double[]{0,0,0};
            example = new double[]{0,0,0};

            inputLayer.setSignals( Arrays.copyOfRange(bucket, 1, bucket.length));
            fuzzyLayer.feed();
            double[] res = perceptron.feed();

            example[(int)bucket[0] - 1] = 1;

            double max = -1000000;
            int maxI = -1;
            String winner = null;
            for (int i = 0; i < res.length; i++) {
                if (res[i] > max) {
                    max = res[i];
                    maxI = i;
                    winner = config.getPam().get(i);
                }
            }
            resCounter[maxI] = 1;
            PreprocessingTools.normalize(res);
            for(int i = 0; i < secondLayer.getNeurons().size(); i++) {
                sum += Math.sqrt(Math.pow(res[i] - example[i], 2));
            }
            if (winner.equals(config.getPam().get((int)bucket[0] - 1))) {
                counter++;
            }

        }
        double trainRes = sum / (train.length * secondLayer.getNeurons().size() - 1.0);

        System.out.printf("\nTrain result: %d / %d;", counter, train.length);

        rmse[0] = trainRes;
        counter = 0;
        sum = 0;
        for(double[] bucket : test) {
            resCounter = new double[]{0,0,0};
            example = new double[]{0,0,0};

            inputLayer.setSignals( Arrays.copyOfRange(bucket, 1, bucket.length));
            fuzzyLayer.feed();
            double[] res = perceptron.feed();

            example[(int)bucket[0] - 1] = 1;

            double max = -1000000;
            int maxI = -1;
            String winner = null;
            for (int i = 0; i < res.length; i++) {
                if (res[i] > max) {
                    max = res[i];
                    maxI = i;
                    winner = config.getPam().get(i);
                }
            }
            resCounter[maxI] = 1;
            PreprocessingTools.normalize(res);
            for(int i = 0; i < secondLayer.getNeurons().size(); i++) {
                sum += Math.sqrt(Math.pow(res[i] - example[i], 2));
            }

            if (winner.equals(config.getPam().get((int)bucket[0] - 1))) {
                counter++;
            }
        }

        double testRes = sum / (test.length * secondLayer.getNeurons().size() - 1.0);
        System.out.printf("\nTest result: %d / %d;", counter, test.length);

        rmse[1] = testRes;




        return rmse[1];
    }
}
