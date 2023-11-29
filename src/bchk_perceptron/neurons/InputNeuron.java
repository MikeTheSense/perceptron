package bchk_perceptron.neurons;

public class InputNeuron implements NeuronOperations {
    private double outPutSignal;
    private String name;

    public InputNeuron(){};

    public InputNeuron(double outPutSignal){
        this.outPutSignal = outPutSignal;
    }

    public InputNeuron(double outPutSignal, String name){
        this.outPutSignal = outPutSignal;
        this.name = name;
    }

    public double getOutPutSignal() {
        return outPutSignal;
    }

    public void setOutPutSignal(double outPutSignal) {
        this.outPutSignal = outPutSignal;
    }

    @Override
    public double getSignal() {
        return outPutSignal;
    }

    @Override
    public void setSignal(double signal) {
        this.outPutSignal = outPutSignal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
