package bchk_perceptron.layers;

import bchk_perceptron.activationFunctions.AcFunction;
import bchk_perceptron.neurons.DeepNeuron;
import bchk_perceptron.neurons.Neuron;
import bchk_perceptron.neurons.NeuronOperations;
import bchk_perceptron.neurons.NeuronsUtil.NeuronsFactory;
import bchk_perceptron.neurons.Weight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PerceptronLayer implements LayerOperations {

    private List<DeepNeuron> neurons;

    private String name;

    public PerceptronLayer(int size,
                           AcFunction acFunction,
                           AcFunction inFunction,
                           LayerOperations prevLayer,
                           String name){
        neurons = new ArrayList<>();
        for (int i = 0; i<size; i++){
            DeepNeuron dn = NeuronsFactory.createDeepNeuron(prevLayer.getNeurons(), acFunction, inFunction);
            dn.setName(Integer.toString(i));
            neurons.add(dn);
        }
        this.name = name;
    }

    @Override
    public List<DeepNeuron> getNeurons(){ return neurons; }

    public void calcActivation() {
        for(DeepNeuron neurone : neurons) {
            neurone.calcActivation();
        }
    }

    public Map<NeuronOperations, Map<NeuronOperations, Double>> trainOutLayer(double nu, double alpha, Map<NeuronOperations, Double> refs) {
        Map<NeuronOperations, Map<NeuronOperations, Double>> res = new HashMap<>();
        for(DeepNeuron dn : neurons) {
            Map<NeuronOperations, Double> reCalculatedWeights = new HashMap<>();
            for(Map.Entry<NeuronOperations, Weight> in : dn.getInputSignalsList().entrySet()) {
                double d = (dn.getSignal() - refs.get(dn)) * dn.calcInverseActivation() * in.getKey().getSignal();
                Double lastWeight = dn.getPrevWights().getOrDefault(in.getValue(), 0.0);
                double newWeight = in.getValue().getValue() - nu * d + alpha * (in.getValue().getValue() - lastWeight);
                dn.getPrevWights().put(in.getValue(), in.getValue().getValue());
                reCalculatedWeights.put(in.getKey(), newWeight);
            }
            res.put(dn, reCalculatedWeights);
        }
        return res;
    }

    public void setNewWeights(Map<NeuronOperations, Map<NeuronOperations, Double>> reCalculatedWeights) {
        for(DeepNeuron dn : neurons) {
            for(NeuronOperations key : dn.getInputSignalsList().keySet()) {
                dn.getInputSignalsList().get(key).setValue(reCalculatedWeights.get(dn).get(key));
            }
        }
    }

    public  Map<NeuronOperations, Map<NeuronOperations, Double>> train(double nu, double alpha, Map<NeuronOperations, Double> refs){
        Map<NeuronOperations, Map<NeuronOperations, Double>> res = new HashMap<>();
        for(DeepNeuron neurone : neurons) {
            Map<NeuronOperations, Double> reCalculatedWeights = new HashMap<>();
            for(Map.Entry<NeuronOperations, Weight> in : neurone.getInputSignalsList().entrySet()) {
                double d = 0;
                for (Map.Entry<NeuronOperations, Weight> out : neurone.getOutputSignals().entrySet()) {
                    d += (out.getKey().getSignal() - refs.get(out.getKey())) * ((DeepNeuron)out.getKey()).calcInverseActivation() *
                            out.getValue().getValue() * neurone.calcInverseActivation() * in.getKey().getSignal();
                }
                Double lastWeight = neurone.getPrevWights().getOrDefault(in.getValue(), 0.0);
                double newWeight = in.getValue().getValue() - nu * d + alpha * (in.getValue().getValue() - lastWeight);
                neurone.getPrevWights().put(in.getValue(), in.getValue().getValue());
                reCalculatedWeights.put(in.getKey(), newWeight);
            }

            /*

            Здесь должна быть формула
            */
            res.put(neurone, reCalculatedWeights);
        }
        return res;
    }

    public String getName() {
        return name;
    }
}
