package bchk_perceptron.layers;

import bchk_perceptron.neurons.DeepNeuron;
import bchk_perceptron.neurons.NeuronOperations;
import bchk_perceptron.neurons.Weight;

import java.util.LinkedList;
import java.util.Map;

public class Perceptron {
    private final LinkedList<LayerOperations> layers;

    public Perceptron() {
        layers = new LinkedList<>();
    }

    public Perceptron addLayer(LayerOperations newLayer) {
        if(layers.size() != 0) {
            LayerOperations layer = layers.getLast();
            for(DeepNeuron neurone : ((PerceptronLayer)layer).getNeurons()) {
                for(DeepNeuron n : ((PerceptronLayer) newLayer).getNeurons()) {
                    Weight weight = n.getInputSignalsList().get(neurone);
                    neurone.putOutputSignal(n, weight);
                }
            }
        }
        layers.addLast(newLayer);
        return this;
    }

    public void train(double nu, double alpha, Map<NeuronOperations, Double> refs){
        for (LayerOperations layer : layers) {
            ((PerceptronLayer) layer).calcActivation();
        }

        PerceptronLayer outLayer = (PerceptronLayer) layers.getLast();
        Map<NeuronOperations, Map<NeuronOperations, Double>> outWeights = outLayer.trainOutLayer(nu, alpha, refs);

        PerceptronLayer hiddenLayer = (PerceptronLayer) layers.getFirst();
        if(hiddenLayer != outLayer) {
            Map<NeuronOperations, Map<NeuronOperations, Double>> hiddenWeights = hiddenLayer.train(nu, alpha, refs);
            hiddenLayer.setNewWeights(hiddenWeights);
        }
        outLayer.setNewWeights(outWeights);
    }

    public void printWeights() {
        for (LayerOperations layer : layers) {
            for(DeepNeuron dn : ((PerceptronLayer)layer).getNeurons()) {
                dn.printWeights();
                System.out.println();
            }
            System.out.println("\n");
        }
        System.out.println("\n------------------\n");
    }

    public double[] feed(){
        LayerOperations l = layers.getLast();
        double res[] = new double[l.getNeurons().size()];
        for (LayerOperations layer : layers) {
            ((PerceptronLayer) layer).calcActivation();
        }
        for(int i = 0; i < res.length; i++) {
            res[i] = l.getNeurons().get(i).calcActivation();
        }
        return res;
    }
}
