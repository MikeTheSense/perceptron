package bchk_perceptron.neurons.NeuronsUtil;

import bchk_perceptron.activationFunctions.AcFunction;
import bchk_perceptron.neurons.DeepNeuron;
import bchk_perceptron.neurons.Neuron;
import bchk_perceptron.neurons.NeuronOperations;
import bchk_perceptron.neurons.Weight;

import java.util.*;

public class NeuronsFactory {
    private static final Random random = new Random();

    public static <N extends NeuronOperations> DeepNeuron createDeepNeuron(List<N> inputNeurons,
                                                                           AcFunction activationFunction,
                                                                           AcFunction inverseFunction){
        Map<NeuronOperations, Weight> initialWeights = initWeights(inputNeurons, -1, 1, (int)new Date().getTime());
        return new DeepNeuron(initialWeights, activationFunction, inverseFunction);
    }


    private static <N extends NeuronOperations> Map<NeuronOperations, Weight> initWeights(List<N> inputs, double a, double b, int seed) {
        Map<NeuronOperations, Weight> res = new HashMap<>();
        for(NeuronOperations neuron : inputs) {
            double w = a + random.nextDouble() * (b - a);
            res.put(neuron, new Weight(w));
        }
        return res;
    }
}
