public class ActivationFunctionUtils {
    enum ActivationFunction {
        SIGMOID, TANH, RELU, LEAKYRELU
    }

    public static ActivationFunction activationFunction = ActivationFunction.SIGMOID;

    public static double activationFunction(double x) {
        switch (activationFunction) {
            case SIGMOID:
                return sigmoid(x);
            case TANH:
                return tanh(x);
            case RELU:
                return ReLU(x);
            case LEAKYRELU:
            default:
                return leakyReLU(x);
        }
    }

    public static double activationFunctionDerivative(double x) {
        switch (activationFunction) {
            case SIGMOID:
                return sigmoidDerivative(x);
            case TANH:
                return tanhDerivative(x);
            case RELU:
                return ReLUDerivative(x);
            case LEAKYRELU:
            default:
                return leakyReLUDerivative(x);
        }
    }

    public static double sigmoid(double x) {
        return (1 / (1 + Math.exp(-x)));
    }

    public static double sigmoidDerivative(double x) {
        return (x * (1 - x));
    }

    public static double tanh(double x) {
        return (2 / (1 + Math.exp(-2 * x))) - 1;
    }

    public static double tanhDerivative(double x) {
        return 1 - Math.pow(tanh(x), 2);
    }

    public static double ReLU(double x) {
        if (x > 0) {
            return x;
        } else {
            return 0;
        }
    }

    public static double ReLUDerivative(double x) {
        if (x > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public static double leakyReLU(double x) {
        if (x > 0) {
            return x;
        } else {
            return 0.01 * x;
        }
    }

    public static double leakyReLUDerivative(double x) {
        if (x > 0) {
            return 1;
        } else {
            return 0.01;
        }
    }
}
