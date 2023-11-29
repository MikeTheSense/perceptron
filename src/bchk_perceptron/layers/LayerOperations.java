package bchk_perceptron.layers;

import bchk_perceptron.neurons.Neuron;

import java.util.List;

public interface LayerOperations {

    <N extends Neuron> List<N> getNeurons();
    String getName();



}
