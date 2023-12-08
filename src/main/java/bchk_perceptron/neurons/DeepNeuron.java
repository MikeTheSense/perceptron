package bchk_perceptron.neurons;

import bchk_perceptron.activationFunctions.AcFunction;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DeepNeuron extends Neuron {
    private AcFunction inverseFunction;

    private Map<Weight, Double> prevWights = new HashMap<>();
    private Map<NeuronOperations, Weight> outputSignals = new HashMap<>();
    private Map<NeuronOperations, Weight> weightsStorage = new HashMap<>();


    public DeepNeuron(Map<NeuronOperations, Weight> inputSignalsList, AcFunction activationFunction,
                       AcFunction inverseFunction) {
        super(inputSignalsList,
                activationFunction);
        this.inverseFunction = inverseFunction;
    }

    @Override
    public double calcActivation() {
        double sum = 0;
        for (Map.Entry<NeuronOperations, Weight> entry : inputSignalsList.entrySet()) {
            sum += entry.getValue().getValue() * entry.getKey().getSignal();
        }
        outPutSignal = Optional.of(acFunction.calculate(sum));
        return sum;
    }

    public double calcInverseActivation() {
        double sum = 0;
        for (Map.Entry<NeuronOperations, Weight> entry : inputSignalsList.entrySet()) {
            sum += entry.getValue().getValue() * entry.getKey().getSignal();
        }
        return inverseFunction.calculate(sum);
    }

    public void calcInverseActivation(double value) {
        outPutSignal = Optional.of(inverseFunction.calculate(value));
    }

    @Override
    public double getSignal() {
        return outPutSignal.orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public void setSignal(double signal) {
        this.outPutSignal = Optional.of(signal);
    }

    public Map<Weight, Double> getPrevWights() {
        return prevWights;
    }

    public Map<NeuronOperations, Weight> getWeightsStorage() {
        return weightsStorage;
    }

    public Map<NeuronOperations, Weight> getOutputSignals() {
        return outputSignals;
    }

    public void putOutputSignal(Neuron n, Weight w) {
        outputSignals.put(n, w);
    }

    public void printWeights() {
        for (Map.Entry<NeuronOperations, Weight> entry : inputSignalsList.entrySet()) {
            System.out.format("%.5f ",entry.getValue().getValue());
        }
    }
}
