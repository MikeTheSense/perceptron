package bchk_perceptron.neurons;

import bchk_perceptron.activationFunctions.AcFunction;

import java.util.Map;
import java.util.Optional;

public abstract class Neuron implements NeuronOperations{
    public String name;

    protected AcFunction acFunction;
    protected Optional<Double> outPutSignal;
    protected Map<NeuronOperations,Weight> inputSignalsList;

    public Neuron(Map<NeuronOperations,Weight> inputSignalsList, AcFunction acFunction){
        this.inputSignalsList = inputSignalsList;
        this.acFunction = acFunction;
    }

    public abstract double calcActivation();

    public Map<NeuronOperations,Weight> getInputSignalsList() {
        return inputSignalsList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
