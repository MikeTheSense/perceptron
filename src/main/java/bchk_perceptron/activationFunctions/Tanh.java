package bchk_perceptron.activationFunctions;

public class Tanh implements AcFunction{
    public double calculate(double value) {
        return Math.tanh(value);
    }
}
