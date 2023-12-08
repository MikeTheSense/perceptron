package bchk_perceptron.activationFunctions;

public class InverseSigmoid implements AcFunction {


    @Override
    public double calculate(double value) {
        Sigmoid s = new Sigmoid();
        double sigmoidValue = s.calculate(value);
        return sigmoidValue * (1 - sigmoidValue);
    }
}
