package bchk_perceptron.ModelTools;

import bchk_perceptron.layers.LayerOperations;
import bchk_perceptron.neurons.Neuron;
import bchk_perceptron.neurons.NeuronOperations;
import bchk_perceptron.neurons.Weight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class History {
    Map<String,Map<String, List<Double>>> history = new HashMap<>();

    public void save(String name,Map<String, List<Double>> history){
        this.history.put(name,history);
    }
    public static Map<String, List<Double>> createHistory(LayerOperations layer) {
        List<Neuron> neurons = layer.getNeurons();
        Map<String, List<Double>> res = new HashMap<>();
        for(Neuron n : neurons) {
            Map<String, Weight> names = new HashMap<>();
            for(Map.Entry<NeuronOperations, Weight> entry : n.getInputSignalsList().entrySet()) {
                names.put((entry.getKey()).getName(), entry.getValue());
            }
            List<Double> list = new ArrayList<>();
            for(int i =0; i < names.size(); i++) {
                list.add(names.get(Integer.toString(i)).getValue());
            }
            res.put(n.getName(), list);
        }
        return res;
    }

}
