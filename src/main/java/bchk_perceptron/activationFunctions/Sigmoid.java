package bchk_perceptron.activationFunctions;

public class Sigmoid  implements AcFunction{

    @Override
    public double calculate(double value) {
        return 1 / (1 + Math.exp(-value));
    }
}
