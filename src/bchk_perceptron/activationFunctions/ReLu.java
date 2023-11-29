package bchk_perceptron.activationFunctions;

public class ReLu implements AcFunction{
    public ReLu(){};
    @Override
    public double calculate(double value) {
        return Math.max(0, value);
    }
}
