package bchk_perceptron.activationFunctions;

public class InverseReLu implements AcFunction{
    public InverseReLu(){};
    @Override
    public double calculate(double value) {
        if (value > 0) return 1;
        else return 0;
    }
}
