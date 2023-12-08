package bchk_perceptron.activationFunctions;

public class InverseTanh implements AcFunction{
    @Override
    public double calculate(double value) {
        double tanhX = Math.tanh(value);
        return 1 - tanhX * tanhX;
    }
}
