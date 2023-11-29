package bchk_perceptron.neurons;

import bchk_perceptron.activationFunctions.AcFunction;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FuzzyNeuron extends Neuron {
    private double fuzzinessValue;
    //private final AcFunction inverseFunction;


    public FuzzyNeuron(Map<NeuronOperations,Weight> inputSignalsList,
                       AcFunction activationFunction,
                       double fuzzinessValue
                       ){
        super(inputSignalsList, activationFunction);
        this.fuzzinessValue = fuzzinessValue;
        //this.inverseFunction = inverseFunction;
    }


    @Override
    public double calcActivation() {
        double up = 0;
        double down = 0;
        for (Map.Entry<NeuronOperations, Weight> entry : inputSignalsList.entrySet()){
            up += Math.pow(entry.getValue().getValue(),fuzzinessValue) * entry.getKey().getSignal();
            down += Math.pow(entry.getValue().getValue(), fuzzinessValue);
        }
        double tmpVal = up/down;
        outPutSignal = Optional.of(tmpVal);
        return tmpVal;
    }

    @Override
    public double getSignal() {
        return outPutSignal.orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public void setSignal(double signal){};


    public void printWeights() {
        for (Map.Entry<NeuronOperations, Weight> entry : inputSignalsList.entrySet()) {
            System.out.format("%.5f ",entry.getValue().getValue());
        }
    }
}
