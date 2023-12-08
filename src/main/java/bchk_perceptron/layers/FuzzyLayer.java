package bchk_perceptron.layers;

import bchk_perceptron.neurons.FuzzyNeuron;
import bchk_perceptron.neurons.Neuron;
import bchk_perceptron.neurons.NeuronOperations;
import bchk_perceptron.neurons.Weight;

import java.util.*;

public class FuzzyLayer implements LayerOperations {

    private String name;

    private StringBuffer story = new StringBuffer();

    private List<FuzzyNeuron> neuronsList = new ArrayList<>();

    private double fuzzinessValue;

    private double lastError = 0;

    /**
     * Инициализация нечеткого слоя
     * @param name Имя слоя
     * @param neuronsNumber количество нейронов (neurons Number * количество нейронов предыдущего слоя)
     * @param prevLayer предыдущий слой
     * @param fuzzinessValue нечеткость
     */
    public FuzzyLayer(String name, int neuronsNumber, LayerOperations prevLayer, double fuzzinessValue){
        this.fuzzinessValue = fuzzinessValue;
        int size = neuronsNumber * prevLayer.getNeurons().size();

        Double[] initC = new Double[size];
        double c = 1.0 / (double) size;
        for(int i = 0; i < size; i++) {
            initC[i] = c;
        }
        Random rnd = new Random((int) new Date().getTime());
        int border;
        if ((size % 2) == 0) border = size;
        else border = size - 1;
        double s = 0;
        for (int i= 0; i< border; i++){
            if (i % 2 == 0){
                s = rnd.nextDouble() * (c/2);
                initC[i] += s;
            }
            else initC[i] -=s;
        }

        List<Double> list = Arrays.asList(initC);

        Collections.shuffle(list);
        list.toArray(initC);
        for(int i = 0; i < neuronsNumber; i++) {
            Map<NeuronOperations, Weight> inputSignals = new HashMap<>();
            for(int j = 0; j < prevLayer.getNeurons().size(); j++) {
                inputSignals.put(prevLayer.getNeurons().get(j), new Weight(initC[prevLayer.getNeurons().size() * i + j]));
            }
            FuzzyNeuron fn = new FuzzyNeuron(inputSignals, null, fuzzinessValue);
            fn.setName(Integer.toString(i));
            neuronsList.add(fn);
        }

        this.name = name;
    }

    private static double[] init(int neuronsNum, int prevSize) {
        int size = neuronsNum * prevSize;
        double[] initC = new double[size];
        return initC;
    }

    public void feed() {
        for (FuzzyNeuron neurone : neuronsList) {
            neurone.calcActivation();
        }
    }

    public void train(double border, double difBorder) {
        story.append("Инициализация тренировки для нечеткого слоя:");
        while(true) {
            story.append("Новая иттерация " + "\r\n");
            for (FuzzyNeuron neurone : neuronsList) {
                neurone.calcActivation();
            }

            double error = 0;
            for (FuzzyNeuron neurone : neuronsList) {
                for (Map.Entry<NeuronOperations, Weight> in : neurone.getInputSignalsList().entrySet()) {
                    error += Math.pow(in.getValue().getValue(), fuzzinessValue) *
                            Math.pow(neurone.getSignal() - in.getKey().getSignal(), 2.0);
                    //story.append("Нечеткий нейрон " + neurone.name + " Центр точки " + neurone.getSignal() + " Предыдущий нейрон " + in.getKey().getName() + " Степень принадлежности " + in.getValue().getValue() + "\r\n");
                }
                //story.append("погрешность = " + error + "\r\n");
            }
            if (error <= border || difBorder >= (error - lastError)) {
                return;
            }
            lastError = error;

            for (FuzzyNeuron neurone : neuronsList) {
                double sum = 0.0;
                for (Map.Entry<NeuronOperations, Weight> in : neurone.getInputSignalsList().entrySet()) {
                    for (FuzzyNeuron n : neuronsList) {
                        sum += Math.pow(Math.pow(neurone.getSignal() - in.getKey().getSignal(), 2) /
                                Math.pow(n.getSignal() - in.getKey().getSignal(), 2), 1.0 / (fuzzinessValue - 1));
                    }
                    in.getValue().setValue(1.0 / sum);
                }
            }
            for (FuzzyNeuron neurone : neuronsList)
            {
                double sum = 0;
                double sigmaI = 0;
                double outI = 0;
                for (FuzzyNeuron neurone1 : neuronsList){
                    sum += Math.pow(neurone.getSignal() - neurone1.getSignal(),2);
                }
                sigmaI = Math.sqrt(1/(neuronsList.size()*sum));
                for (Map.Entry<NeuronOperations, Weight> in : neurone.getInputSignalsList().entrySet()){
                    outI = Math.pow(in.getValue().getValue() - neurone.getSignal(),2);
                    in.getValue().setValue(Math.exp(-outI/(2*sigmaI)));
                }
                outI = Math.exp(-outI/(2*sigmaI));
                neurone.setOutPut(outI);
            }
        }
    }





    @Override
    public List<FuzzyNeuron> getNeurons() {
        return neuronsList;
    }

    @Override
    public String getName() {
        return name;
    }

    public StringBuffer getStory() {
        return story;
    }
}
