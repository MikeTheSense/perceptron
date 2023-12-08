package bchk_perceptron.layers;

import bchk_perceptron.neurons.InputNeuron;

import java.util.ArrayList;
import java.util.List;

public class InputLayer implements LayerOperations{
    private List<InputNeuron> inputNeuronsList;

    /**
     * @param input входной вектор сигналов
     * @param num количество нейронов
     */
    public InputLayer (double[] input, int num){
        inputNeuronsList = new ArrayList<>(num);
        for(int i = 0; i< num; i++){
            InputNeuron inputNeuron = new InputNeuron(input[i], Integer.toString(i));
            inputNeuronsList.add(inputNeuron);
        }
    }

    public void setSignals (double[] data){
        for(int i = 0; i< data.length; i++){
            inputNeuronsList.get(i).setOutPutSignal(data[i]);
        }
    }
    public List<InputNeuron> getNeurons() {return inputNeuronsList;}

    public String getName() {return "Input layer" + "Количество нейронов = " + inputNeuronsList.size();}

}
